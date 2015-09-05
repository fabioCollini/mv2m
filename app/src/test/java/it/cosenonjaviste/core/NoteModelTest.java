package it.cosenonjaviste.core;

import org.junit.Test;

import it.cosenonjaviste.model.Note;

public class NoteModelTest {
    @Test
    public void testParcelable() {
        NoteModel model = new NoteModel();
        model.setNote(new Note(123L, "a", "b"));
        ParcelableTester.check(model, NoteModel.CREATOR);
    }
}
