package com.eirture.easy.base;

import android.support.annotation.NonNull;

import com.eirture.easy.base.bus.DataEvent;
import com.eirture.easy.base.bus.ErrorEvent;
import com.eirture.easy.base.bus.SingleBus;

import rx.Observer;

/**
 * Created by eirture on 16-12-23.
 */

public class ObserverImpl<T> implements Observer<T> {
    private SingleBus bus;
    private Class<? extends DataEvent<T>> clazz;
    private int errorCode = Constant.ERROR_CODE_DEFAULT;

    public ObserverImpl(@NonNull SingleBus bus, Class<? extends DataEvent<T>> eventClass) {
        this.bus = bus;
        this.clazz = eventClass;
    }


    public ObserverImpl errorCode(int code) {
        errorCode = code;
        return this;
    }

    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable e) {
        bus.post(new ErrorEvent(errorCode, e.getMessage()));
    }

    @Override
    public void onNext(T t) {
        try {
            DataEvent<T> event = clazz.newInstance();
            bus.post(event);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
