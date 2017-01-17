package com.eirture.easy.mine.data;

import org.androidannotations.annotations.sharedpreferences.SharedPref;

/**
 * Created by eirture on 17-1-17.
 */
@SharedPref(SharedPref.Scope.UNIQUE)
public interface PasswordPrefs {

    String password();

    boolean openPassword();

    boolean openFingerprint();
}
