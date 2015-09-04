package it.cosenonjaviste.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.hannesdorfmann.parcelableplease.annotation.ParcelablePlease;

@ParcelablePlease
public class Note implements Parcelable {
    String title;

    String text;

    Note() {
    }

    public Note(String title, String text) {
        this.title = title;
        this.text = text;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override public int describeContents() {
        return 0;
    }

    @Override public void writeToParcel(Parcel dest, int flags) {
        NoteParcelablePlease.writeToParcel(this, dest, flags);
    }

    public static final Creator<Note> CREATOR = new Creator<Note>() {
        public Note createFromParcel(Parcel source) {
            Note target = new Note();
            NoteParcelablePlease.readFromParcel(target, source);
            return target;
        }

        public Note[] newArray(int size) {
            return new Note[size];
        }
    };
}
