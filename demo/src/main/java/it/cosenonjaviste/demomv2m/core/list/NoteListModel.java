package it.cosenonjaviste.demomv2m.core.list;

import android.databinding.ObservableArrayList;
import android.os.Parcel;
import android.os.Parcelable;

import it.cosenonjaviste.demomv2m.model.Note;

public class NoteListModel implements Parcelable {

    private ObservableArrayList<Note> items = new ObservableArrayList<>();

    public NoteListModel() {
    }

    protected NoteListModel(Parcel in) {
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
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
}
