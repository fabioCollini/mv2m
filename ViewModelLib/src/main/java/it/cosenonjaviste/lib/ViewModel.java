package it.cosenonjaviste.lib;

import android.os.Parcelable;

public class ViewModel<M extends Parcelable, V> {

    private V view;

    private M model;

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
}
