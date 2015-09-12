package it.cosenonjaviste.demomv2m.core.detail;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableInt;
import android.os.Parcel;
import android.os.Parcelable;

import it.cosenonjaviste.demomv2m.core.utils.ObservableString;
import it.cosenonjaviste.demomv2m.model.Note;

public class NoteModel implements Parcelable {

    private long noteId;

    private ObservableBoolean error = new ObservableBoolean();

    private ObservableString title = new ObservableString();

    private ObservableString text = new ObservableString();

    private ObservableInt titleError = new ObservableInt();

    private ObservableInt textError = new ObservableInt();

    public NoteModel() {
    }

    protected NoteModel(Parcel in) {
        noteId = in.readLong();
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
        noteId = note.getId();
        title.set(note.getTitle());
        text.set(note.getText());
        error.set(false);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(noteId);
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
        return noteId != 0 || error.get();
    }

    public long getNoteId() {
        return noteId;
    }
}
