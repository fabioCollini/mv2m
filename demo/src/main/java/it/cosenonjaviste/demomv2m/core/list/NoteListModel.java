package it.cosenonjaviste.demomv2m.core.list;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableBoolean;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

import it.cosenonjaviste.demomv2m.model.Note;

public class NoteListModel implements Parcelable {

    private ObservableArrayList<Note> items = new ObservableArrayList<>();

    private ObservableBoolean loading = new ObservableBoolean();

    private ObservableBoolean error = new ObservableBoolean();

    public NoteListModel() {
    }

    protected NoteListModel(Parcel in) {
        loading = in.readParcelable(ObservableBoolean.class.getClassLoader());
        error = in.readParcelable(ObservableBoolean.class.getClassLoader());
        in.readList(items, getClass().getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(loading, flags);
        dest.writeParcelable(error, flags);
        dest.writeList(items);
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

    public ObservableBoolean getLoading() {
        return loading;
    }

    public ObservableBoolean getError() {
        return error;
    }

    public void loadedData(List<Note> notes) {
        items.addAll(notes);
        loading.set(false);
        error.set(false);
    }
}
