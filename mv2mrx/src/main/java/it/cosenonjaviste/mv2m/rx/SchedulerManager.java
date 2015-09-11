package it.cosenonjaviste.mv2m.rx;

import rx.Observable;

public abstract class SchedulerManager {
    public abstract <T> Observable<T> bindObservable(Observable<T> observable);

    public static final SchedulerManager IDENTITY = new SchedulerManager() {
        @Override public <T> Observable<T> bindObservable(Observable<T> observable) {
            return observable;
        }
    };
}
