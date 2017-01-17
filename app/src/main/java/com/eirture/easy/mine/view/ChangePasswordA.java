package com.eirture.easy.mine.view;

import android.widget.TextView;

import com.eirture.easy.R;
import com.eirture.easy.base.views.BaseActivity;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

/**
 * Created by eirture on 17-1-17.
 */
@EActivity(R.layout.a_change_password)
public class ChangePasswordA extends BaseActivity {

    @ViewById(R.id.tv_hint)
    TextView tvHint;


    @AfterViews
    protected void initViews() {
        getSupportFragmentManager().beginTransaction()
                .add(R.id.input_container, PasswordInputF_.builder().build())
                .commit();
    }
}
