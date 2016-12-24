package com.eirture.easy.base.utils;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by eirture on 16-12-23.
 */

public class Observables {
    private Observables() {
        throw new AssertionError("Observables is util class, can not instance");
    }

    public static <T> void UiThreadSubscribe(Observable<T> observable, Observer<? super T> observer) {
        observable.observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

}
