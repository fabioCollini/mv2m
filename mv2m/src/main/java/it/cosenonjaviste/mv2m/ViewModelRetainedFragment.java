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
import android.support.v4.app.FragmentManager;

public class ViewModelRetainedFragment<VM extends ViewModel<?, ?>> extends Fragment {

    public static final String TAG = ViewModelRetainedFragment.class.getName();

    VM viewModel;

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    public static <P extends ViewModel<?, ?>> ViewModelRetainedFragment<P> getOrCreateFragment(FragmentManager fragmentManager, String tag) {
        ViewModelRetainedFragment<P> fragment = (ViewModelRetainedFragment<P>) fragmentManager.findFragmentByTag(tag);
        if (fragment == null) {
            fragment = new ViewModelRetainedFragment<>();
            fragmentManager.beginTransaction().add(fragment, tag).commit();
        }
        return fragment;
    }

    @Override public void onDestroy() {
        super.onDestroy();
        if (viewModel != null) {
            viewModel.destroy();
        }
    }
}