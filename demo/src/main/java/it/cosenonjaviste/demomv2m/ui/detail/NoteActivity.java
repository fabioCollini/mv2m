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
package it.cosenonjaviste.demomv2m.ui.detail;

import android.databinding.DataBindingUtil;
import android.os.Bundle;

import it.cosenonjaviste.demomv2m.R;
import it.cosenonjaviste.demomv2m.core.detail.NoteViewModel;
import it.cosenonjaviste.demomv2m.databinding.NoteDetailBinding;
import it.cosenonjaviste.demomv2m.ui.ObjectFactory;
import it.cosenonjaviste.demomv2m.ui.SnackbarMessageManager;
import it.cosenonjaviste.mv2m.ViewModelActivity;

public class NoteActivity extends ViewModelActivity<NoteViewModel> {

    @Override public NoteViewModel createViewModel() {
        return new NoteViewModel(
                ObjectFactory.singleton().backgroundExecutor(),
                ObjectFactory.singleton().uiExecutor(),
                ObjectFactory.singleton().noteLoaderService(),
                ObjectFactory.singleton().noteSaverService(),
                new SnackbarMessageManager());
    }

    @Override protected void onCreate(Bundle state) {
        super.onCreate(state);
        NoteDetailBinding binding = DataBindingUtil.setContentView(this, R.layout.note_detail);
        binding.setViewModel(viewModel);
    }
}