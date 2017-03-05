package com.eirture.easy.user.view;

import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.EditText;

import com.avos.avoscloud.AVUser;
import com.eirture.easy.R;
import com.eirture.easy.base.views.BaseActivity;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

/**
 * Created by eirture on 17-3-5.
 */
@EActivity(R.layout.a_user_detail)
public class UserDetailA extends BaseActivity {

    @ViewById(R.id.toolbar)
    Toolbar toolbar;

    @ViewById(R.id.et_username)
    EditText etUsername;
    @ViewById(R.id.et_summary)
    EditText etSummary;

    private AVUser currentUser;

    @AfterViews
    protected void initViews() {
        toolbar.setNavigationOnClickListener(v -> back(v));
        toolbar.getMenu().add(R.string.save).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        toolbar.setOnMenuItemClickListener(item -> {
            return true;
        });

        currentUser = AVUser.getCurrentUser();
        refreshUserData();
    }

    private void refreshUserData() {
        if (currentUser == null)
            return;

        etUsername.setText(currentUser.getUsername());
        etSummary.setText(currentUser.getEmail());
    }

    @Click(R.id.btn_exit)
    protected void clickExit() {
        AVUser.logOut();
        setResult(RESULT_OK);
        finish();
    }
}
