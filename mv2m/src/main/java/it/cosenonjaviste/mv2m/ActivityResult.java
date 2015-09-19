package it.cosenonjaviste.mv2m;

import android.os.Parcelable;

public class ActivityResult {
    private boolean resultOk;

    private Parcelable data;

    public ActivityResult(boolean resultOk, Parcelable data) {
        this.resultOk = resultOk;
        this.data = data;
    }

    public boolean isResultOk() {
        return resultOk;
    }

    public <T extends Parcelable> T getData() {
        return (T) data;
    }
}
