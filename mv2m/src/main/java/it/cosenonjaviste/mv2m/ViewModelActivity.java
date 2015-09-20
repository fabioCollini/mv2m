package it.cosenonjaviste.mv2m;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

public abstract class ViewModelActivity<VM extends ViewModel<?>> extends AppCompatActivity implements ViewModelContainer<VM> {

    private ViewModelManager<VM> vmManager;

    protected VM viewModel;

    @Override protected void onCreate(Bundle state) {
        super.onCreate(state);
        vmManager = new ViewModelManager<>();
        viewModel = vmManager.getOrCreate(this, state);
    }

    @Override public String getFragmentTag(Parcelable model) {
        return getClass().getName();
    }

    @Override protected void onResume() {
        super.onResume();
        vmManager.resume();
    }

    @Override protected void onPause() {
        super.onPause();
        vmManager.pause();
    }

    @Override protected void onDestroy() {
        super.onDestroy();
        vmManager.destroy();
    }

    @Override protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        vmManager.saveState(outState);
    }

    @Override public void onBackPressed() {
        vmManager.onBackPressed(this);
        super.onBackPressed();
    }

    @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        vmManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override public boolean onCreateOptionsMenu(Menu menu) {
        return vmManager.onCreateOptionsMenu(menu, getMenuInflater()) || super.onCreateOptionsMenu(menu);
    }

    @Override public boolean onOptionsItemSelected(MenuItem item) {
        return vmManager.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }
}
