package it.cosenonjaviste.model;

import org.junit.Test;

import it.cosenonjaviste.core.ParcelableTester;

public class NoteTest {
    @Test
    public void testParcelable() {
        ParcelableTester.check(new Note(123L, "a", "b"), Note.CREATOR);
    }
}
