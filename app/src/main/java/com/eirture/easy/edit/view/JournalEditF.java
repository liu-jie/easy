package com.eirture.easy.edit.view;

import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;

import com.eirture.easy.R;
import com.eirture.easy.base.views.BusFragment;
import com.eirture.easy.base.widget.HorizontalScrollViewPro;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

/**
 * Created by eirture on 16-12-6.
 */
@EFragment(R.layout.f_journal_edit)
public class JournalEditF extends BusFragment {


    @ViewById(R.id.et_content)
    JournalEditText etContent;

    @ViewById(R.id.btn_scroll)
    ImageView btnScrollEnd;

    @ViewById(R.id.hsv_edit_options)
    HorizontalScrollViewPro hsvEditOptionBar;

    @AfterViews
    void initViews() {
        hsvEditOptionBar.addOnScrollChangedListener((l, t, oldl, oldt) -> {
            View child = hsvEditOptionBar.getChildAt(hsvEditOptionBar.getChildCount() - 1);
            int diff = child.getRight() - (hsvEditOptionBar.getWidth() + l);

            if ((l == 0 && btnScrollEnd.getRotation() == 0) ||
                    (diff < 10 && btnScrollEnd.getRotation() == 180)) {
                rotationEditArrowButton(false);
            }
        });

        btnScrollEnd.setOnClickListener(v -> rotationEditArrowButton(true));
    }

    private void rotationEditArrowButton(boolean scrollbar) {
        float rAngle = 0 == btnScrollEnd.getRotation() ? 180 : 0;
        btnScrollEnd.animate().setInterpolator(new DecelerateInterpolator(1.5f))
                .rotationBy(180 - rAngle)
                .rotation(rAngle)
                .start();
        if (scrollbar) {
            hsvEditOptionBar.smoothScrollTo(rAngle == 0 ? hsvEditOptionBar.getRight() : hsvEditOptionBar.getLeft(), 0);
        }
    }

    public String getContent() {
        if (etContent == null)
            return "";

        return etContent.getText().toString();
    }

}
