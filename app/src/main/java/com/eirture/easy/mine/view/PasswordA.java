package com.eirture.easy.mine.view;

import android.support.v7.widget.Toolbar;

import com.eirture.easy.R;
import com.eirture.easy.base.views.BaseActivity;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

/**
 * Created by eirture on 17-1-16.
 */
@EActivity(R.layout.a_password)
public class PasswordA extends BaseActivity {

    @ViewById(R.id.toolbar)
    Toolbar toolbar;

    @AfterViews
    protected void initViews() {
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> back(v));
    }

    @Click(R.id.item_change_password)
    protected void clickChangePassword() {
        ChangePasswordA_.intent(this)
                .start();
    }

}
