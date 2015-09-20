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
package it.cosenonjaviste.demomv2m.core.detail;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableInt;
import android.os.Parcel;
import android.os.Parcelable;

import it.cosenonjaviste.demomv2m.core.utils.ObservableString;
import it.cosenonjaviste.demomv2m.model.Note;

public class NoteModel implements Parcelable {

    private String noteId;

    private ObservableBoolean error = new ObservableBoolean();

    private ObservableString title = new ObservableString();

    private ObservableString text = new ObservableString();

    private ObservableInt titleError = new ObservableInt();

    private ObservableInt textError = new ObservableInt();

    public NoteModel() {
    }

    public NoteModel(String noteId) {
        this.noteId = noteId;
    }

    protected NoteModel(Parcel in) {
        noteId = in.readString();
        title = in.readParcelable(ObservableString.class.getClassLoader());
        text = in.readParcelable(ObservableString.class.getClassLoader());
        titleError = in.readParcelable(ObservableInt.class.getClassLoader());
        textError = in.readParcelable(ObservableInt.class.getClassLoader());
    }

    public ObservableBoolean getError() {
        return error;
    }

    public ObservableString getTitle() {
        return title;
    }

    public ObservableString getText() {
        return text;
    }

    public ObservableInt getTitleError() {
        return titleError;
    }

    public ObservableInt getTextError() {
        return textError;
    }

    public void update(Note note) {
        title.set(note.getTitle());
        text.set(note.getText());
        error.set(false);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(noteId);
        dest.writeParcelable(title, flags);
        dest.writeParcelable(text, flags);
        dest.writeParcelable(titleError, flags);
        dest.writeParcelable(textError, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<NoteModel> CREATOR = new Creator<NoteModel>() {
        @Override
        public NoteModel createFromParcel(Parcel in) {
            return new NoteModel(in);
        }

        @Override
        public NoteModel[] newArray(int size) {
            return new NoteModel[size];
        }
    };

    public boolean isLoaded() {
        return title.get() != null || error.get();
    }

    public String getNoteId() {
        return noteId;
    }

    public void setNoteId(String noteId) {
        this.noteId = noteId;
    }
}
