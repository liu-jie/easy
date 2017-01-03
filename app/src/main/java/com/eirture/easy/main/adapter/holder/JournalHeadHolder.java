package com.eirture.easy.main.adapter.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
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
        labPhotos.setText(String.valueOf(notebook.getPictures()));
        labWeek.setText(String.valueOf(notebook.getWeeklyCount()));
        labToday.setText(String.valueOf(notebook.getTodays()));

    }

}
