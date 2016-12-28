package com.eirture.easy.base;

import android.support.annotation.NonNull;

import com.eirture.easy.base.bus.DataEvent;
import com.eirture.easy.base.bus.Error;
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

    // 通知现在开始
    public ObserverImpl<T> prelude() {
        try {
            DataEvent event = clazz.newInstance();
            event.isPrelude = true;
            bus.post(event);
        } catch (InstantiationException e) {
//            e.printStackTrace();
            System.err.println(e.toString());
        } catch (IllegalAccessException e) {
//            e.printStackTrace();
            System.err.println(e.toString());
        }

        return this;
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
        bus.post(new Error(errorCode, e.getMessage()));
    }

    @Override
    public void onNext(T t) {
        DataEvent<T> event = generateEvent(t);
        bus.post(event);

    }

    private DataEvent<T> generateEvent(T t) {
        DataEvent<T> event = null;
        try {
            event = clazz.newInstance();
            event.data = t;
            event.successful = true;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return event;
    }
}
