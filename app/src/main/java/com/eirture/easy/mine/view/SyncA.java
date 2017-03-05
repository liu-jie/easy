package com.eirture.easy.mine.view;

import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;

import com.eirture.easy.R;
import com.eirture.easy.base.views.BaseActivity;
import com.eirture.easy.main.data.ConfigPrefs_;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;

/**
 * Created by eirture on 17-3-5.
 */
@EActivity(R.layout.a_sync)
public class SyncA extends BaseActivity {

    @ViewById(R.id.toolbar)
    Toolbar toolbar;

    @ViewById(R.id.switch_sync)
    SwitchCompat scSync;

    @Pref
    ConfigPrefs_ configPrefs;

    @AfterViews
    protected void initViews() {
        setSupportActionBar(toolbar);
        scSync.setChecked(configPrefs.openSync().getOr(false));
        scSync.setOnCheckedChangeListener((buttonView, isChecked) -> {
            configPrefs.edit().openSync().put(isChecked).apply();
            setResult(RESULT_OK);
        });
    }
}
