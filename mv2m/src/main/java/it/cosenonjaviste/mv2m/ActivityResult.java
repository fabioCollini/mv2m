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

import android.os.Parcelable;

public class ActivityResult {
    private boolean resultOk;

    private Parcelable data;

    public ActivityResult(boolean resultOk, Parcelable data) {
        this.resultOk = resultOk;
        this.data = data;
    }

    public boolean isResultOk() {
        return resultOk;
    }

    public <T extends Parcelable> T getData() {
        return (T) data;
    }
}
