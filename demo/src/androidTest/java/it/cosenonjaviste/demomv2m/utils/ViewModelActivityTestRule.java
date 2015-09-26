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
package it.cosenonjaviste.demomv2m.utils;

import android.app.Activity;
import android.content.Intent;
import android.support.test.rule.ActivityTestRule;

import it.cosenonjaviste.mv2m.ArgumentManager;
import it.cosenonjaviste.mv2m.ViewModel;
import it.cosenonjaviste.mv2m.ViewModelActivity;

public class ViewModelActivityTestRule<A> extends ActivityTestRule<Activity> {
    public ViewModelActivityTestRule(Class<? extends ViewModelActivity<? extends ViewModel<A, ?>>> activityClass) {
        super((Class) activityClass, false, false);
    }

    public void launchActivity(A arg) {
        launchActivity(ArgumentManager.writeArgument(new Intent(), arg));
    }

    public void launchActivity() {
        launchActivity(ArgumentManager.writeArgument(new Intent(), null));
    }
}
