package com.eirture.easy.mine.view;

import android.app.Activity;
import android.widget.TextView;

import com.avos.avoscloud.AVUser;
import com.eirture.easy.R;
import com.eirture.easy.main.data.ConfigPrefs_;
import com.eirture.easy.main.view.MainFragment;
import com.eirture.easy.user.view.LoginA_;
import com.eirture.easy.user.view.UserDetailA_;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.OnActivityResult;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;

/**
 * Created by eirture on 17-1-10.
 */
@EFragment(R.layout.f_mine)
public class MineF extends MainFragment {
    private static final int REQUEST_LOGIN_CODE = 1;
    private static final int REQUEST_SYNC_CODE = 2;
    @ViewById(R.id.tv_username)
    TextView tvUsername;
    @ViewById(R.id.tv_sync)
    TextView tvSync;

    private AVUser currentUser;
    @Pref
    ConfigPrefs_ configPrefs;

    @AfterViews
    protected void initViews() {
        refreshUserName();
        refreshSyncState();
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
            refreshSyncState();
        }
    }

    private void refreshUserName() {
        if (tvUsername == null)
            return;

        currentUser = AVUser.getCurrentUser();
        tvUsername.setText(currentUser == null ? "" : currentUser.getUsername());
        if (currentUser == null) {
            configPrefs.edit().openSync().put(false).apply();
        }
    }

    @Click(R.id.item_sync)
    protected void clickSync() {
        SyncA_.intent(this)
                .startForResult(REQUEST_SYNC_CODE);
    }

    @OnActivityResult(REQUEST_SYNC_CODE)
    protected void syncResult(int resultCode) {
        if (resultCode == Activity.RESULT_OK)
            refreshSyncState();
    }

    private void refreshSyncState() {
        tvSync.setText(configPrefs.openSync().getOr(false) ? R.string.enabled : R.string.disabled);
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
