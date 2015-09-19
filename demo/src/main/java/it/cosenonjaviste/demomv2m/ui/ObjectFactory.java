package it.cosenonjaviste.demomv2m.ui;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import it.cosenonjaviste.demomv2m.BuildConfig;
import it.cosenonjaviste.demomv2m.model.NoteLoaderService;
import it.cosenonjaviste.demomv2m.model.NoteSaverService;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;

public class ObjectFactory {

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
