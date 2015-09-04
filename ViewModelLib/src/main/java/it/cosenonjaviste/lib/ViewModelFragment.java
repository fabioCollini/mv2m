package it.cosenonjaviste.lib;

import android.os.Bundle;
import android.support.v4.app.Fragment;

public abstract class ViewModelFragment<VM extends ViewModel<?, ?>> extends Fragment {

    protected VM viewModel;

    protected abstract VM createViewModel();

    @Override public void onCreate(Bundle state) {
        super.onCreate(state);
        viewModel = ViewModelRetainedFragment.getOrCreate(this, state, getFragmentTag(), this::createViewModel);
    }

    protected String getFragmentTag() {
        return getClass().getName();
    }

    @Override public void onResume() {
        super.onResume();
        ViewModelManager.resume(this, viewModel);
    }

    @Override public void onPause() {
        super.onPause();
        ViewModelManager.pause(viewModel);
    }

    @Override public void onDestroy() {
        super.onDestroy();
        ViewModelManager.destroy(viewModel);
    }

    @Override public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        ViewModelManager.saveState(outState, viewModel);
    }
}
