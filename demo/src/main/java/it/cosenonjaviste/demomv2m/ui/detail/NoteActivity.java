package it.cosenonjaviste.demomv2m.ui.detail;

import android.databinding.DataBindingUtil;
import android.os.Bundle;

import it.cosenonjaviste.demomv2m.R;
import it.cosenonjaviste.demomv2m.core.detail.NoteViewModel;
import it.cosenonjaviste.demomv2m.databinding.NoteDetailBinding;
import it.cosenonjaviste.demomv2m.ui.ObjectFactory;
import it.cosenonjaviste.demomv2m.ui.SnackbarMessageManager;
import it.cosenonjaviste.mv2m.ViewModelActivity;

public class NoteActivity extends ViewModelActivity<NoteViewModel> {

    @Override public NoteViewModel createViewModel() {
        return new NoteViewModel(
                ObjectFactory.singleton().backgroundExecutor(),
                ObjectFactory.singleton().uiExecutor(),
                ObjectFactory.singleton().noteLoaderService(),
                ObjectFactory.singleton().noteSaverService(),
                new SnackbarMessageManager());
    }

    @Override protected void onCreate(Bundle state) {
        super.onCreate(state);
        NoteDetailBinding binding = DataBindingUtil.setContentView(this, R.layout.note_detail);
        binding.setViewModel(viewModel);
    }
}