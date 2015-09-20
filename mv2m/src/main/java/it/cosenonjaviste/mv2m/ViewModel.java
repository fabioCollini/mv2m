package it.cosenonjaviste.mv2m;

import android.app.Activity;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class ViewModel<M extends Parcelable> {

    private M model;

    private List<ActivityAware> activityAwares = new ArrayList<>();

    protected void registerActivityAware(ActivityAware activityAware) {
        activityAwares.add(activityAware);
    }

    public void pause() {
    }

    public void resume() {
    }

    public void destroy() {
    }

    public void detachView() {
        for (ActivityAware activityAware : activityAwares) {
            activityAware.setActivity(null);
        }
    }

    public M createDefaultModel() {
        return null;
    }

    public void initModel(M model) {
        this.model = model;
        if (this.model == null) {
            this.model = createDefaultModel();
            if (this.model == null) {
                throw new RuntimeException("createDefaultModel not implemented in " + getClass().getName());
            }
        }
    }

    public M initAndResume() {
        return initAndResume(null);
    }

    public M initAndResume(M newModel) {
        initModel(newModel);
        resume();
        return getModel();
    }

    public M getModel() {
        return model;
    }

    public final void attachActivity(Activity activity) {
        for (ActivityAware activityAware : activityAwares) {
            activityAware.setActivity(activity);
        }
    }

    public ActivityResult onBackPressed() {
        return null;
    }

    public void onResult(int requestCode, ActivityResult activityResult) {
    }

    public int getOptionMenuId() {
        return -1;
    }

    public boolean onOptionsItemSelected(int itemId) {
        return false;
    }
}
