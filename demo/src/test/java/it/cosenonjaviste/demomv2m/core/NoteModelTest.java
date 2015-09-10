package it.cosenonjaviste.demomv2m.core;

import org.junit.Test;

import it.cosenonjaviste.demomv2m.model.Note;

public class NoteModelTest {
    @Test
    public void testParcelable() {
        NoteModel model = new NoteModel();
        model.update(new Note(123L, "a", "b"));
        ParcelableTester.check(model, NoteModel.CREATOR);
    }
}
