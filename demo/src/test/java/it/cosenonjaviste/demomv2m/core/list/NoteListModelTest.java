package it.cosenonjaviste.demomv2m.core.list;

import org.junit.Test;

import java.util.Arrays;

import it.cosenonjaviste.demomv2m.TestData;
import it.cosenonjaviste.demomv2m.core.ParcelableTester;

public class NoteListModelTest {

    @Test
    public void testParcelable() {
        NoteListModel model = new NoteListModel();
        model.loadedData(Arrays.asList(TestData.note1()));
        ParcelableTester.check(model, NoteListModel.CREATOR);
    }
}