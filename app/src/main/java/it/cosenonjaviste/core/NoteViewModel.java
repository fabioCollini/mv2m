package it.cosenonjaviste.core;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableInt;

import it.cosenonjaviste.R;
import it.cosenonjaviste.core.utils.ObservableString;
import it.cosenonjaviste.lib.ViewModel;
import it.cosenonjaviste.model.Note;
import it.cosenonjaviste.model.NoteLoaderService;
import it.cosenonjaviste.model.NoteSaverService;

public class NoteViewModel extends ViewModel<NoteModel, NoteView> {

    private NoteLoaderService noteLoaderService;

    private NoteSaverService noteSaverService;

    private ObservableBoolean loading = new ObservableBoolean();

    private ObservableBoolean sending = new ObservableBoolean();

    public NoteViewModel(NoteLoaderService noteLoaderService, NoteSaverService noteSaverService) {
        this.noteLoaderService = noteLoaderService;
        this.noteSaverService = noteSaverService;
    }

    @Override public NoteModel createDefaultModel() {
        return new NoteModel();
    }

    @Override public void resume() {
        NoteModel model = getModel();
        if (!loading.get() && model.getNote() == null) {
            Note note = noteLoaderService.load();
            model.setNote(note);
            model.getTitle().set(note.getTitle());
            model.getText().set(note.getText());
        }
    }

    public void save() {
        NoteModel model = getModel();
        boolean titleValid = checkMandatory(model.getTitle(), model.getTitleError());
        boolean textValid = checkMandatory(model.getText(), model.getTextError());
        if (titleValid && textValid) {
            model.getNote().setTitle(model.getTitle().get());
            model.getNote().setText(model.getText().get());
            noteSaverService.save(model.getNote());
            getView().showMessage(R.string.note_saved);
        }
    }

    private boolean checkMandatory(ObservableString bindableString, ObservableInt error) {
        boolean empty = bindableString.isEmpty();
        error.set(empty ? R.string.mandatory_field : 0);
        return !empty;
    }

    public ObservableBoolean getLoading() {
        return loading;
    }

    public ObservableBoolean getSending() {
        return sending;
    }
}
