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
package it.cosenonjaviste.demomv2m.core.list;

import android.databinding.ObservableBoolean;
import android.support.annotation.NonNull;

import java.util.List;
import java.util.concurrent.Executor;

import it.cosenonjaviste.demomv2m.R;
import it.cosenonjaviste.demomv2m.core.Navigator;
import it.cosenonjaviste.demomv2m.model.Note;
import it.cosenonjaviste.demomv2m.model.NoteLoader;
import it.cosenonjaviste.mv2m.ActivityResult;
import it.cosenonjaviste.mv2m.ViewModel;

public class NoteListViewModel extends ViewModel<Void, NoteListModel> {

    private NoteLoader service;

    private ObservableBoolean loading = new ObservableBoolean();

    private final Executor backgroundExecutor;

    private final Executor uiExecutor;

    private Navigator navigator;

    public NoteListViewModel(NoteLoader service, Executor backgroundExecutor, Executor uiExecutor, Navigator navigator) {
        this.service = service;
        this.backgroundExecutor = backgroundExecutor;
        this.uiExecutor = uiExecutor;
        this.navigator = navigator;
    }

    @NonNull @Override public NoteListModel createModel() {
        return new NoteListModel();
    }

    @Override public void resume() {
        if (!loading.get() && !model.isLoaded()) {
            reloadData();
        }
    }

    public void reloadData() {
        loading.set(true);
        backgroundExecutor.execute(new Runnable() {
            @Override public void run() {
                try {
                    final List<Note> notes = service.loadItems().getResults();
                    uiExecutor.execute(new Runnable() {
                        @Override public void run() {
                            loading.set(false);
                            model.loadedData(notes);
                        }
                    });
                } catch (Exception e) {
                    uiExecutor.execute(new Runnable() {
                        @Override public void run() {
                            loading.set(false);
                            model.loadedWithError();
                        }
                    });
                }
            }
        });
    }

    public ObservableBoolean getLoading() {
        return loading;
    }

    public void openDetail(String objectId) {
        navigator.openDetail(activityHolder, objectId);
    }

    public void openCreateNewNote() {
        navigator.openDetail(activityHolder, null);
    }

    public void onResult(int requestCode, ActivityResult activityResult) {
        Note data = activityResult.getData();
        if (data != null) {
            model.updateItem(data);
        }
    }

    @Override public int getOptionMenuId() {
        return R.menu.list_menu;
    }

    @Override public boolean onOptionsItemSelected(int itemId) {
        if (itemId == R.id.new_note) {
            openCreateNewNote();
            return true;
        } else {
            return super.onOptionsItemSelected(itemId);
        }
    }
}
