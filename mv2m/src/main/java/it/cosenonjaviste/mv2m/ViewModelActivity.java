package it.cosenonjaviste.mv2m;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public abstract class ViewModelActivity<VM extends ViewModel<?>> extends AppCompatActivity {

    protected VM viewModel;

    protected abstract VM createViewModel();

    @Override protected void onCreate(Bundle state) {
        super.onCreate(state);
        viewModel = ViewModelManager.getOrCreate(this, state, getFragmentTag(), new ViewModelManager.Factory<VM>() {
            @Override public VM create() {
                return createViewModel();
            }
        });
    }

    protected String getFragmentTag() {
        return getClass().getName();
    }

    @Override protected void onResume() {
        super.onResume();
        ViewModelManager.resume(this, viewModel);
    }

    @Override protected void onPause() {
        super.onPause();
        ViewModelManager.pause(viewModel);
    }

    @Override protected void onDestroy() {
        super.onDestroy();
        ViewModelManager.destroy(viewModel);
    }

    @Override protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        ViewModelManager.saveState(outState, viewModel);
    }

    @Override public void onBackPressed() {
        ViewModelManager.onBackPressed(this, viewModel);
        super.onBackPressed();
    }

    @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ViewModelManager.onActivityResult(viewModel, requestCode, resultCode, data);
    }
}
