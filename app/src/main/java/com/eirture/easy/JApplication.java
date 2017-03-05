package com.eirture.easy;

import android.support.multidex.MultiDexApplication;

import org.androidannotations.annotations.EApplication;

/**
 * Created by eirture on 16-12-28.
 */
@EApplication
public class JApplication extends MultiDexApplication {
    @Override
    public void onCreate() {
        super.onCreate();
    }
}
