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

public class ActivityHolder {

    private ViewModelContainer<?> viewModelContainer;

    public void setViewModelContainer(ViewModelContainer<?> viewModelContainer) {
        this.viewModelContainer = viewModelContainer;
    }

    public Activity getActivity() {
        return viewModelContainer.getActivity();
    }

    public void startActivity(Intent intent) {
        viewModelContainer.startActivity(intent);
    }

    public void startActivityForResult(Intent intent, int requestCode) {
        viewModelContainer.startActivityForResult(intent, requestCode);
    }

    public <ARG, VM extends ViewModel<ARG, ?>, F extends ViewModelFragment<VM>> F instantiateFragment(Class<F> cls, ARG argument) {
        return ArgumentManager.instantiateFragment(getActivity(), cls, argument);
    }

    public <ARG, VM extends ViewModel<ARG, ?>, A extends ViewModelActivity<VM>> void startActivity(Class<A> cls, ARG argument) {
        Activity activity = getActivity();
        if (activity != null) {
            activity.startActivity(ArgumentManager.createIntent(activity, cls, argument));
        }
    }

    public <ARG, VM extends ViewModel<ARG, ?>, A extends ViewModelActivity<VM>> void startActivityForResult(Class<A> cls, int requestCode, ARG argument) {
        Activity activity = getActivity();
        if (activity != null) {
            activity.startActivityForResult(ArgumentManager.createIntent(activity, cls, argument), requestCode);
        }
    }
}
