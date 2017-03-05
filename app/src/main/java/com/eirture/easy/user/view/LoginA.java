package com.eirture.easy.user.view;

import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.EditText;

import com.eirture.easy.R;
import com.eirture.easy.base.views.BusActivity;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

/**
 * Created by eirture on 17-3-5.
 */
@EActivity(R.layout.a_login)
public class LoginA extends BusActivity {

    @ViewById(R.id.et_account)
    EditText etAccount;
    @ViewById(R.id.et_password)
    EditText etPassword;

    @ViewById(R.id.toolbar)
    Toolbar toolbar;

    @AfterViews
    protected void initViews() {
        initMenu();

    }

    private void initMenu() {
        toolbar.getMenu().add(R.string.register).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        toolbar.setOnMenuItemClickListener(item -> {
            //
            return true;
        });
    }

}

