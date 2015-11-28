/*
 *  Copyright 2015 Fabio Collini.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package it.cosenonjaviste.mv2m;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

public abstract class ViewModelActivity<VM extends ViewModel<?, ?>> extends AppCompatActivity implements ViewModelContainer<VM> {

    private ViewModelManager<VM> vmManager;

    protected VM viewModel;

    @Override protected void onCreate(Bundle state) {
        super.onCreate(state);
        vmManager = new ViewModelManager<>();
        viewModel = vmManager.getOrCreate(this, state);
    }

    @Override public String getFragmentTag(Object args) {
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

    @Override public Activity getActivity() {
        return this;
    }
}
