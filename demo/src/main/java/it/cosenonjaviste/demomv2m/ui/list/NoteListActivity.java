package it.cosenonjaviste.demomv2m.ui.list;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.ViewGroup;

import it.cosenonjaviste.demomv2m.BR;
import it.cosenonjaviste.demomv2m.R;
import it.cosenonjaviste.demomv2m.core.list.NoteListView;
import it.cosenonjaviste.demomv2m.core.list.NoteListViewModel;
import it.cosenonjaviste.demomv2m.databinding.NoteListBinding;
import it.cosenonjaviste.demomv2m.databinding.NoteListItemBinding;
import it.cosenonjaviste.demomv2m.model.Note;
import it.cosenonjaviste.demomv2m.ui.ObjectFactory;
import it.cosenonjaviste.mv2m.ViewModelActivity;

public class NoteListActivity extends ViewModelActivity<NoteListViewModel> implements NoteListView {
    @Override protected NoteListViewModel createViewModel() {
        return new NoteListViewModel(
                ObjectFactory.singleton().noteLoaderService(),
                ObjectFactory.singleton().backgroundExecutor(),
                ObjectFactory.singleton().uiExecutor());
    }

    @Override protected void onCreate(Bundle state) {
        super.onCreate(state);
        NoteListBinding binding = DataBindingUtil.setContentView(this, R.layout.note_list);
        binding.setViewModel(viewModel);
        binding.list.setLayoutManager(new LinearLayoutManager(this));
        binding.list.setAdapter(new BindableAdapter<Note>(viewModel.getModel().getItems()) {
            @Override public BindableViewHolder<ViewDataBinding, Note> onCreateViewHolder(ViewGroup parent, int viewType) {
                NoteListItemBinding binding = NoteListItemBinding.inflate(getLayoutInflater(), parent, false);
                return new BindableViewHolder<ViewDataBinding, Note>(binding, BR.item);
            }
        });
    }
}
