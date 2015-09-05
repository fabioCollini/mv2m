package it.cosenonjaviste.ui;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.Snackbar;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import it.cosenonjaviste.R;
import it.cosenonjaviste.core.NoteView;
import it.cosenonjaviste.core.NoteViewModel;
import it.cosenonjaviste.databinding.NoteDetailBinding;
import it.cosenonjaviste.lib.ViewModelActivity;
import it.cosenonjaviste.model.Note;
import it.cosenonjaviste.model.NoteLoaderService;
import it.cosenonjaviste.model.NoteSaverService;

public class NoteActivity extends ViewModelActivity<NoteViewModel> implements NoteView {

    private NoteDetailBinding binding;

    @Override protected NoteViewModel createViewModel() {
        return new NoteViewModel(
                Executors.newCachedThreadPool(),
                new Executor() {
                    @Override public void execute(Runnable command) {
                        runOnUiThread(command);
                    }
                },
                new NoteLoaderService() {
                    @Override public Note load() {
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        return new Note("aaaa", "bbb");
                    }
                }, new NoteSaverService() {
            @Override public void save(Note note) {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override protected void onCreate(Bundle state) {
        super.onCreate(state);
        binding = DataBindingUtil.setContentView(this, R.layout.note_detail);
        binding.setViewModel(viewModel);
    }

    @Override public void showMessage(int message) {
        Snackbar.make(binding.getRoot(), message, Snackbar.LENGTH_LONG).show();
    }
}