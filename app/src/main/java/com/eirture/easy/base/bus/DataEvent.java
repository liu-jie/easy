package com.eirture.easy.base.bus;

/**
 * Created by eirture on 16-12-6.
 */

public abstract class DataEvent<T> extends Event {
    public T data;
    public boolean isPrelude = false;

    public DataEvent() {
    }

    public DataEvent(T data) {
        this.data = data;
    }
}
