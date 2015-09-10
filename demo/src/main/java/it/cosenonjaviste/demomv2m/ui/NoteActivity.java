package it.cosenonjaviste.demomv2m.ui;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.Snackbar;

import it.cosenonjaviste.demomv2m.R;
import it.cosenonjaviste.demomv2m.core.NoteView;
import it.cosenonjaviste.demomv2m.core.NoteViewModel;
import it.cosenonjaviste.demomv2m.databinding.NoteDetailBinding;
import it.cosenonjaviste.mv2m.ViewModelActivity;

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