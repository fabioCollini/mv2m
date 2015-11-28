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

import android.databinding.ObservableField;
import android.databinding.ViewDataBinding;

public class BaseBindableViewHolder<B extends ViewDataBinding, T> extends BindableViewHolder<T> {

    protected final B binding;

    protected final ObservableField<T> item = new ObservableField<>();

    protected BaseBindableViewHolder(B binding, int variableId) {
        super(binding.getRoot());
        this.binding = binding;
        binding.setVariable(variableId, this);
    }

    public void bind(T item) {
        this.item.set(item);
    }

    public ObservableField<T> getItem() {
        return item;
    }
}