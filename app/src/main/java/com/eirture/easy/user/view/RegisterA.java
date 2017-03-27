package com.eirture.easy.user.view;

import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import com.eirture.easy.R;
import com.eirture.easy.base.utils.MyTextWatch;
import com.eirture.easy.base.utils.Strings;
import com.eirture.easy.base.views.BusActivity;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

/**
 * Created by eirture on 17-3-5.
 */
@EActivity(R.layout.a_register)
public class RegisterA extends BusActivity {

    @ViewById(R.id.toolbar)
    Toolbar toolbar;

    @ViewById(R.id.et_account)
    EditText etAccount;
    @ViewById(R.id.et_email)
    EditText etEmail;
    @ViewById(R.id.et_password)
    EditText etPassword;
    @ViewById(R.id.et_password_confirm)
    EditText etPasswordConfirm;
    @ViewById(R.id.btn_register)
    View btnRegister;

    @AfterViews
    protected void initViews() {
        setSupportActionBar(toolbar);
        refreshBtnStatus();

        MyTextWatch.createSimpleTextWatch(etAccount, 16, 6, active -> changeEditStatus(etAccount, active));
        MyTextWatch.createSimpleTextWatch(etEmail, 24, 6, active -> changeEditStatus(etEmail, active));
        MyTextWatch.createSimpleTextWatch(etPassword, 0, 6, active -> changeEditStatus(etPassword, active));
        MyTextWatch.createSimpleTextWatch(etPasswordConfirm, 0, 6, active -> changeEditStatus(etPasswordConfirm, active));
    }

    private void changeEditStatus(EditText editText, boolean active) {
        editText.setSelected(active);
        refreshBtnStatus();
    }

    private void refreshBtnStatus() {
        btnRegister.setEnabled(etEmail.isSelected() && etAccount.isSelected());
    }

    @Click(R.id.btn_register)
    protected void clickRegister() {
        String username = etAccount.getText().toString();
        String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();

        if (!Strings.checkString(4, username)) {

        }

        if (Strings.checkString(6, password) && password.equals(etPasswordConfirm.getText().toString())) {

        }
    }

}
