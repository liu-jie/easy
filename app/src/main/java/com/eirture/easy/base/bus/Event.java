package com.eirture.easy.base.bus;

/**
 * Created by eirture on 16-12-6.
 */

public abstract class Event {
    protected boolean successful;

    public Event() {
    }

    public boolean successful() {
        return successful;
    }
}
