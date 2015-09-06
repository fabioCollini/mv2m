package it.cosenonjaviste.core;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableInt;

import java.util.concurrent.Executor;

import it.cosenonjaviste.R;
import it.cosenonjaviste.core.utils.ObservableString;
import it.cosenonjaviste.lib.ViewModel;
import it.cosenonjaviste.model.Note;
import it.cosenonjaviste.model.NoteLoaderService;
import it.cosenonjaviste.model.NoteSaverService;
import retrofit.RetrofitError;

public class NoteViewModel extends ViewModel<NoteModel, NoteView> {

    private final Executor backgroundExecutor;
    private final Executor uiExecutor;
    private NoteLoaderService noteLoaderService;

    private NoteSaverService noteSaverService;

    public final ObservableBoolean loading = new ObservableBoolean();

    public final ObservableBoolean sending = new ObservableBoolean();

    public NoteViewModel(Executor backgroundExecutor, Executor uiExecutor, NoteLoaderService noteLoaderService, NoteSaverService noteSaverService) {
        this.backgroundExecutor = backgroundExecutor;
        this.uiExecutor = uiExecutor;
        this.noteLoaderService = noteLoaderService;
        this.noteSaverService = noteSaverService;
    }

    @Override public NoteModel createDefaultModel() {
        return new NoteModel();
    }

    @Override public void resume() {
        if (!loading.get() && !getModel().isLoaded()) {
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
            final Note note = noteLoaderService.load();
            uiExecutor.execute(new Runnable() {
                @Override public void run() {
                    getModel().update(note);
                    loading.set(false);
                }
            });
        } catch (Exception e) {
            uiExecutor.execute(new Runnable() {
                @Override public void run() {
                    getModel().getError().set(true);
                    loading.set(false);
                }
            });
        }
    }

    public void save() {
        boolean titleValid = checkMandatory(getModel().getTitle(), getModel().getTitleError());
        boolean textValid = checkMandatory(getModel().getText(), getModel().getTextError());
        if (titleValid && textValid) {
            final Note note = getModel().getNote();
            note.setTitle(getModel().getTitle().get());
            note.setText(getModel().getText().get());
            sending.set(true);
            backgroundExecutor.execute(new Runnable() {
                @Override public void run() {
                    try {
                        noteSaverService.save(note.getId(), note.getTitle(), note.getText());
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
                getView().showMessage(message);
                sending.set(false);
            }
        });
    }

    private boolean checkMandatory(ObservableString bindableString, ObservableInt error) {
        boolean empty = bindableString.isEmpty();
        error.set(empty ? R.string.mandatory_field : 0);
        return !empty;
    }
}
