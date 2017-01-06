package com.eirture.easy.base.bus;

/**
 * Created by eirture on 16-12-23.
 */

public class BusMessage {
    public static final int SUCCESS_CODE = 1;

    public int code;
    public String message;

    public BusMessage(int code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String toString() {
        return "BusMessage{" +
                "code=" + code +
                ", message='" + message + '\'' +
                '}';
    }

    private static BusMessage NETWORK_UNAVAILABLE;

    public static BusMessage networkUnavailable() {
        if (NETWORK_UNAVAILABLE == null)
            NETWORK_UNAVAILABLE = new BusMessage(100, "Network unavailable");

        return NETWORK_UNAVAILABLE;
    }


}
