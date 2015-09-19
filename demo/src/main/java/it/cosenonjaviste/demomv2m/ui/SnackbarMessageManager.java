package it.cosenonjaviste.demomv2m.ui;

import android.app.Activity;
import android.support.design.widget.Snackbar;

import it.cosenonjaviste.demomv2m.core.MessageManager;

public class SnackbarMessageManager implements MessageManager {
    private Activity activity;

    @Override public void showMessage(int message) {
        if (activity != null) {
            Snackbar.make(activity.findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG).show();
        }
    }

    @Override public void setActivity(Activity activity) {
        this.activity = activity;
    }
}
