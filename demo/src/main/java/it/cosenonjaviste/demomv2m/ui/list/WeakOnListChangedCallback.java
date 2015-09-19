package it.cosenonjaviste.demomv2m.ui.list;

import android.databinding.ObservableList;
import android.databinding.ObservableList.OnListChangedCallback;

import java.lang.ref.WeakReference;

public class WeakOnListChangedCallback<T extends ObservableList> extends OnListChangedCallback<T> {

    private WeakReference<OnListChangedCallback<T>> ref;

    public WeakOnListChangedCallback(OnListChangedCallback<T> delegate) {
        this.ref = new WeakReference<>(delegate);
    }

    @Override public void onChanged(T sender) {
        if (ref.get() != null) {
            ref.get().onChanged(sender);
        }
    }

    @Override public void onItemRangeChanged(T sender, int positionStart, int itemCount) {
        if (ref.get() != null) {
            ref.get().onItemRangeChanged(sender, positionStart, itemCount);
        }
    }

    @Override public void onItemRangeInserted(T sender, int positionStart, int itemCount) {
        if (ref.get() != null) {
            ref.get().onItemRangeInserted(sender, positionStart, itemCount);
        }
    }

    @Override public void onItemRangeMoved(T sender, int fromPosition, int toPosition, int itemCount) {
        if (ref.get() != null) {
            ref.get().onItemRangeMoved(sender, fromPosition, toPosition, itemCount);
        }
    }

    @Override public void onItemRangeRemoved(T sender, int positionStart, int itemCount) {
        if (ref.get() != null) {
            ref.get().onItemRangeRemoved(sender, positionStart, itemCount);
        }
    }
}