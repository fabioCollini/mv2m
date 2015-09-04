package it.cosenonjaviste.lib;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;

public class ViewModelManager {
    public static final String MODEL = "model";

    public static void resume(Object view, ViewModel<?, ?> viewModel) {
        ((ViewModel) viewModel).resume(view);
    }

    public static void pause(ViewModel<?, ?> viewModel) {
        viewModel.pause();
    }

    public static void destroy(ViewModel<?, ?> viewModel) {
        viewModel.detachView();
    }

    public static void saveState(final Bundle outState, ViewModel<?, ?> viewModel) {
        outState.putParcelable(MODEL, viewModel.getModel());
    }

    @NonNull public static <VM extends ViewModel<?, ?>> VM init(VM viewModel, Bundle state, Bundle args, Factory<VM> factory) {
        if (viewModel == null) {
            viewModel = factory.create();
        }
        Parcelable model = null;
        if (state != null) {
            model = state.getParcelable(MODEL);
        }
        if (model == null && args != null) {
            model = args.getParcelable(MODEL);
        }
        ((ViewModel) viewModel).initModel(model);
        return viewModel;
    }

    public interface Factory<VM extends ViewModel<?, ?>> {
        VM create();
    }
}
