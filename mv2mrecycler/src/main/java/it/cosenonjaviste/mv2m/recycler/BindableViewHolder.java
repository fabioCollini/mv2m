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

import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public abstract class BindableViewHolder<T> extends RecyclerView.ViewHolder {

    public BindableViewHolder(View itemView) {
        super(itemView);
    }

    public static <B extends ViewDataBinding, T> BindableViewHolder<T> create(B binding, Binder<B, T> binder) {
        return new SimpleBindableViewHolder<>(binding, binder);
    }

    public static <B extends ViewDataBinding, T> BindableViewHolder<T> create(B binding, int variableId) {
        return new SimpleBindableViewHolder<>(binding, variableId);
    }

    public abstract void bind(T item);

    public interface Binder<B extends ViewDataBinding, T> {
        void bind(B binding, T item);
    }
}