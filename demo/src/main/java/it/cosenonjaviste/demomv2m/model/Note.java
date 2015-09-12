package it.cosenonjaviste.demomv2m.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Note implements Parcelable {
    private long id;

    private String title;

    private String text;

    Note() {
    }

    public Note(long id, String title) {
        this.id = id;
        this.title = title;
    }

    public Note(long id, String title, String text) {
        this.id = id;
        this.title = title;
        this.text = text;
    }

    protected Note(Parcel in) {
        id = in.readLong();
        title = in.readString();
        text = in.readString();
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
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
