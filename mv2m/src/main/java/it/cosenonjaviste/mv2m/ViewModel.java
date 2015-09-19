package it.cosenonjaviste.mv2m;

import android.app.Activity;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class ViewModel<M extends Parcelable, V> {

    private V view;

    private M model;

    private List<ActivityAware> activityAwares = new ArrayList<>();

    protected void registerActivityAware(ActivityAware activityAware) {
        activityAwares.add(activityAware);
    }

    public final void resume(V view) {
        this.view = view;
        resume();
    }

    public void pause() {
    }

    public void resume() {
    }

    public void destroy() {
    }

    public void detachView() {
        this.view = null;
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

    public M initAndResume(V view) {
        return initAndResume(null, view);
    }

    public M initAndResume(final M newModel, V view) {
        initModel(newModel);
        resume(view);
        return getModel();
    }

    public final V getView() {
        return view;
    }

    public M getModel() {
        return model;
    }

    public final void attachActivity(Activity activity) {
        for (ActivityAware activityAware : activityAwares) {
            activityAware.setActivity(activity);
        }
    }
}
