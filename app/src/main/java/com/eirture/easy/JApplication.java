package com.eirture.easy;

import android.support.multidex.MultiDexApplication;

import com.avos.avoscloud.AVOSCloud;

import org.androidannotations.annotations.EApplication;

/**
 * Created by eirture on 16-12-28.
 */
@EApplication
public class JApplication extends MultiDexApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        AVOSCloud.initialize(this, "6kaVL2tz7iIPjSNFrkasfe5p-gzGzoHsz", "1ngCD68yavxOVWath9yz9XPT");
        AVOSCloud.setDebugLogEnabled(true);
    }
}
