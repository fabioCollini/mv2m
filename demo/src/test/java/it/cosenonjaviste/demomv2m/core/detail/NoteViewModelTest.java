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
package it.cosenonjaviste.demomv2m.core.detail;

import org.junit.Before;
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
import it.cosenonjaviste.demomv2m.core.MessageManager;
import it.cosenonjaviste.demomv2m.core.TestExecutor;
import it.cosenonjaviste.demomv2m.model.Note;
import it.cosenonjaviste.demomv2m.model.NoteLoaderService;
import it.cosenonjaviste.demomv2m.model.NoteSaverService;
import it.cosenonjaviste.demomv2m.model.SaveResponse;
import it.cosenonjaviste.mv2m.ActivityResult;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class NoteViewModelTest {

    @Mock NoteLoaderService noteLoaderService;

    @Mock NoteSaverService noteSaverService;

    @Mock MessageManager messageManager;

    @Captor ArgumentCaptor<Note> captor;

    @Spy Executor executor = new TestExecutor();

    @InjectMocks NoteViewModel viewModel;

    @Before
    public void setUp() {
        when(noteLoaderService.load(eq(TestData.ID_A))).thenReturn(TestData.noteA());
    }

    @Test
    public void testLoadData() {
        NoteModel model = viewModel.initAndResume(TestData.ID_A);

        assertThat(model.getTitle().get()).isEqualTo(TestData.TITLE_A);
        assertThat(model.getText().get()).isEqualTo(TestData.TEXT_A);
    }

    @Test
    public void testErrorLoadingData() {
        when(noteLoaderService.load(anyString()))
                .thenThrow(TestData.networkError());

        NoteModel model = viewModel.initAndResume(TestData.ID_A);

        verify(messageManager, never()).showMessage(anyInt());
        assertThat(model.getError().get()).isTrue();
    }

    @Test
    public void testReloadAfterError() {
        when(noteLoaderService.load(anyString()))
                .thenThrow(TestData.networkError())
                .thenReturn(TestData.noteA());

        NoteModel model = viewModel.initAndResume(TestData.ID_A);

        assertThat(model.getError().get()).isTrue();

        viewModel.reloadData();

        assertThat(model.getError().get()).isFalse();
        assertThat(model.getTitle().get()).isEqualTo(TestData.TITLE_A);
    }

    @Test
    public void testValidation() {
        NoteModel model = viewModel.initAndResume(TestData.ID_A);

        model.getTitle().set("");
        model.getText().set("");

        viewModel.save();

        assertThat(model.getTitleError().get()).isEqualTo(R.string.mandatory_field);
        assertThat(model.getTextError().get()).isEqualTo(R.string.mandatory_field);

        verify(noteSaverService, never()).save(anyString(), any(Note.class));
        verify(messageManager, never()).showMessage(anyInt());
    }

    @Test
    public void testSaveData() {
        NoteModel model = viewModel.initAndResume(TestData.ID_A);

        model.getTitle().set(TestData.NEW_TITLE);
        model.getText().set(TestData.NEW_TEXT);

        viewModel.save();

        verify(noteSaverService).save(eq(TestData.ID_A), captor.capture());

        assertThat(captor.getValue())
                .isEqualToComparingFieldByField(new Note(null, TestData.NEW_TITLE, TestData.NEW_TEXT));

        verify(messageManager).showMessage(eq(R.string.note_saved));
    }

    @Test
    public void testErrorSavingData() {
        when(noteSaverService.save(anyString(), any(Note.class)))
                .thenThrow(TestData.networkError());

        NoteModel model = viewModel.initAndResume(TestData.ID_A);

        model.getTitle().set(TestData.NEW_TITLE);
        model.getText().set(TestData.NEW_TEXT);

        viewModel.save();

        verify(messageManager).showMessage(eq(R.string.error_saving_note));
    }

    @Test
    public void testCreateNewNote() {
        when(noteSaverService.createNewNote(any(Note.class)))
                .thenReturn(new SaveResponse(TestData.NEW_ID));

        NoteModel model = viewModel.initAndResume();

        verify(noteLoaderService, never()).load(anyString());

        model.getTitle().set(TestData.NEW_TITLE);
        model.getText().set(TestData.NEW_TEXT);

        viewModel.save();

        verify(noteSaverService).createNewNote(captor.capture());

        assertThat(captor.getValue())
                .isEqualToComparingFieldByField(new Note(null, TestData.NEW_TITLE, TestData.NEW_TEXT));
        assertThat(model.getNoteId()).isEqualTo(TestData.NEW_ID);
    }

    @Test
    public void testOnBack() {
        when(noteSaverService.createNewNote(any(Note.class)))
                .thenReturn(new SaveResponse(TestData.NEW_ID));

        NoteModel model = viewModel.initAndResume();

        model.getTitle().set(TestData.NEW_TITLE);
        model.getText().set(TestData.NEW_TEXT);

        viewModel.save();
        ActivityResult result = viewModel.onBackPressed();

        assertThat(result).isNotNull();
        assertThat(result.getData())
                .isEqualToComparingFieldByField(new Note(TestData.NEW_ID, TestData.NEW_TITLE, TestData.NEW_TEXT));
        assertThat(result.isResultOk()).isTrue();
    }
}