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
package it.cosenonjaviste.demomv2m.ui.list;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.support.v7.widget.RecyclerView;

public abstract class BindableAdapter<T> extends RecyclerView.Adapter<BindableViewHolder<?, T>> {

    private ObservableArrayList<T> items;

    public BindableAdapter(ObservableArrayList<T> items) {
        this.items = items;
        items.addOnListChangedCallback(new WeakOnListChangedCallback<>(new ObservableList.OnListChangedCallback<ObservableList<T>>() {
            @Override public void onChanged(ObservableList<T> sender) {
                notifyDataSetChanged();
            }

            @Override public void onItemRangeChanged(ObservableList<T> sender, int positionStart, int itemCount) {
                notifyItemRangeChanged(positionStart, itemCount);
            }

            @Override public void onItemRangeInserted(ObservableList<T> sender, int positionStart, int itemCount) {
                notifyItemRangeInserted(positionStart, itemCount);
            }

            @Override public void onItemRangeMoved(ObservableList<T> sender, int fromPosition, int toPosition, int itemCount) {
                notifyDataSetChanged();
            }

            @Override public void onItemRangeRemoved(ObservableList<T> sender, int positionStart, int itemCount) {
                notifyItemRangeRemoved(positionStart, itemCount);
            }
        }));
    }

    @Override public void onBindViewHolder(BindableViewHolder<?, T> viewHolder, int i) {
        viewHolder.bind(items.get(i));
    }

    @Override public int getItemCount() {
        return items.size();
    }
}
