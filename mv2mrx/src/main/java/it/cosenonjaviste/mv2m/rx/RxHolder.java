/*
 *  Copyright 2015 Fabio Collini.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package it.cosenonjaviste.mv2m.rx;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.observables.ConnectableObservable;
import rx.subscriptions.CompositeSubscription;

public class RxHolder {
    private SchedulerManager schedulerManager;

    private final CompositeSubscription connectableSubscriptions = new CompositeSubscription();

    private CompositeSubscription subscriptions = new CompositeSubscription();

    protected final List<ObservableWithObserver> observables = new ArrayList<>();

    public RxHolder(SchedulerManager schedulerManager) {
        this.schedulerManager = schedulerManager;
    }

    public <T> void subscribe(Observable<T> observable, Action1<? super T> onNext, Action1<Throwable> onError) {
        subscribe(observable, onNext, onError, null);
    }

    public <T> void subscribe(Observable<T> observable, final Action1<? super T> onNext, final Action1<Throwable> onError, final Action0 onCompleted) {
        ConnectableObservable<T> replay = observable.compose(new Observable.Transformer<T, T>() {
            @Override public Observable<T> call(Observable<T> observable1) {
                return schedulerManager.bindObservable(observable1);
            }
        }).replay();
        connectableSubscriptions.add(replay.connect());
        ObservableWithObserver<T> observableWithObserver = new ObservableWithObserver<>(replay, new Observer<T>() {
            @Override public void onCompleted() {
                if (onCompleted != null) {
                    onCompleted.call();
                }
            }

            @Override public void onError(Throwable e) {
                if (onError != null) {
                    onError.call(e);
                }
            }

            @Override public void onNext(T t) {
                if (onNext != null) {
                    onNext.call(t);
                }
            }
        });
        observables.add(observableWithObserver);
        subscribe(observableWithObserver);
    }


    private <T> void subscribe(final ObservableWithObserver<T> observableWithObserver) {
        subscriptions.add(
                observableWithObserver.observable
                        .finallyDo(new Action0() {
                            @Override public void call() {
                                observables.remove(observableWithObserver);
                            }
                        })
                        .subscribe(observableWithObserver.observer)
        );
    }

    public void resubscribePendingObservable() {
        ArrayList<ObservableWithObserver> observableCopy = new ArrayList<>(observables);
        for (ObservableWithObserver<?> observableWithObserver : observableCopy) {
            subscribe(observableWithObserver);
        }
    }

    public void pause() {
        subscriptions.unsubscribe();
        subscriptions = new CompositeSubscription();
    }

    public void destroy() {
        connectableSubscriptions.unsubscribe();
    }

    public CompositeSubscription getSubscriptions() {
        return subscriptions;
    }

    private static class ObservableWithObserver<T> {
        public final ConnectableObservable<T> observable;

        public final Observer<T> observer;

        public ObservableWithObserver(ConnectableObservable<T> observable, Observer<T> observer) {
            this.observable = observable;
            this.observer = observer;
        }
    }
}