package it.cosenonjaviste.demomv2m.ui.list;

import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;

public class BindableViewHolder<B extends ViewDataBinding, T> extends RecyclerView.ViewHolder {

    protected final B binding;

    private int variableId;

    protected T item;

    public BindableViewHolder(B binding, int variableId) {
        super(binding.getRoot());
        this.binding = binding;
        this.variableId = variableId;
    }

    public void bind(T item) {
        this.item = item;
        binding.setVariable(variableId, item);
    }

    public T getItem() {
        return item;
    }
}
