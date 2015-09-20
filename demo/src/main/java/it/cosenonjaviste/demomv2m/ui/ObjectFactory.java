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
package it.cosenonjaviste.demomv2m.ui;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import it.cosenonjaviste.demomv2m.BuildConfig;
import it.cosenonjaviste.demomv2m.core.Navigator;
import it.cosenonjaviste.demomv2m.model.NoteLoaderService;
import it.cosenonjaviste.demomv2m.model.NoteSaverService;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;

public class ObjectFactory {

    protected Navigator navigator;

    protected NoteLoaderService noteLoaderService;

    protected NoteSaverService noteSaverService;

    protected Executor backgroundExecutor;

    protected Executor uiExecutor;

    private static ObjectFactory singleton;

    public static ObjectFactory singleton() {
        if (singleton == null) {
            singleton = new ObjectFactory();
        }
        return singleton;
    }

    @VisibleForTesting
    public static void setSingleton(ObjectFactory singleton) {
        ObjectFactory.singleton = singleton;
    }

    @NonNull public Navigator navigator() {
        if (navigator == null) {
            navigator = new ActivityNavigator();
        }
        return navigator;
    }

    @NonNull public NoteSaverService noteSaverService() {
        if (noteSaverService == null) {
            noteSaverService = createService(NoteSaverService.class);
        }
        return noteSaverService;
    }

    @NonNull public NoteLoaderService noteLoaderService() {
        if (noteLoaderService == null) {
            noteLoaderService = createService(NoteLoaderService.class);
        }
        return noteLoaderService;
    }

    private static <T> T createService(Class<T> serviceClass) {
        //    curl -X GET \
//            -H "Content-Type: application/json" \
//    https://api.parse.com/1/classes/Note

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setRequestInterceptor(new RequestInterceptor() {
                    @Override public void intercept(RequestFacade request) {
                        request.addHeader("X-Parse-Application-Id", "kRXsTF3e1IijVicMEUNDce2rmpafKfigrwzKRBwF");
                        request.addHeader("X-Parse-REST-API-Key", "6hcfLUihLi68T0SqD2KeIBfmv6ILs8VeEegxS02X");
                    }
                })
                .setEndpoint("https://api.parse.com/1/classes/")
                .build();
        if (BuildConfig.DEBUG) {
            restAdapter.setLogLevel(RestAdapter.LogLevel.FULL);
        }
        return restAdapter.create(serviceClass);
    }

    @NonNull public Executor backgroundExecutor() {
        if (backgroundExecutor == null) {
            backgroundExecutor = Executors.newCachedThreadPool();
        }
        return backgroundExecutor;
    }

    @NonNull public Executor uiExecutor() {
        if (uiExecutor == null) {
            uiExecutor = new Executor() {
                @Override public void execute(Runnable command) {
                    new Handler(Looper.getMainLooper()).post(command);
                }
            };
        }
        return uiExecutor;
    }
}
