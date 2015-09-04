package it.cosenonjaviste.core;

import android.databinding.ObservableInt;
import android.os.Parcel;
import android.os.Parcelable;

import com.hannesdorfmann.parcelableplease.annotation.ParcelablePlease;

import it.cosenonjaviste.core.utils.ObservableString;
import it.cosenonjaviste.model.Note;

@ParcelablePlease
public class NoteModel implements Parcelable {

    Note note;

    ObservableString title = new ObservableString();

    ObservableString text = new ObservableString();

    ObservableInt titleError = new ObservableInt();

    ObservableInt textError = new ObservableInt();

    public Note getNote() {
        return note;
    }

    public void setNote(Note note) {
        this.note = note;
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

    @Override public int describeContents() {
        return 0;
    }

    @Override public void writeToParcel(Parcel dest, int flags) {
        NoteModelParcelablePlease.writeToParcel(this, dest, flags);
    }

    public static final Creator<NoteModel> CREATOR = new Creator<NoteModel>() {
        public NoteModel createFromParcel(Parcel source) {
            NoteModel target = new NoteModel();
            NoteModelParcelablePlease.readFromParcel(target, source);
            return target;
        }

        public NoteModel[] newArray(int size) {
            return new NoteModel[size];
        }
    };
}
