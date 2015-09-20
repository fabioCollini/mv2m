package it.cosenonjaviste.mv2m;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;

public abstract class ViewModelFragment<VM extends ViewModel<?>> extends Fragment implements ViewModelContainer<VM> {

    private ViewModelManager<VM> vmManager;

    protected VM viewModel;

    @Override public void onCreate(Bundle state) {
        super.onCreate(state);
        vmManager = new ViewModelManager<>();
        viewModel = vmManager.getOrCreate(this, state);
    }

    public String getFragmentTag(Parcelable model) {
        return getClass().getName();
    }

    @Override public void onResume() {
        super.onResume();
        vmManager.resume();
    }

    @Override public void onPause() {
        super.onPause();
        vmManager.pause();
    }

    @Override public void onDestroy() {
        super.onDestroy();
        vmManager.destroy();
    }

    @Override public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        vmManager.saveState(outState);
    }
}
