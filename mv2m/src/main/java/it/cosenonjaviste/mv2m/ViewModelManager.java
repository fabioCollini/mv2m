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
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

class ViewModelManager<VM extends ViewModel<?>> {
    public static final String RESULT_DATA = "RESULT_DATA";

    private VM viewModel;

    public <A extends AppCompatActivity & ViewModelContainer<VM>> VM getOrCreate(A activity, Bundle state) {
        Parcelable model = readModel(state, activity.getIntent().getExtras());
        return getOrCreate(activity, activity, activity.getSupportFragmentManager(), model);
    }

    public <F extends Fragment & ViewModelContainer<VM>> VM getOrCreate(F fragment, Bundle state) {
        Parcelable model = readModel(state, fragment.getArguments());
        return getOrCreate(fragment.getActivity(), fragment, fragment.getFragmentManager(), model);
    }

    private VM getOrCreate(Activity activity, final ViewModelContainer<VM> container, FragmentManager fragmentManager, Parcelable model) {
        viewModel = ViewModelRetainedFragment.getOrCreate(container.getFragmentTag(model), container, fragmentManager);

        ((ViewModel) viewModel).initModel(model);

        viewModel.attachActivity(activity);

        return viewModel;
    }

    public void resume() {
        viewModel.resume();
    }

    public void pause() {
        viewModel.pause();
    }

    public void destroy() {
        viewModel.detachView();
    }

    public void saveState(final Bundle outState) {
        outState.putParcelable(ViewModel.MODEL, viewModel.getModel());
    }

    @Nullable private <M extends Parcelable> M readModel(Bundle state, Bundle args) {
        M model = null;
        if (state != null) {
            model = state.getParcelable(ViewModel.MODEL);
        }
        if (model == null && args != null) {
            model = args.getParcelable(ViewModel.MODEL);
        }
        return model;
    }

    public void onBackPressed(Activity activity) {
        ActivityResult result = viewModel.onBackPressed();
        if (result != null) {
            Intent intent = new Intent();
            intent.putExtra(RESULT_DATA, result.getData());
            activity.setResult(result.isResultOk() ? Activity.RESULT_OK : Activity.RESULT_CANCELED, intent);
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        viewModel.onResult(requestCode, new ActivityResult(resultCode == Activity.RESULT_OK, data.getParcelableExtra(RESULT_DATA)));
    }

    public boolean onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        int menuId = viewModel.getOptionMenuId();
        if (menuId > 0) {
            menuInflater.inflate(menuId, menu);
            return true;
        } else {
            return false;
        }
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        return viewModel.onOptionsItemSelected(item.getItemId());
    }
}
