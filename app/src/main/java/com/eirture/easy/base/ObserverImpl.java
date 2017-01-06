package com.eirture.easy.base;

import android.support.annotation.NonNull;

import com.eirture.easy.base.bus.BusMessage;
import com.eirture.easy.base.bus.DataEvent;
import com.eirture.easy.base.bus.SingleBus;

import rx.Observer;

/**
 * Created by eirture on 16-12-23.
 */

public class ObserverImpl<T> implements Observer<T> {
    private SingleBus bus;
    private Class<? extends DataEvent<T>> clazz;
    private int errorCode = Constant.ERROR_CODE_DEFAULT;
    private DataEvent<T> event;

    public ObserverImpl(@NonNull SingleBus bus, Class<? extends DataEvent<T>> eventClass) {
        this.bus = bus;
        this.clazz = eventClass;

        try {
            event = clazz.newInstance();
        } catch (InstantiationException e) {
            System.err.println(e.toString());
        } catch (IllegalAccessException e) {
            System.err.println(e.toString());
        }

    }

    // 通知现在开始
    public ObserverImpl<T> prelude() {
        try {
            DataEvent event = clazz.newInstance().prelude();
            bus.post(event);
        } catch (InstantiationException e) {
            System.err.println(e.toString());
        } catch (IllegalAccessException e) {
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
        bus.post(event.error(new BusMessage(errorCode, e.getMessage())));
    }

    @Override
    public void onNext(T t) {
        bus.post(event.success(t));

    }
}
