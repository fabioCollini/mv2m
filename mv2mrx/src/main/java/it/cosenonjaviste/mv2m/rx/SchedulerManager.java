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

import rx.Observable;

public abstract class SchedulerManager {
    public abstract <T> Observable<T> bindObservable(Observable<T> observable);

    public static final SchedulerManager IDENTITY = new SchedulerManager() {
        @Override public <T> Observable<T> bindObservable(Observable<T> observable) {
            return observable;
        }
    };
}
