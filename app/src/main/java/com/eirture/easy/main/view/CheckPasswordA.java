package com.eirture.easy.main.view;

import android.support.v7.app.AppCompatActivity;

import com.eirture.easy.R;
import com.eirture.easy.mine.data.PasswordPrefs_;
import com.eirture.easy.mine.interfaces.PasswordInputOption;
import com.eirture.easy.mine.view.PasswordInputF;
import com.eirture.easy.mine.view.PasswordInputF_;
import com.google.common.base.Strings;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.sharedpreferences.Pref;

/**
 * Created by eirture on 17-1-17.
 */
@EActivity(R.layout.a_check_password)
public class CheckPasswordA extends AppCompatActivity {

    private PasswordInputOption passwordInputOption;
    @Pref
    PasswordPrefs_ passwordPrefs;
    private String currentPasswordStr;
    private boolean pass;

    @AfterViews
    protected void initViews() {
        currentPasswordStr = passwordPrefs.password().get();
        if (Strings.isNullOrEmpty(currentPasswordStr))
            finish();

        PasswordInputF f = PasswordInputF_.builder().build();
        passwordInputOption = f;
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fl_container, f)
                .commit();

        passwordInputOption.addInputFinishListener(password -> {
            if (currentPasswordStr.equals(password)) {
                pass = true;
                setResult(RESULT_OK);
                finish();
            } else {
                passwordInputOption.clean(false);
            }
        });
    }

    @Override
    public void finish() {
        if (pass)
            super.finish();
    }
}
