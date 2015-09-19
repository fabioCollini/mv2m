package it.cosenonjaviste.mv2m;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

public class ViewModelRetainedFragment<VM extends ViewModel<?, ?>> extends Fragment {

    public static final String TAG = ViewModelRetainedFragment.class.getName();

    private VM viewModel;

    public static <VM extends ViewModel<?, ?>> VM getOrCreate(Fragment fragment, Bundle state, String fragmentTag, ViewModelManager.Factory<VM> factory) {
        ViewModelRetainedFragment<VM> retainedFragment = getOrCreateFragment(fragment.getFragmentManager(), TAG + fragmentTag);
        retainedFragment.viewModel = ViewModelManager.init(retainedFragment.viewModel, state, fragment.getArguments(), factory);
        return retainedFragment.viewModel;
    }

    public static <VM extends ViewModel<?, ?>> VM getOrCreate(AppCompatActivity activity, Bundle state, String fragmentTag, ViewModelManager.Factory<VM> factory) {
        ViewModelRetainedFragment<VM> retainedFragment = getOrCreateFragment(activity.getSupportFragmentManager(), TAG + fragmentTag);
        retainedFragment.viewModel = ViewModelManager.init(retainedFragment.viewModel, state, activity.getIntent().getExtras(), factory);
        return retainedFragment.viewModel;
    }

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    public static <P extends ViewModel<?, ?>> ViewModelRetainedFragment<P> getOrCreateFragment(FragmentManager fragmentManager, String tag) {
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