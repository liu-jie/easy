package com.eirture.easy.base.views;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.eirture.easy.main.view.CheckPasswordA_;
import com.eirture.easy.mine.data.PasswordPrefs_;

/**
 * Created by eirture on 16-12-4.
 */

public class BaseActivity extends AppCompatActivity {
    private static final String TAG = "BaseActivity";
    private static final int REQUEST_LOCK_ACTIVITY = 99;
    private static boolean needPassword;
    private PasswordPrefs_ passwordPrefs;

    private Handler handler = new Handler();
    private Runnable startLockTask = () ->
            CheckPasswordA_.intent(this)
                    .startForResult(REQUEST_LOCK_ACTIVITY);

    public void back(View view) {
        this.finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        handler.removeCallbacks(startLockTask);
        if (needPassword && passwordPrefs.openPassword().get()) {
            handler.postDelayed(startLockTask, 300);
        }
//        Log.d(TAG, "onStart: " + getClass().getSimpleName());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        handler.removeCallbacks(startLockTask);
        needPassword = false;
//        Log.d(TAG, "onActivityResult: " + getClass().getSimpleName());
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onStop() {
        super.onStop();
        needPassword = true;
//        Log.d(TAG, "onStop: " + getClass().getSimpleName());
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        passwordPrefs = new PasswordPrefs_(this);
        needPassword = false;
//        Log.d(TAG, "onCreate: " + getClass().getSimpleName());
    }

    @Override
    public void finish() {
        needPassword = false;
        super.finish();
//        Log.d(TAG, "finish: " + getClass().getSimpleName());
    }
}
