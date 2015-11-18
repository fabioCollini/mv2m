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
package it.cosenonjaviste.mv2m.recycler;

import android.databinding.ObservableList;
import android.databinding.ObservableList.OnListChangedCallback;

import java.lang.ref.WeakReference;

public class WeakOnListChangedCallback<T extends ObservableList> extends OnListChangedCallback<T> {

    private WeakReference<OnListChangedCallback<T>> ref;

    public WeakOnListChangedCallback(OnListChangedCallback<T> delegate) {
        this.ref = new WeakReference<>(delegate);
    }

    @Override public void onChanged(T sender) {
        if (ref.get() != null) {
            ref.get().onChanged(sender);
        }
    }

    @Override public void onItemRangeChanged(T sender, int positionStart, int itemCount) {
        if (ref.get() != null) {
            ref.get().onItemRangeChanged(sender, positionStart, itemCount);
        }
    }

    @Override public void onItemRangeInserted(T sender, int positionStart, int itemCount) {
        if (ref.get() != null) {
            ref.get().onItemRangeInserted(sender, positionStart, itemCount);
        }
    }

    @Override public void onItemRangeMoved(T sender, int fromPosition, int toPosition, int itemCount) {
        if (ref.get() != null) {
            ref.get().onItemRangeMoved(sender, fromPosition, toPosition, itemCount);
        }
    }

    @Override public void onItemRangeRemoved(T sender, int positionStart, int itemCount) {
        if (ref.get() != null) {
            ref.get().onItemRangeRemoved(sender, positionStart, itemCount);
        }
    }
}