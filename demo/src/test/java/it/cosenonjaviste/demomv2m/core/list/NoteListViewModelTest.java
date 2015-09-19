package it.cosenonjaviste.demomv2m.core.list;

import android.databinding.Observable;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.concurrent.Executor;

import it.cosenonjaviste.demomv2m.core.TestExecutor;
import it.cosenonjaviste.demomv2m.model.Note;
import it.cosenonjaviste.demomv2m.model.NoteListResponse;
import it.cosenonjaviste.demomv2m.model.NoteLoaderService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class NoteListViewModelTest {

    @Mock NoteListView view;

    @Mock NoteLoaderService service;

    @Mock Observable.OnPropertyChangedCallback callback;

    @Spy Executor executor = new TestExecutor();

    @InjectMocks NoteListViewModel viewModel;

    @Test
    public void testLoad() {
        when(service.loadItems())
                .thenReturn(new NoteListResponse(new Note("1", "a"), new Note("2", "b")));

        NoteListModel model = viewModel.initAndResume(view);

        assertThat(model).isNotNull();
        assertThat(model.getItems()).isNotEmpty().hasSize(2);
    }

    @Test
    public void testError() {
        when(service.loadItems()).thenThrow(new RuntimeException());

        NoteListModel model = viewModel.initAndResume(view);

        assertThat(model.getError().get()).isTrue();
    }

    @Test
    public void testReloadAfterError() {
        when(service.loadItems())
                .thenThrow(new RuntimeException())
                .thenReturn(new NoteListResponse(new Note("1", "a"), new Note("2", "b")));

        NoteListModel model = viewModel.initAndResume(view);

        assertThat(model.getError().get()).isTrue();

        viewModel.reloadData();

        assertThat(model.getError().get()).isFalse();
        assertThat(model.getItems()).hasSize(2);
    }

    @Test
    public void testLoadingIndicator() {
        when(service.loadItems()).thenReturn(new NoteListResponse(new Note("1", "a"), new Note("2", "b")));
        NoteListModel model = new NoteListModel();
        viewModel.getLoading().addOnPropertyChangedCallback(callback);

        viewModel.initAndResume(model, view);

        verify(callback, times(2)).onPropertyChanged(eq(viewModel.getLoading()), anyInt());
    }
}