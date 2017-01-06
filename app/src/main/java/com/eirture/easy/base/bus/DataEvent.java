package com.eirture.easy.base.bus;

/**
 * Created by eirture on 16-12-6.
 */

public abstract class DataEvent<T> extends Event {
    public T data;
    public BusMessage errorMessage;
    private boolean isPrelude;

    public boolean isPrelude() {
        return isPrelude;
    }

    public <E extends DataEvent> E success(T data) {
        isPrelude = false;
        successful = true;
        this.data = data;
        return (E) this;
    }

    public <E extends DataEvent> E error(BusMessage errorMessage) {
        isPrelude = false;
        successful = false;
        this.errorMessage = errorMessage;
        return (E) this;
    }

    public <E extends DataEvent> E prelude() {
        isPrelude = true;
        return (E) this;
    }

}
