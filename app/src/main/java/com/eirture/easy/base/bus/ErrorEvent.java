package com.eirture.easy.base.bus;

/**
 * Created by eirture on 16-12-23.
 */

public class ErrorEvent extends Event {
    public int code;
    public String message;

    public ErrorEvent() {
    }

    public ErrorEvent(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
