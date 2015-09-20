/*
 *  Copyright 2015 Fabio Collini.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
