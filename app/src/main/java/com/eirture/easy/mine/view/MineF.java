package com.eirture.easy.mine.view;

import android.app.Activity;
import android.widget.TextView;

import com.avos.avoscloud.AVUser;
import com.eirture.easy.R;
import com.eirture.easy.main.view.MainFragment;
import com.eirture.easy.user.view.LoginA_;
import com.eirture.easy.user.view.UserDetailA_;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.OnActivityResult;
import org.androidannotations.annotations.ViewById;

/**
 * Created by eirture on 17-1-10.
 */
@EFragment(R.layout.f_mine)
public class MineF extends MainFragment {
    private static final int REQUEST_LOGIN_CODE = 1;

    @ViewById(R.id.tv_username)
    TextView tvUsername;

    private AVUser currentUser;

    @AfterViews
    protected void initViews() {
        refreshUserName();
    }

    @Override
    protected void refreshNotebook() {

    }


    @Click(R.id.item_account)
    protected void clickAccount() {
        if (currentUser == null) {
            LoginA_.intent(this)
                    .startForResult(REQUEST_LOGIN_CODE);
        } else {
            UserDetailA_.intent(this)
                    .startForResult(REQUEST_LOGIN_CODE);
        }
    }

    @OnActivityResult(REQUEST_LOGIN_CODE)
    protected void loginResult(int resultCode) {
        if (resultCode == Activity.RESULT_OK) {
            refreshUserName();
        }
    }

    private void refreshUserName() {
        if (tvUsername == null)
            return;

        currentUser = AVUser.getCurrentUser();
        tvUsername.setText(currentUser == null ? "" : currentUser.getUsername());
    }

    @Click(R.id.item_sync)
    protected void clickSync() {

    }

    @Click(R.id.item_lock)
    protected void clickLock() {
        PasswordA_.intent(this).start();
    }

    @Click(R.id.item_alert)
    protected void clickAlert() {
        NotificationA_.intent(this).start();
    }

    @Click(R.id.item_about)
    protected void clickAbout() {
        AboutA_.intent(this).start();
    }
}
