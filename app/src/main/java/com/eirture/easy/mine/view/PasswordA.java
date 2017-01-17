package com.eirture.easy.mine.view;

import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.eirture.easy.R;
import com.eirture.easy.base.views.BaseActivity;
import com.eirture.easy.mine.data.PasswordPrefs_;
import com.jakewharton.rxbinding.view.RxView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OnActivityResult;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;

/**
 * Created by eirture on 17-1-16.
 */
@EActivity(R.layout.a_password)
public class PasswordA extends BaseActivity {

    private static final int REQUEST_CHANGE_PASSWORD = 1;

    @ViewById(R.id.toolbar)
    Toolbar toolbar;
    @ViewById(R.id.switch_password)
    SwitchCompat switchPassword;
    @ViewById(R.id.switch_fingerprint)
    SwitchCompat switchFingerprint;
    @ViewById(R.id.item_change_password)
    View itemChangePassword;
    @Pref
    PasswordPrefs_ passwordPrefs;

    @AfterViews
    protected void initViews() {
        RxView.clicks(itemChangePassword)
                .subscribe(aVoid -> ChangePasswordA_.intent(this).start());

        boolean passwordIsActive = passwordPrefs.openPassword().get();
        refreshPasswordActive(passwordIsActive);

        switchPassword.setChecked(passwordIsActive);
        switchFingerprint.setChecked(passwordPrefs.openFingerprint().get());

        switchFingerprint.setOnCheckedChangeListener(
                (buttonView, isChecked) -> passwordPrefs.edit().openFingerprint().put(isChecked).apply());
        switchPassword.setOnClickListener(view -> switchPassword(switchPassword.isChecked()));

        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> back(v));
    }

    boolean openPassword;

    private void switchPassword(boolean isChecked) {
        openPassword = isChecked;
        ChangePasswordA_.intent(this)
                .onlyCheckPassword(!openPassword)   // only check when close password.
                .startForResult(REQUEST_CHANGE_PASSWORD);
    }

    @OnActivityResult(REQUEST_CHANGE_PASSWORD)
    protected void resultChangePassword(int resultCode) {
        if (resultCode != RESULT_OK) {
            switchPassword.setChecked(!openPassword);
            return;
        }

        refreshPasswordActive(openPassword);
        if (openPassword) {
            passwordPrefs.edit().openPassword().put(true).apply();
        } else {
            passwordPrefs.edit().openPassword().put(false)
                    .openFingerprint().put(false)
                    .password().put("")
                    .apply();
        }
    }

    private void refreshPasswordActive(boolean openPassword) {
        itemChangePassword.setEnabled(openPassword);
        switchFingerprint.setEnabled(openPassword);
    }


}
