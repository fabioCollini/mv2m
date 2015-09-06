package it.cosenonjaviste.core;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableInt;
import android.os.Parcel;
import android.os.Parcelable;

import it.cosenonjaviste.core.utils.ObservableString;
import it.cosenonjaviste.model.Note;

public class NoteModel implements Parcelable {

    private Note note;

    private ObservableBoolean error = new ObservableBoolean();

    private ObservableString title = new ObservableString();

    private ObservableString text = new ObservableString();

    private ObservableInt titleError = new ObservableInt();

    private ObservableInt textError = new ObservableInt();

    public NoteModel() {
    }

    protected NoteModel(Parcel in) {
        note = in.readParcelable(Note.class.getClassLoader());
        title = in.readParcelable(ObservableString.class.getClassLoader());
        text = in.readParcelable(ObservableString.class.getClassLoader());
        titleError = in.readParcelable(ObservableInt.class.getClassLoader());
        textError = in.readParcelable(ObservableInt.class.getClassLoader());
    }

    public Note getNote() {
        return note;
    }

    public void setNote(Note note) {
        this.note = note;
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
        this.note = note;
        title.set(note.getTitle());
        text.set(note.getText());
        error.set(false);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(note, flags);
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
        return note != null || error.get();
    }
}
