package com.eirture.rxcommon.base;

/**
 * Created by eirture on 17-1-3.
 */

public final class Preconditions {
    public static void checkArgument(boolean assertion, String message) {
        if (!assertion) {
            throw new IllegalArgumentException(message);
        }
    }

    public static <T> T checkNotNull(T value, String message) {
        if (value == null) {
            throw new NullPointerException(message);
        }
        return value;
    }

    public static <T> T checkNotNull(T value) {
        if (value == null) {
            throw new NullPointerException();
        }

        return value;
    }

    private Preconditions() {
        throw new AssertionError("No instances.");
    }
}