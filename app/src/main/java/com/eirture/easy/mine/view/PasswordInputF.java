package com.eirture.easy.mine.view;

import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.eirture.easy.R;
import com.eirture.easy.base.views.BaseFragment;
import com.eirture.easy.mine.interfaces.PasswordInputFinishListener;
import com.eirture.easy.mine.interfaces.PasswordInputOption;
import com.google.common.base.Joiner;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.ViewsById;

import java.util.Arrays;
import java.util.List;

/**
 * Created by eirture on 17-1-17.
 */
@EFragment(R.layout.f_input_password)
public class PasswordInputF extends BaseFragment implements PasswordInputOption {

    private String[] passwords;
    private int currentIndex;
    private PasswordInputFinishListener listener;

    @ViewsById({R.id.pw_one, R.id.pw_two, R.id.pw_three, R.id.pw_four})
    List<View> itemPasswords;
    @ViewById(R.id.ll_password)
    View password_linerlayout;

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
            if (listener != null) {
                listener.callback(Joiner.on("").join(passwords));
                return;
            }
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


    @Override
    public void addInputFinishListener(PasswordInputFinishListener listener) {
        this.listener = listener;
    }

    @Override
    public void clean(boolean pass) {

        for (int i = 0; i < itemPasswords.size(); i++) {
            itemPasswords.get(i).setSelected(false);
        }
        currentIndex = 0;

        if (!pass) {
            password_linerlayout.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.shake));
        }
    }
}
