/*
 *  Copyright 2015 Fabio Collini.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package it.cosenonjaviste.demomv2m.core.detail;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableInt;
import android.support.annotation.NonNull;

import java.util.concurrent.Executor;

import it.cosenonjaviste.demomv2m.R;
import it.cosenonjaviste.demomv2m.core.MessageManager;
import it.cosenonjaviste.demomv2m.core.utils.ObservableString;
import it.cosenonjaviste.demomv2m.model.Note;
import it.cosenonjaviste.demomv2m.model.NoteLoader;
import it.cosenonjaviste.demomv2m.model.NoteSaver;
import it.cosenonjaviste.mv2m.ActivityResult;
import it.cosenonjaviste.mv2m.ViewModel;
import retrofit.RetrofitError;

public class NoteViewModel extends ViewModel<String, NoteModel> {

    private final Executor backgroundExecutor;
    private final Executor uiExecutor;
    private NoteLoader noteLoader;

    private NoteSaver noteSaver;

    private MessageManager messageManager;

    public final ObservableBoolean loading = new ObservableBoolean();

    public final ObservableBoolean sending = new ObservableBoolean();

    public NoteViewModel(Executor backgroundExecutor, Executor uiExecutor, NoteLoader noteLoader, NoteSaver noteSaver, MessageManager messageManager) {
        this.backgroundExecutor = backgroundExecutor;
        this.uiExecutor = uiExecutor;
        this.noteLoader = noteLoader;
        this.noteSaver = noteSaver;
        this.messageManager = messageManager;
    }

    @NonNull @Override public NoteModel createModel() {
        return new NoteModel();
    }

    @Override public void resume() {
        if (!loading.get() && !model.isLoaded() && getArgument() != null) {
            reloadData();
        }
    }

    public void reloadData() {
        loading.set(true);
        backgroundExecutor.execute(new Runnable() {
            @Override public void run() {
                executeServerCall();
            }
        });
    }

    private void executeServerCall() {
        try {
            final Note note = noteLoader.load(getArgument());
            uiExecutor.execute(new Runnable() {
                @Override public void run() {
                    model.update(note);
                    loading.set(false);
                }
            });
        } catch (Exception e) {
            uiExecutor.execute(new Runnable() {
                @Override public void run() {
                    model.getError().set(true);
                    loading.set(false);
                }
            });
        }
    }

    public void save() {
        boolean titleValid = checkMandatory(model.getTitle(), model.getTitleError());
        boolean textValid = checkMandatory(model.getText(), model.getTextError());
        if (titleValid && textValid) {
            sending.set(true);
            backgroundExecutor.execute(new Runnable() {
                @Override public void run() {
                    try {
                        Note note = new Note(null, model.getTitle().get(), model.getText().get());
                        String noteId = model.getNoteId();
                        if (noteId == null) {
                            noteId = noteSaver.createNewNote(note).getObjectId();
                            model.setNoteId(noteId);
                        } else {
                            noteSaver.save(noteId, note);
                        }
                        hideSendProgressAndShoMessage(R.string.note_saved);
                    } catch (RetrofitError e) {
                        hideSendProgressAndShoMessage(R.string.error_saving_note);
                    }
                }
            });
        }
    }

    private void hideSendProgressAndShoMessage(final int message) {
        uiExecutor.execute(new Runnable() {
            @Override public void run() {
                messageManager.showMessage(view, message);
                sending.set(false);
            }
        });
    }

    private boolean checkMandatory(ObservableString bindableString, ObservableInt error) {
        boolean empty = bindableString.isEmpty();
        error.set(empty ? R.string.mandatory_field : 0);
        return !empty;
    }

    @Override public ActivityResult onBackPressed() {
        return new ActivityResult(true, new Note(model.getNoteId(), model.getTitle().get(), model.getText().get()));
    }
}
