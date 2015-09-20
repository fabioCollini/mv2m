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
package it.cosenonjaviste.demomv2m.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Parcelable;

import it.cosenonjaviste.demomv2m.core.Navigator;
import it.cosenonjaviste.demomv2m.core.detail.NoteModel;
import it.cosenonjaviste.demomv2m.ui.detail.NoteActivity;
import it.cosenonjaviste.mv2m.ViewModel;

public class ActivityNavigator implements Navigator {

    private Activity activity;

    public static void startActivity(Activity activity, Class<?> cls, Parcelable noteModel) {
        if (activity != null) {
            activity.startActivity(new Intent(activity, cls).putExtra(ViewModel.MODEL, noteModel));
        }
    }

    public static void startActivityForResult(Activity activity, Class<?> cls, int requestCode, Parcelable noteModel) {
        if (activity != null) {
            activity.startActivityForResult(new Intent(activity, cls).putExtra(ViewModel.MODEL, noteModel), requestCode);
        }
    }

    @Override public void openDetail(NoteModel model) {
        startActivityForResult(activity, NoteActivity.class, OPEN_DETAIL, model);
    }

    @Override public void setActivity(Activity activity) {
        this.activity = activity;
    }
}
