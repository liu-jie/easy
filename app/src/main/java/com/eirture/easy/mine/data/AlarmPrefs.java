package com.eirture.easy.mine.data;

import org.androidannotations.annotations.sharedpreferences.SharedPref;

/**
 * Created by eirture on 17-1-19.
 */
@SharedPref(SharedPref.Scope.UNIQUE)
public interface AlarmPrefs {

    boolean isOpen();

    int alarmTime();
}
