package it.cosenonjaviste.demomv2m.ui;

import android.app.Activity;
import android.content.Intent;

import it.cosenonjaviste.demomv2m.core.detail.NoteModel;
import it.cosenonjaviste.mv2m.ViewModelManager;

public class Navigator {
    public static void startActivity(Activity activity, Class<?> cls, NoteModel noteModel) {
        activity.startActivity(new Intent(activity, cls).putExtra(ViewModelManager.MODEL, noteModel));
    }
}
