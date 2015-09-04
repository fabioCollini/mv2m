package it.cosenonjaviste.core;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import it.cosenonjaviste.R;
import it.cosenonjaviste.model.Note;
import it.cosenonjaviste.model.NoteLoaderService;
import it.cosenonjaviste.model.NoteSaverService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class NoteViewModelTest {

    @Mock NoteView view;

    @Mock NoteLoaderService noteLoaderService;

    @Mock NoteSaverService noteSaverService;

    @Captor ArgumentCaptor<Note> captor;

    @InjectMocks NoteViewModel viewModel;

    @Test
    public void testLoadData() {
        when(noteLoaderService.load()).thenReturn(new Note("title", "text"));

        NoteModel model = viewModel.initAndResume(view);

        assertThat(model.getTitle().get()).isEqualTo("title");
        assertThat(model.getText().get()).isEqualTo("text");
    }

    @Test
    public void testSaveData() {
        when(noteLoaderService.load()).thenReturn(new Note("title", "text"));

        NoteModel model = viewModel.initAndResume(view);

        model.getTitle().set("newTitle");
        model.getText().set("newText");

        viewModel.save();

        verify(noteSaverService).save(captor.capture());
        assertThat(captor.getValue()).isEqualToComparingFieldByField(new Note("newTitle", "newText"));

        verify(view).showMessage(eq(R.string.note_saved));
    }
}