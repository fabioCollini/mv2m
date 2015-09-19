package it.cosenonjaviste.demomv2m.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Parcelable;

import it.cosenonjaviste.demomv2m.core.Navigator;
import it.cosenonjaviste.demomv2m.core.detail.NoteModel;
import it.cosenonjaviste.demomv2m.ui.detail.NoteActivity;
import it.cosenonjaviste.mv2m.ViewModelManager;

public class ActivityNavigator implements Navigator {
    private Activity activity;

    public static void startActivity(Activity activity, Class<?> cls, Parcelable noteModel) {
        if (activity != null) {
            activity.startActivity(new Intent(activity, cls).putExtra(ViewModelManager.MODEL, noteModel));
        }
    }

    @Override public void openDetail(NoteModel model) {
        startActivity(activity, NoteActivity.class, model);
    }

    @Override public void setActivity(Activity activity) {
        this.activity = activity;
    }
}
