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
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class ViewModel<M extends Parcelable> {

    public static final String MODEL = "model";

    private M model;

    private List<ActivityAware> activityAwares = new ArrayList<>();

    protected void registerActivityAware(ActivityAware activityAware) {
        activityAwares.add(activityAware);
    }

    public void pause() {
    }

    public void resume() {
    }

    public void destroy() {
    }

    public void detachView() {
        for (ActivityAware activityAware : activityAwares) {
            activityAware.setActivity(null);
        }
    }

    public M createDefaultModel() {
        return null;
    }

    public void initModel(M model) {
        this.model = model;
        if (this.model == null) {
            this.model = createDefaultModel();
            if (this.model == null) {
                throw new RuntimeException("createDefaultModel not implemented in " + getClass().getName());
            }
        }
    }

    public M initAndResume() {
        return initAndResume(null);
    }

    public M initAndResume(M newModel) {
        initModel(newModel);
        resume();
        return getModel();
    }

    public M getModel() {
        return model;
    }

    public final void attachActivity(Activity activity) {
        for (ActivityAware activityAware : activityAwares) {
            activityAware.setActivity(activity);
        }
    }

    public ActivityResult onBackPressed() {
        return null;
    }

    public void onResult(int requestCode, ActivityResult activityResult) {
    }

    public int getOptionMenuId() {
        return -1;
    }

    public boolean onOptionsItemSelected(int itemId) {
        return false;
    }
}
