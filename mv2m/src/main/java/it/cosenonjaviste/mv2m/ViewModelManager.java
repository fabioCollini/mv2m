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
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

class ViewModelManager<VM extends ViewModel<?, ?>> {
    public static final String RESULT_DATA = "RESULT_DATA";

    private VM viewModel;

    public <A extends AppCompatActivity & ViewModelContainer<VM>> VM getOrCreate(A activity, Bundle state) {
        return getOrCreate(activity, activity.getSupportFragmentManager(), state, activity.getIntent().getExtras());
    }

    public <F extends Fragment & ViewModelContainer<VM>> VM getOrCreate(F fragment, Bundle state) {
        VM viewModel = getOrCreate(fragment, fragment.getActivity().getSupportFragmentManager(), state, fragment.getArguments());
        if (viewModel.getOptionMenuId() > 0) {
            fragment.setHasOptionsMenu(true);
        }
        return viewModel;
    }

    private VM getOrCreate(final ViewModelContainer<VM> container, FragmentManager fragmentManager, Bundle state, Bundle arguments) {
        Object args = null;
        if (arguments != null) {
            args = ArgumentManager.readArgument(arguments);
        }
        ViewModelRetainedFragment<VM> retainedFragment = ViewModelRetainedFragment.getOrCreateFragment(fragmentManager, ViewModelRetainedFragment.TAG + container.getFragmentTag(args));
        viewModel = retainedFragment.viewModel;
        if (viewModel == null) {
            viewModel = container.createViewModel();
            retainedFragment.viewModel = viewModel;
            Parcelable model = null;
            if (state != null) {
                model = state.getParcelable(ViewModel.MODEL);
            }
            ((ViewModel) viewModel).initArgumentAndModel(args, model);
        }

        viewModel.attachActivity(container);

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

    public void onBackPressed(Activity activity) {
        ActivityResult result = viewModel.onBackPressed();
        if (result != null) {
            Intent intent = new Intent();
            intent.putExtra(RESULT_DATA, result.getData());
            activity.setResult(result.isResultOk() ? Activity.RESULT_OK : Activity.RESULT_CANCELED, intent);
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Parcelable resultData = data != null ? data.getParcelableExtra(RESULT_DATA) : null;
        viewModel.onResult(requestCode, new ActivityResult(resultCode, resultData));
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
