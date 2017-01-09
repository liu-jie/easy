package com.eirture.easy.main.adapter.holder;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.widget.TextView;

import com.eirture.easy.R;
import com.eirture.easy.main.model.Notebook;
import com.eirture.rxcommon.utils.Views;

/**
 * Created by eirture on 16-12-4.
 */

public class JournalHeadHolder extends RecyclerView.ViewHolder {
    public View btnPhoto, btnAdd;
    public TextView labCount, labDays, labPhotos, labWeek, labToday;

    public JournalHeadHolder(View parent) {
        super(Views.inflate(parent, R.layout.h_journal));
        labCount = Views.find(itemView, R.id.tv_lab_count);
        labDays = Views.find(itemView, R.id.tv_lab_days);
        labPhotos = Views.find(itemView, R.id.tv_lab_picture_count);
        labWeek = Views.find(itemView, R.id.tv_lab_week_count);
        labToday = Views.find(itemView, R.id.tv_lab_today_count);

        btnPhoto = Views.find(itemView, R.id.btn_photo);
        btnAdd = Views.find(itemView, R.id.btn_add);
    }

    public void bindData(Notebook notebook) {
        if (notebook == null)
            return;

        labCount.setText(String.valueOf(notebook.getCount()));
        labDays.setText(String.valueOf(notebook.getDays()));
//        labPhotos.setText(String.valueOf(notebook.getPictures()));
        startCountAnimation(labPhotos, notebook.getPictures());

        labWeek.setText(String.valueOf(notebook.getWeeklyCount()));
        labToday.setText(String.valueOf(notebook.getTodayCount()));
    }

    private static void startCountAnimation(TextView textView, int count) {
        int countBefore = Integer.parseInt(textView.getText().toString());
        ValueAnimator anim = ValueAnimator.ofInt(countBefore, count);
        anim.setDuration(400);
        anim.setInterpolator(new AccelerateDecelerateInterpolator());
        anim.addUpdateListener(animation -> {
            textView.setText(String.valueOf(anim.getAnimatedValue()));
        });

        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                textView.setText("" + count);
            }
        });

        anim.start();
    }

}
