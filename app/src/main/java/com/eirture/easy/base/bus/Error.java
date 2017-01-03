package com.eirture.easy.base.bus;

/**
 * Created by eirture on 16-12-23.
 */

public class Error {
    public int code;
    public String message;

    public Error(int code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String toString() {
        return "Error{" +
                "code=" + code +
                ", message='" + message + '\'' +
                '}';
    }

    private static Error NETWORK_UNAVAILABLE;

    public static Error networkUnavailable() {
        if (NETWORK_UNAVAILABLE == null)
            NETWORK_UNAVAILABLE = new Error(100, "Network unavailable");

        return NETWORK_UNAVAILABLE;
    }
}
