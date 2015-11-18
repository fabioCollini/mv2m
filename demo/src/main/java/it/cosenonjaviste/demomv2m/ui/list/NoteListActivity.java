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

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.ViewGroup;

import it.cosenonjaviste.demomv2m.BR;
import it.cosenonjaviste.demomv2m.R;
import it.cosenonjaviste.demomv2m.core.list.NoteListViewModel;
import it.cosenonjaviste.demomv2m.databinding.NoteListBinding;
import it.cosenonjaviste.demomv2m.databinding.NoteListItemBinding;
import it.cosenonjaviste.demomv2m.model.Note;
import it.cosenonjaviste.demomv2m.ui.ObjectFactory;
import it.cosenonjaviste.mv2m.ViewModelActivity;
import it.cosenonjaviste.mv2m.recycler.BindableAdapter;
import it.cosenonjaviste.mv2m.recycler.BindableViewHolder;

public class NoteListActivity extends ViewModelActivity<NoteListViewModel> {
    @Override public NoteListViewModel createViewModel() {
        return new NoteListViewModel(
                ObjectFactory.singleton().noteLoaderService(),
                ObjectFactory.singleton().backgroundExecutor(),
                ObjectFactory.singleton().uiExecutor(),
                ObjectFactory.singleton().navigator());
    }

    @Override protected void onCreate(Bundle state) {
        super.onCreate(state);
        NoteListBinding binding = DataBindingUtil.setContentView(this, R.layout.note_list);
        binding.setViewModel(viewModel);
        binding.list.setLayoutManager(new LinearLayoutManager(this));
        BindableAdapter<Note> adapter = new BindableAdapter<>(viewModel.getModel().getItems(), new BindableAdapter.ViewHolderFactory<Note>() {
            @Override public BindableViewHolder<Note> create(ViewGroup viewGroup) {
                final NoteListItemBinding binding = NoteListItemBinding.inflate(getLayoutInflater(), viewGroup, false);
                binding.getRoot().setOnClickListener(new View.OnClickListener() {
                    @Override public void onClick(View v) {
                        viewModel.openDetail(binding.getItem().getObjectId());
                    }
                });
                return BindableViewHolder.create(binding, BR.item);
            }
        });
        binding.list.setAdapter(adapter);
    }
}
