package it.cosenonjaviste.ui;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import it.cosenonjaviste.BuildConfig;
import it.cosenonjaviste.model.NoteLoaderService;
import it.cosenonjaviste.model.NoteSaverService;
import retrofit.RestAdapter;

public class ObjectFactory {

    private static NoteLoaderService noteLoaderService;

    private static NoteSaverService noteSaverService;

    private static ExecutorService executorService;

    private static Executor uiExecutor;

    @NonNull public static NoteSaverService noteSaverService() {
        if (noteSaverService == null) {
            noteSaverService = createService(NoteSaverService.class);
        }
        return noteSaverService;
    }

    @NonNull public static NoteLoaderService noteLoaderService() {
        if (noteLoaderService == null) {
            noteLoaderService = createService(NoteLoaderService.class);
        }
        return noteLoaderService;
    }

    private static <T> T createService(Class<T> serviceClass) {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint("https://raw.githubusercontent.com/fabioCollini/DemoMVVM/master/")
                .build();
        if (BuildConfig.DEBUG) {
            restAdapter.setLogLevel(RestAdapter.LogLevel.FULL);
        }
        return restAdapter.create(serviceClass);
    }

    @NonNull public static ExecutorService backgroundExecutor() {
        if (executorService == null) {
            executorService = Executors.newCachedThreadPool();
        }
        return executorService;
    }

    @NonNull public static Executor uiExecutor() {
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
