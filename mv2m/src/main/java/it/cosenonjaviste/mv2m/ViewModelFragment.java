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

import android.os.Bundle;
import android.support.v4.app.Fragment;

public abstract class ViewModelFragment<VM extends ViewModel<?, ?>> extends Fragment implements ViewModelContainer<VM> {

    private ViewModelManager<VM> vmManager;

    protected VM viewModel;

    @Override public void onCreate(Bundle state) {
        super.onCreate(state);
        vmManager = new ViewModelManager<>();
        viewModel = vmManager.getOrCreate(this, state);
    }

    public String getFragmentTag(Object args) {
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
