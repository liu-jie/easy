package com.eirture.easy.main.data;

import org.androidannotations.annotations.sharedpreferences.SharedPref;

/**
 * Created by eirture on 17-3-5.
 */
@SharedPref(SharedPref.Scope.UNIQUE)
public interface ConfigPrefs {

    boolean openSync();
}
