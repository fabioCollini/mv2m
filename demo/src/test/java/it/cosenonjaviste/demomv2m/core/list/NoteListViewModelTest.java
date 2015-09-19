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

import it.cosenonjaviste.demomv2m.core.Navigator;
import it.cosenonjaviste.demomv2m.core.TestExecutor;
import it.cosenonjaviste.demomv2m.core.detail.NoteModel;
import it.cosenonjaviste.demomv2m.model.Note;
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

    @Captor ArgumentCaptor<NoteModel> captor;

    @Test
    public void testLoad() {
        when(service.loadItems())
                .thenReturn(new NoteListResponse(new Note("1", "a"), new Note("2", "b")));

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
                .thenReturn(new NoteListResponse(new Note("1", "a"), new Note("2", "b")));

        NoteListModel model = viewModel.initAndResume();

        assertThat(model.getError().get()).isTrue();

        viewModel.reloadData();

        assertThat(model.getError().get()).isFalse();
        assertThat(model.getItems()).hasSize(2);
    }

    @Test
    public void testLoadingIndicator() {
        when(service.loadItems()).thenReturn(new NoteListResponse(new Note("1", "a"), new Note("2", "b")));
        viewModel.getLoading().addOnPropertyChangedCallback(callback);

        viewModel.initAndResume();

        verify(callback, times(2)).onPropertyChanged(eq(viewModel.getLoading()), anyInt());
    }

    @Test
    public void testOpenDetail() {
        Note note = new Note("1", "a");
        when(service.loadItems()).thenReturn(new NoteListResponse(note, new Note("2", "b")));

        viewModel.initAndResume();
        viewModel.openDetail(note);

        verify(navigator).openDetail(captor.capture());
        assertThat(captor.getValue().getNoteId()).isEqualTo("1");
    }

    @Test
    public void testReturnFromDetail() {
        Note note = new Note("1", "a");
        when(service.loadItems()).thenReturn(new NoteListResponse(note, new Note("2", "b")));

        NoteListModel model = viewModel.initAndResume();
        viewModel.openDetail(note);
        viewModel.onResult(Navigator.OPEN_DETAIL, new ActivityResult(true, new Note("1", "abc", "a")));
        assertThat(model.getItems().get(0).getTitle()).isEqualTo("abc");
    }

    @Test
    public void testReturnFromDetailAfterCreation() {
        Note note = new Note("1", "a");
        when(service.loadItems()).thenReturn(new NoteListResponse(note, new Note("2", "b")));

        NoteListModel model = viewModel.initAndResume();
        viewModel.openCreateNewNote();
        viewModel.onResult(Navigator.OPEN_DETAIL, new ActivityResult(true, new Note("3", "abc", "a")));

        assertThat(model.getItems().get(2).getTitle()).isEqualTo("abc");
    }

    @Test
    public void testCreateNewNote() {
        when(service.loadItems()).thenReturn(new NoteListResponse());

        viewModel.initAndResume();
        viewModel.openCreateNewNote();

        verify(navigator).openDetail(captor.capture());
        assertThat(captor.getValue().getNoteId()).isNull();
    }
}