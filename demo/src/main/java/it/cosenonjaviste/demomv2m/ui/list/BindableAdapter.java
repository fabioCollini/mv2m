package it.cosenonjaviste.demomv2m.ui.list;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.support.v7.widget.RecyclerView;

public abstract class BindableAdapter<T> extends RecyclerView.Adapter<BindableViewHolder<?, T>> {

    private ObservableArrayList<T> items;

    public BindableAdapter(ObservableArrayList<T> items) {
        this.items = items;
        items.addOnListChangedCallback(new WeakOnListChangedCallback<>(new ObservableList.OnListChangedCallback<ObservableList<T>>() {
            @Override public void onChanged(ObservableList<T> sender) {
                notifyDataSetChanged();
            }

            @Override public void onItemRangeChanged(ObservableList<T> sender, int positionStart, int itemCount) {
                notifyItemRangeChanged(positionStart, itemCount);
            }

            @Override public void onItemRangeInserted(ObservableList<T> sender, int positionStart, int itemCount) {
                notifyItemRangeInserted(positionStart, itemCount);
            }

            @Override public void onItemRangeMoved(ObservableList<T> sender, int fromPosition, int toPosition, int itemCount) {
                notifyDataSetChanged();
            }

            @Override public void onItemRangeRemoved(ObservableList<T> sender, int positionStart, int itemCount) {
                notifyItemRangeRemoved(positionStart, itemCount);
            }
        }));
    }

    @Override public void onBindViewHolder(BindableViewHolder<?, T> viewHolder, int i) {
        viewHolder.bind(items.get(i));
    }

    @Override public int getItemCount() {
        return items.size();
    }
}
