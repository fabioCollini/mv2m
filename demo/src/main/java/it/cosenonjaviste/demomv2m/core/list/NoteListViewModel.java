package it.cosenonjaviste.demomv2m.core.list;

import java.util.List;
import java.util.concurrent.Executor;

import it.cosenonjaviste.demomv2m.model.Note;
import it.cosenonjaviste.demomv2m.model.NoteLoaderService;
import it.cosenonjaviste.mv2m.ViewModel;

public class NoteListViewModel extends ViewModel<NoteListModel, NoteListView> {

    private NoteLoaderService service;

    private final Executor backgroundExecutor;

    private final Executor uiExecutor;

    public NoteListViewModel(NoteLoaderService service, Executor backgroundExecutor, Executor uiExecutor) {
        this.service = service;
        this.backgroundExecutor = backgroundExecutor;
        this.uiExecutor = uiExecutor;
    }

    @Override public NoteListModel createDefaultModel() {
        return new NoteListModel();
    }

    @Override public void resume() {
        reloadData();
    }

    public void reloadData() {
        getModel().getLoading().set(true);
        backgroundExecutor.execute(new Runnable() {
            @Override public void run() {
                try {
                    final List<Note> notes = service.loadItems();
                    uiExecutor.execute(new Runnable() {
                        @Override public void run() {
                            NoteListViewModel.this.getModel().loadedData(notes);
                        }
                    });
                } catch (Exception e) {
                    uiExecutor.execute(new Runnable() {
                        @Override public void run() {
                            getModel().getError().set(true);
                        }
                    });
                }
            }
        });
    }
}
