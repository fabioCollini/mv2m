package it.cosenonjaviste.core;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.concurrent.Executor;

import it.cosenonjaviste.R;
import it.cosenonjaviste.model.Note;
import it.cosenonjaviste.model.NoteLoaderService;
import it.cosenonjaviste.model.NoteSaverService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class NoteViewModelTest {

    @Mock NoteView view;

    @Mock NoteLoaderService noteLoaderService;

    @Mock NoteSaverService noteSaverService;

    @Spy Executor executor = new Executor() {
        @Override public void execute(Runnable command) {
            command.run();
        }
    };

    @InjectMocks NoteViewModel viewModel;

    @Before
    public void setUp() {
        when(noteLoaderService.load()).thenReturn(new Note(123, "title", "text"));
    }

    @Test
    public void testLoadData() {
        NoteModel model = viewModel.initAndResume(view);

        assertThat(model.getTitle().get()).isEqualTo("title");
        assertThat(model.getText().get()).isEqualTo("text");
    }

    @Test
    public void testValidation() {
        NoteModel model = viewModel.initAndResume(view);

        model.getTitle().set("");
        model.getText().set("");

        viewModel.save();

        assertThat(model.getTitleError().get()).isEqualTo(R.string.mandatory_field);
        assertThat(model.getTextError().get()).isEqualTo(R.string.mandatory_field);

        verify(noteSaverService, never()).save(anyLong(), anyString(), anyString());
        verify(view, never()).showMessage(anyInt());
    }

    @Test
    public void testSaveData() {
        NoteModel model = viewModel.initAndResume(view);

        model.getTitle().set("newTitle");
        model.getText().set("newText");

        viewModel.save();

        verify(noteSaverService).save(eq(123L), eq("newTitle"), eq("newText"));

        verify(view).showMessage(eq(R.string.note_saved));
    }
}