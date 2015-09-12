package it.cosenonjaviste.demomv2m.core.list;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.concurrent.Executor;

import it.cosenonjaviste.demomv2m.core.TestExecutor;
import it.cosenonjaviste.demomv2m.model.Note;
import it.cosenonjaviste.demomv2m.model.NoteLoaderService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class NoteListViewModelTest {

    @Mock NoteListView view;

    @Mock NoteLoaderService service;

    @Spy Executor executor = new TestExecutor();

    @InjectMocks NoteListViewModel viewModel;

    @Test
    public void testLoad() {
        when(service.loadItems()).thenReturn(Arrays.asList(new Note(1, "a"), new Note(2, "b")));

        NoteListModel model = viewModel.initAndResume(view);

        assertThat(model).isNotNull();
        assertThat(model.getItems()).isNotEmpty().hasSize(2);
    }
}