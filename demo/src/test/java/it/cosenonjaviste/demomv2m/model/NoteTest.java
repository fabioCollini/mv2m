package it.cosenonjaviste.demomv2m.model;

import org.junit.Test;

import it.cosenonjaviste.demomv2m.core.ParcelableTester;

public class NoteTest {
    @Test
    public void testParcelable() {
        ParcelableTester.check(new Note(123L, "a", "b"), Note.CREATOR);
    }
}
