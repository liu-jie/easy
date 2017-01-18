package com.eirture.easy.base.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

/**
 * Created by eirture on 16-12-4.
 */

public class BaseActivity extends AppCompatActivity {
    private static final String TAG = "BaseActivity";
    private static boolean needPassword;

    public void back(View view) {
        this.finish();
    }


    @Override
    protected void onStart() {
        super.onStart();
//        if (needPassword) {
//            CheckPasswordA_.intent(this)
//                    .startForResult(REQUEST_PASSWORD);
//        }

        Log.d(TAG, "onStart: start password check: " + needPassword);
        Log.d(TAG, "onStart: " + getClass().getSimpleName());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        needPassword = false;
        Log.d(TAG, "onActivityResult: " + getClass().getSimpleName());
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onStop() {
        super.onStop();
        needPassword = true;
        Log.d(TAG, "onStop: " + getClass().getSimpleName());
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        needPassword = false;

        Log.d(TAG, "onCreate: context package: " + getIntent().getComponent().getPackageName());

        Log.d(TAG, "onCreate: " + getClass().getSimpleName());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        needPassword = false;
        Log.d(TAG, "onDestroy: " + getClass().getSimpleName());
    }

    @Override
    public void finish() {
        super.finish();
        needPassword = false;
        Log.d(TAG, "finish: " + getClass().getSimpleName());
    }
}
