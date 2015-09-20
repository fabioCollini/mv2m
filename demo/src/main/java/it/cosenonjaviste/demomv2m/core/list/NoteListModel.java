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
package it.cosenonjaviste.demomv2m.core.list;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableBoolean;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

import it.cosenonjaviste.demomv2m.model.Note;

public class NoteListModel implements Parcelable {

    private ObservableArrayList<Note> items = new ObservableArrayList<>();

    private ObservableBoolean error = new ObservableBoolean();

    private boolean loaded;

    public NoteListModel() {
    }

    protected NoteListModel(Parcel in) {
        error = in.readParcelable(ObservableBoolean.class.getClassLoader());
        in.readList(items, getClass().getClassLoader());
        loaded = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(error, flags);
        dest.writeList(items);
        dest.writeByte((byte) (loaded ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<NoteListModel> CREATOR = new Creator<NoteListModel>() {
        @Override
        public NoteListModel createFromParcel(Parcel in) {
            return new NoteListModel(in);
        }

        @Override
        public NoteListModel[] newArray(int size) {
            return new NoteListModel[size];
        }
    };

    public ObservableArrayList<Note> getItems() {
        return items;
    }

    public ObservableBoolean getError() {
        return error;
    }

    public void loadedData(List<Note> notes) {
        items.addAll(notes);
        error.set(false);
        loaded = true;
    }

    public boolean isLoaded() {
        return loaded;
    }

    public void loadedWithError() {
        error.set(true);
        loaded = true;
    }

    public void updateItem(Note newNote) {
        int pos = 0;
        for (Note note : items) {
            if (note.getObjectId().equals(newNote.getObjectId())) {
                items.set(pos, newNote);
                return;
            }
            pos++;
        }
        items.add(newNote);
    }
}
