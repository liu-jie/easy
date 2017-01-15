package com.eirture.rxcommon.base;

import android.os.Environment;

/**
 * Created by eirture on 17-1-15.
 */

public class StorageUtils {

    public static boolean isExternalStorageWritable() {
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());

    }

    private StorageUtils() {
    }
}
