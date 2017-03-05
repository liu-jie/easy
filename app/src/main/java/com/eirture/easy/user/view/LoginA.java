package com.eirture.easy.user.view;

import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.LogInCallback;
import com.eirture.easy.R;
import com.eirture.easy.base.utils.MyTextWatch;
import com.eirture.easy.base.views.BusActivity;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OnActivityResult;
import org.androidannotations.annotations.ViewById;

/**
 * Created by eirture on 17-3-5.
 */
@EActivity(R.layout.a_login)
public class LoginA extends BusActivity {
    private static final int REQUEST_REGISTER_CODE = 1;
    @ViewById(R.id.et_account)
    EditText etAccount;
    @ViewById(R.id.et_password)
    EditText etPassword;

    @ViewById(R.id.toolbar)
    Toolbar toolbar;

    @ViewById(R.id.btn_login)
    TextView btnLogin;

    @AfterViews
    protected void initViews() {
        initListeners();
        initMenu();
    }

    private void initListeners() {
        MyTextWatch.createTextWatch(etPassword, 0, 6, new MyTextWatch.TextChangCallBack() {
            @Override
            public void call(int totalCount) {

            }

            @Override
            public void changeStatus(boolean active) {
                etPassword.setSelected(active);
                refreshBtnState();
            }
        });
        MyTextWatch.createTextWatch(etAccount, 0, 6, new MyTextWatch.TextChangCallBack() {
            @Override
            public void call(int totalCount) {

            }

            @Override
            public void changeStatus(boolean active) {
                etAccount.setSelected(active);
                refreshBtnState();
            }
        });

        btnLogin.setOnClickListener(v -> {
            AVUser.logInInBackground(etAccount.getText().toString(), etPassword.getText().toString(), new LogInCallback<AVUser>() {
                @Override
                public void done(AVUser avUser, AVException e) {
                    if (e == null) {
                        setResult(RESULT_OK);
                        finish();
                        return;
                    }

                    Toast.makeText(LoginA.this, "账号名或密码错误", Toast.LENGTH_SHORT).show();
                }
            });
        });
    }

    private void refreshBtnState() {
        btnLogin.setActivated(etPassword.isSelected() && etAccount.isSelected());
    }

    private void initMenu() {
        toolbar.setNavigationOnClickListener(v -> back(v));
        toolbar.getMenu().add(R.string.register).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        toolbar.setOnMenuItemClickListener(item -> {
            //
            RegisterA_.intent(LoginA.this)
                    .startForResult(REQUEST_REGISTER_CODE);
            return true;
        });
    }

    @OnActivityResult(REQUEST_REGISTER_CODE)
    protected void registerResult(int resultCode) {
        if (resultCode == RESULT_OK) {
            setResult(RESULT_OK);
            this.finish();
        }
    }

}

