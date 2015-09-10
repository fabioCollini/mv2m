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
import retrofit.RestAdapter;

public class ObjectFactory {

    private static NoteLoaderService noteLoaderService;

    private static NoteSaverService noteSaverService;

    private static Executor backgroundExecutor;

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

    @NonNull public static Executor backgroundExecutor() {
        if (backgroundExecutor == null) {
            backgroundExecutor = Executors.newCachedThreadPool();
        }
        return backgroundExecutor;
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

    @VisibleForTesting
    public static void setNoteLoaderService(NoteLoaderService noteLoaderService) {
        ObjectFactory.noteLoaderService = noteLoaderService;
    }

    @VisibleForTesting
    public static void setNoteSaverService(NoteSaverService noteSaverService) {
        ObjectFactory.noteSaverService = noteSaverService;
    }

    @VisibleForTesting
    public static void setBackgroundExecutor(Executor backgroundExecutor) {
        ObjectFactory.backgroundExecutor = backgroundExecutor;
    }
}
