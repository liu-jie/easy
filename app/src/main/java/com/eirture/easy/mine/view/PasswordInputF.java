package com.eirture.easy.mine.view;

import android.view.View;
import android.widget.TextView;

import com.eirture.easy.R;
import com.eirture.easy.base.views.BaseFragment;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewsById;

import java.util.Arrays;
import java.util.List;

/**
 * Created by eirture on 17-1-17.
 */
@EFragment(R.layout.f_input_password)
public class PasswordInputF extends BaseFragment {

    private String[] passwords;
    private int currentIndex;

    @ViewsById({R.id.pw_one, R.id.pw_two, R.id.pw_three, R.id.pw_four})
    List<View> itemPasswords;

    @AfterViews
    void initViews() {
        if (passwords == null) {
            passwords = new String[itemPasswords.size()];
        }
    }

    @Click({R.id.item_one, R.id.item_two, R.id.item_three, R.id.item_four, R.id.item_five, R.id.item_six, R.id.item_seven, R.id.item_eight, R.id.item_nine, R.id.item_zero})
    protected void clickNumberItem(TextView itemView) {
        if (currentIndex == passwords.length)
            return;

        itemPasswords.get(currentIndex).setSelected(true);
        passwords[currentIndex] = itemView.getText().toString();
        if (currentIndex == passwords.length - 1) {
            System.out.println(Arrays.asList(passwords));
        }
        currentIndex++;
    }

    @Click(R.id.item_back)
    protected void clickBack() {
        if (currentIndex <= 0)
            return;
        currentIndex--;
        itemPasswords.get(currentIndex).setSelected(false);
    }

}
