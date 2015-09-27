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


import android.os.Parcelable;

import it.cosenonjaviste.mv2m.ViewModel;
import rx.Observable;
import rx.functions.Action1;

public abstract class RxViewModel<A, M extends Parcelable> extends ViewModel<A, M> {

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

    public <T> void subscribe(Action1<Boolean> loadingAction, Observable<T> observable, Action1<? super T> onNext, Action1<Throwable> onError) {
        rxHolder.subscribe(loadingAction, observable, onNext, onError);
    }

    public <T> void subscribe(Observable<T> observable, Action1<? super T> onNext, Action1<Throwable> onError) {
        rxHolder.subscribe(observable, onNext, onError);
    }
}