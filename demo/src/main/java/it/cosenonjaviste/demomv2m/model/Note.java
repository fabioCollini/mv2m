package it.cosenonjaviste.demomv2m.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Note implements Parcelable {
    private String objectId;

    private String title;

    private String text;

    Note() {
    }

    public Note(String objectId, String title) {
        this.objectId = objectId;
        this.title = title;
    }

    public Note(String objectId, String title, String text) {
        this.objectId = objectId;
        this.title = title;
        this.text = text;
    }

    protected Note(Parcel in) {
        objectId = in.readString();
        title = in.readString();
        text = in.readString();
    }

    public String getObjectId() {
        return objectId;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(objectId);
        dest.writeString(title);
        dest.writeString(text);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Note> CREATOR = new Creator<Note>() {
        @Override
        public Note createFromParcel(Parcel in) {
            return new Note(in);
        }

        @Override
        public Note[] newArray(int size) {
            return new Note[size];
        }
    };
}
