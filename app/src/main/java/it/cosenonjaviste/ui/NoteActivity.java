package it.cosenonjaviste.ui;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.Snackbar;

import it.cosenonjaviste.R;
import it.cosenonjaviste.core.NoteView;
import it.cosenonjaviste.core.NoteViewModel;
import it.cosenonjaviste.databinding.NoteDetailBinding;
import it.cosenonjaviste.lib.ViewModelActivity;

public class NoteActivity extends ViewModelActivity<NoteViewModel> implements NoteView {

    private NoteDetailBinding binding;

    @Override protected NoteViewModel createViewModel() {
        return new NoteViewModel(
                ObjectFactory.backgroundExecutor(),
                ObjectFactory.uiExecutor(),
                ObjectFactory.noteLoaderService(),
                ObjectFactory.noteSaverService());
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