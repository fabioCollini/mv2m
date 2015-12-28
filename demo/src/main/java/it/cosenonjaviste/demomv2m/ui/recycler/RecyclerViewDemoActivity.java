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
package it.cosenonjaviste.demomv2m.ui.recycler;

import android.databinding.ObservableArrayList;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import it.cosenonjaviste.demomv2m.BR;
import it.cosenonjaviste.demomv2m.databinding.NormalListItemBinding;
import it.cosenonjaviste.demomv2m.databinding.TitleListItemBinding;
import it.cosenonjaviste.mv2m.recycler.BindableAdapter;
import it.cosenonjaviste.mv2m.recycler.BindableViewHolder;

public class RecyclerViewDemoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RecyclerView recyclerView = new RecyclerView(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        setContentView(recyclerView);
        ObservableArrayList<DemoItem> items = new ObservableArrayList<>();

        BindableAdapter<DemoItem> adapter = new BindableAdapter<>(items);

        adapter.addViewType(new BindableAdapter.ViewHolderFactory<NormalDemoItem>() {
            @Override public BindableViewHolder<NormalDemoItem> create(ViewGroup viewGroup) {
                return BindableViewHolder.create(NormalListItemBinding.inflate(getLayoutInflater(), viewGroup, false), BR.item);
            }
        }, NormalDemoItem.class);
        adapter.addViewType(BindableViewHolder.<TitleDemoItem>factory(getLayoutInflater(), BR.item, new BindableViewHolder.BindingInflater() {
            @Override public ViewDataBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean attachToRoot) {
                return TitleListItemBinding.inflate(layoutInflater, viewGroup, attachToRoot);
            }
        }), TitleDemoItem.class);

        recyclerView.setAdapter(adapter);

        items.add(new TitleDemoItem("Title"));
        items.add(new NormalDemoItem("normal1"));
        items.add(new NormalDemoItem("normal2"));
        items.add(new NormalDemoItem("normal3"));
        items.add(new NormalDemoItem("normal4"));
        items.add(new NormalDemoItem("normal5"));
    }

}
