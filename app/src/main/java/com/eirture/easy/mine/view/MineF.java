package com.eirture.easy.mine.view;

import com.eirture.easy.R;
import com.eirture.easy.main.view.MainFragment;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;

/**
 * Created by eirture on 17-1-10.
 */
@EFragment(R.layout.f_mine)
public class MineF extends MainFragment {

    @Override
    protected void refreshNotebook() {

    }


    @Click(R.id.item_account)
    protected void clickAccount() {

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
