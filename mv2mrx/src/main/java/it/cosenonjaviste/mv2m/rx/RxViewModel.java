package it.cosenonjaviste.mv2m.rx;


import android.os.Parcelable;

import it.cosenonjaviste.mv2m.ViewModel;
import rx.Observable;
import rx.functions.Action1;

public abstract class RxViewModel<M extends Parcelable, V> extends ViewModel<M> {

    private RxHolder rxHolder;

    public RxViewModel(SchedulerManager schedulerManager) {
        rxHolder = new RxHolder(schedulerManager != null ? schedulerManager : SchedulerManager.IDENTITY);
    }

    @Override public void resume() {
        rxHolder.resubscribePendingObservable();
    }

    @Override public void pause() {
        rxHolder.pause();
    }

    @Override public void destroy() {
        rxHolder.destroy();
    }

    public <T> void subscribe(Observable<T> observable, Action1<? super T> onNext, Action1<Throwable> onError) {
        rxHolder.subscribe(observable, onNext, onError);
    }
}