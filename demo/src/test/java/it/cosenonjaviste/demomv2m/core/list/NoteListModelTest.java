package it.cosenonjaviste.demomv2m.core.list;

import org.junit.Test;

import java.util.Arrays;

import it.cosenonjaviste.demomv2m.core.ParcelableTester;
import it.cosenonjaviste.demomv2m.model.Note;

public class NoteListModelTest {

    @Test
    public void testParcelable() {
        NoteListModel model = new NoteListModel();
        model.loadedData(Arrays.asList(new Note(1, "a")));
        ParcelableTester.check(model, NoteListModel.CREATOR);
    }
}