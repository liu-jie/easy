package com.eirture.easy.base.utils;

/**
 * Created by eirture on 17-3-5.
 */

public class Strings {

    public static boolean checkString(int minLength, String str) {
        return str != null && str.length() >= minLength;
    }


    private Strings() {
    }
}
