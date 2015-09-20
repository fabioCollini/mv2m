package it.cosenonjaviste.demomv2m.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Parcelable;

import it.cosenonjaviste.demomv2m.core.Navigator;
import it.cosenonjaviste.demomv2m.core.detail.NoteModel;
import it.cosenonjaviste.demomv2m.ui.detail.NoteActivity;
import it.cosenonjaviste.mv2m.ViewModel;

public class ActivityNavigator implements Navigator {

    private Activity activity;

    public static void startActivity(Activity activity, Class<?> cls, Parcelable noteModel) {
        if (activity != null) {
            activity.startActivity(new Intent(activity, cls).putExtra(ViewModel.MODEL, noteModel));
        }
    }

    public static void startActivityForResult(Activity activity, Class<?> cls, int requestCode, Parcelable noteModel) {
        if (activity != null) {
            activity.startActivityForResult(new Intent(activity, cls).putExtra(ViewModel.MODEL, noteModel), requestCode);
        }
    }

    @Override public void openDetail(NoteModel model) {
        startActivityForResult(activity, NoteActivity.class, OPEN_DETAIL, model);
    }

    @Override public void setActivity(Activity activity) {
        this.activity = activity;
    }
}
