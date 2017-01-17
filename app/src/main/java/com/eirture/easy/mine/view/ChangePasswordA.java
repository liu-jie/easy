package com.eirture.easy.mine.view;

import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.eirture.easy.R;
import com.eirture.easy.base.views.BaseActivity;
import com.eirture.easy.mine.data.PasswordPrefs_;
import com.eirture.easy.mine.interfaces.PasswordInputOption;
import com.google.common.base.Strings;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;

/**
 * Created by eirture on 17-1-17.
 */
@EActivity(R.layout.a_change_password)
public class ChangePasswordA extends BaseActivity {
    @Extra
    boolean onlyCheckPassword;

    @ViewById(R.id.tv_hint)
    TextView tvHint;
    @ViewById(R.id.toolbar)
    Toolbar toolbar;

    @Pref
    PasswordPrefs_ passwordPrefs;
    private PasswordInputOption passwordInputOption;

    private String passwordOnce, currentPwd;

    @AfterViews
    protected void initViews() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationOnClickListener(v -> back(v));

        currentPwd = passwordPrefs.password().get();
        initPasswordFragment();
    }

    private void initPasswordFragment() {
        PasswordInputF passwordInputF = PasswordInputF_.builder().build();
        this.passwordInputOption = passwordInputF;
        getSupportFragmentManager().beginTransaction()
                .add(R.id.input_container, passwordInputF)
                .commit();

        tvHint.setText(Strings.isNullOrEmpty(currentPwd) ? R.string.input_new_password : R.string.input_current_password);

        passwordInputOption.addInputFinishListener(password -> {
            if (!Strings.isNullOrEmpty(currentPwd)) {
                boolean pass = currentPwd.equals(password);
                passwordInputOption.clean(pass);
                if (pass) {
                    if (onlyCheckPassword) {
                        setResult(RESULT_OK);
                        finish();
                        return;
                    }
                    currentPwd = null;
                    tvHint.setText(R.string.input_new_password);
                }


                return;
            }

            if (Strings.isNullOrEmpty(passwordOnce)) {
                passwordOnce = password;
                passwordInputOption.clean(true);
                tvHint.setText(R.string.input_new_password_again);
                return;
            }

            if (passwordOnce.equals(password)) {
                passwordPrefs.edit().password().put(password).apply();
                setResult(RESULT_OK);
                this.finish();
            } else {
                passwordInputOption.clean(false);
                tvHint.setText(R.string.password_unconsistent);
                passwordOnce = null;
            }
        });
    }
}
