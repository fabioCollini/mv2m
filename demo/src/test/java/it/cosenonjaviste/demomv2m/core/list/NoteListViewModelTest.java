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

import android.databinding.Observable;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.concurrent.Executor;

import it.cosenonjaviste.demomv2m.R;
import it.cosenonjaviste.demomv2m.TestData;
import it.cosenonjaviste.demomv2m.core.Navigator;
import it.cosenonjaviste.demomv2m.core.TestExecutor;
import it.cosenonjaviste.demomv2m.model.NoteListResponse;
import it.cosenonjaviste.demomv2m.model.NoteLoaderService;
import it.cosenonjaviste.mv2m.ActivityResult;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class NoteListViewModelTest {

    @Mock NoteLoaderService service;

    @Mock Navigator navigator;

    @Mock Observable.OnPropertyChangedCallback callback;

    @Spy Executor executor = new TestExecutor();

    @InjectMocks NoteListViewModel viewModel;

    @Captor ArgumentCaptor<String> captor;

    @Test
    public void testLoad() {
        when(service.loadItems())
                .thenReturn(TestData.response());

        NoteListModel model = viewModel.initAndResume();

        assertThat(model).isNotNull();
        assertThat(model.getItems()).isNotEmpty().hasSize(2);
    }

    @Test
    public void testError() {
        when(service.loadItems()).thenThrow(new RuntimeException());

        NoteListModel model = viewModel.initAndResume();

        assertThat(model.getError().get()).isTrue();
    }

    @Test
    public void testReloadAfterError() {
        when(service.loadItems())
                .thenThrow(new RuntimeException())
                .thenReturn(TestData.response());

        NoteListModel model = viewModel.initAndResume();

        assertThat(model.getError().get()).isTrue();

        viewModel.reloadData();

        assertThat(model.getError().get()).isFalse();
        assertThat(model.getItems()).hasSize(2);
    }

    @Test
    public void testLoadingIndicator() {
        when(service.loadItems()).thenReturn(TestData.response());
        viewModel.getLoading().addOnPropertyChangedCallback(callback);

        viewModel.initAndResume();

        verify(callback, times(2)).onPropertyChanged(eq(viewModel.getLoading()), anyInt());
    }

    @Test
    public void testOpenDetail() {
        when(service.loadItems()).thenReturn(TestData.response());

        viewModel.initAndResume();
        viewModel.openDetail(TestData.ID_1);

        verify(navigator).openDetail(captor.capture());
        assertThat(captor.getValue()).isEqualTo(TestData.ID_1);
    }

    @Test
    public void testReturnFromDetail() {
        when(service.loadItems()).thenReturn(TestData.response());

        NoteListModel model = viewModel.initAndResume();
        viewModel.openDetail(TestData.ID_1);
        viewModel.onResult(Navigator.OPEN_DETAIL, new ActivityResult(true, TestData.note1("abc")));
        assertThat(model.getItems().get(0).getTitle()).isEqualTo("abc");
    }

    @Test
    public void testReturnFromDetailAfterCreation() {
        when(service.loadItems()).thenReturn(TestData.response());

        NoteListModel model = viewModel.initAndResume();
        viewModel.openCreateNewNote();
        viewModel.onResult(Navigator.OPEN_DETAIL, new ActivityResult(true, TestData.note3()));

        assertThat(model.getItems().get(2).getTitle()).isEqualTo("c");
    }

    @Test
    public void testCreateNewNote() {
        when(service.loadItems()).thenReturn(new NoteListResponse());

        viewModel.initAndResume();
        viewModel.onOptionsItemSelected(R.id.new_note);

        verify(navigator).openDetail(captor.capture());
        assertThat(captor.getValue()).isNull();
    }
}