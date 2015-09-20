package it.cosenonjaviste.demomv2m.core.detail;

import org.junit.Test;

import it.cosenonjaviste.demomv2m.TestData;
import it.cosenonjaviste.demomv2m.core.ParcelableTester;

public class NoteModelTest {
    @Test
    public void testParcelable() {
        NoteModel model = new NoteModel(TestData.ID_A);
        model.update(TestData.noteA());
        ParcelableTester.check(model, NoteModel.CREATOR);
    }
}
