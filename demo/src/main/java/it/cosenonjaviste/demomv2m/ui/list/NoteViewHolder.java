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

import it.cosenonjaviste.demomv2m.BR;
import it.cosenonjaviste.demomv2m.core.list.NoteListViewModel;
import it.cosenonjaviste.demomv2m.databinding.NoteListItemBinding;
import it.cosenonjaviste.demomv2m.model.Note;
import it.cosenonjaviste.mv2m.recycler.BaseBindableViewHolder;

public class NoteViewHolder extends BaseBindableViewHolder<NoteListItemBinding, Note> {
    private final NoteListViewModel viewModel;

    protected NoteViewHolder(NoteListItemBinding binding, NoteListViewModel viewModel) {
        super(binding, BR.viewHolder);
        this.viewModel = viewModel;
    }

    public void openDetail() {
        viewModel.openDetail(item.get().getObjectId());
    }
}
