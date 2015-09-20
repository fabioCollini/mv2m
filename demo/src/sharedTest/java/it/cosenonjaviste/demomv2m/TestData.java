package it.cosenonjaviste.demomv2m;

import android.support.annotation.NonNull;

import java.io.IOException;

import it.cosenonjaviste.demomv2m.model.Note;
import it.cosenonjaviste.demomv2m.model.NoteListResponse;
import retrofit.RetrofitError;

public class TestData {

    public static final String ID_1 = "1";
    public static final String ID_2 = "2";
    public static final String ID_A = "a";
    public static final String TITLE_A = "xyz";
    public static final String TEXT_A = "jkl";
    public static final String NEW_TITLE = "newTitle";
    public static final String NEW_ID = "newId";
    public static final String NEW_TEXT = "newText";
    public static final String TITLE_1 = "abcdefgh";

    @NonNull public static NoteListResponse response() {
        return new NoteListResponse(note1(), note2());
    }

    @NonNull public static Note note1() {
        return note1(TITLE_1);
    }

    @NonNull public static Note note1(String title) {
        return new Note(ID_1, title);
    }

    @NonNull public static Note note2() {
        return new Note(ID_2, "b");
    }

    @NonNull public static Note note3() {
        return new Note("3", "c");
    }

    @NonNull public static Note noteA() {
        return new Note(ID_A, TITLE_A, TEXT_A);
    }

    @NonNull public static RetrofitError networkError() {
        return RetrofitError.networkError("url", new IOException());
    }
}
