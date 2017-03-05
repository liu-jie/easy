package com.eirture.easy.mine.view;

import com.eirture.easy.R;
import com.eirture.easy.main.view.MainFragment;
import com.eirture.easy.user.view.LoginA_;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;

/**
 * Created by eirture on 17-1-10.
 */
@EFragment(R.layout.f_mine)
public class MineF extends MainFragment {
    private static final int REQUEST_LOGIN_CODE = 1;

    @Override
    protected void refreshNotebook() {

    }


    @Click(R.id.item_account)
    protected void clickAccount() {
        LoginA_.intent(this)
                .startForResult(REQUEST_LOGIN_CODE);
    }

    @Click(R.id.item_sync)
    protected void clickSync() {

    }

    @Click(R.id.item_notebook)
    protected void clickNotebook() {

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
