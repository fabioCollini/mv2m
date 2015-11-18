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

public class SimpleBindableViewHolder<B extends ViewDataBinding, T> extends BindableViewHolder<T> {

    protected final B binding;

    private final Binder<B, T> binder;

    private final int variableId;

    protected T item;

    protected SimpleBindableViewHolder(B binding, Binder<B, T> binder) {
        super(binding.getRoot());
        this.binding = binding;
        this.binder = binder;
        variableId = 0;
    }

    protected SimpleBindableViewHolder(B binding, int variableId) {
        super(binding.getRoot());
        this.binding = binding;
        this.variableId = variableId;
        binder = null;
    }

    public void bind(T item) {
        this.item = item;
        if (binder != null) {
            binder.bind(binding, item);
        } else {
            binding.setVariable(variableId, item);
        }
        binding.executePendingBindings();
    }

    public T getItem() {
        return item;
    }
}