package it.cosenonjaviste.androidtest.utils;

import rx.functions.Action1;

public class TestUtils {

    public static <T> Action1<T> sleepAction() {
        return o -> sleep(1);
    }

    public static void sleep(int seconds) {
//        try {
//            Thread.sleep(seconds * 1000);
//        } catch (InterruptedException ignored) {
//        }
    }
}
