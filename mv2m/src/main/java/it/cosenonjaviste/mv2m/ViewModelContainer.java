package it.cosenonjaviste.mv2m;

import android.os.Parcelable;

public interface ViewModelContainer<VM extends ViewModel<?>> {
    VM createViewModel();

    String getFragmentTag(Parcelable model);
}
