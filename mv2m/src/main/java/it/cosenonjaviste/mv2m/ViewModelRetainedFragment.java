package it.cosenonjaviste.mv2m;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

public class ViewModelRetainedFragment<VM extends ViewModel<?>> extends Fragment {

    public static final String TAG = ViewModelRetainedFragment.class.getName();

    VM viewModel;

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    public static <VM extends ViewModel<?>> VM getOrCreate(String fragmentTag, ViewModelContainer<VM> container, FragmentManager fragmentManager) {
        ViewModelRetainedFragment<VM> retainedFragment = getOrCreateFragment(fragmentManager, TAG + fragmentTag);
        VM viewModel = retainedFragment.viewModel;
        if (viewModel == null) {
            viewModel = container.createViewModel();
            retainedFragment.viewModel = viewModel;
        }
        return viewModel;
    }

    private static <P extends ViewModel<?>> ViewModelRetainedFragment<P> getOrCreateFragment(FragmentManager fragmentManager, String tag) {
        ViewModelRetainedFragment<P> fragment = (ViewModelRetainedFragment<P>) fragmentManager.findFragmentByTag(tag);
        if (fragment == null) {
            fragment = new ViewModelRetainedFragment<>();
            fragmentManager.beginTransaction().add(fragment, tag).commit();
        }
        return fragment;
    }

    @Override public void onDestroy() {
        super.onDestroy();
        viewModel.destroy();
    }
}