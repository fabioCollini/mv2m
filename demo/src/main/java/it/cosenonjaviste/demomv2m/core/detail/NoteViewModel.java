package it.cosenonjaviste.demomv2m.core.detail;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableInt;

import java.util.concurrent.Executor;

import it.cosenonjaviste.demomv2m.R;
import it.cosenonjaviste.demomv2m.core.MessageManager;
import it.cosenonjaviste.demomv2m.core.utils.ObservableString;
import it.cosenonjaviste.demomv2m.model.Note;
import it.cosenonjaviste.demomv2m.model.NoteLoaderService;
import it.cosenonjaviste.demomv2m.model.NoteSaverService;
import it.cosenonjaviste.mv2m.ViewModel;
import retrofit.RetrofitError;

public class NoteViewModel extends ViewModel<NoteModel> {

    private final Executor backgroundExecutor;
    private final Executor uiExecutor;
    private NoteLoaderService noteLoaderService;

    private NoteSaverService noteSaverService;

    private MessageManager messageManager;

    public final ObservableBoolean loading = new ObservableBoolean();

    public final ObservableBoolean sending = new ObservableBoolean();

    public NoteViewModel(Executor backgroundExecutor, Executor uiExecutor, NoteLoaderService noteLoaderService, NoteSaverService noteSaverService, MessageManager messageManager) {
        this.backgroundExecutor = backgroundExecutor;
        this.uiExecutor = uiExecutor;
        this.noteLoaderService = noteLoaderService;
        this.noteSaverService = noteSaverService;
        this.messageManager = messageManager;
        registerActivityAware(messageManager);
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
            final Note note = noteLoaderService.load(getModel().getNoteId());
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
            sending.set(true);
            backgroundExecutor.execute(new Runnable() {
                @Override public void run() {
                    try {
                        noteSaverService.save(getModel().getNoteId(), getModel().getTitle().get(), getModel().getText().get());
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
                messageManager.showMessage(message);
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
