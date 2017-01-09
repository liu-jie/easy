package com.eirture.easy.main.adapter.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.eirture.easy.R;
import com.eirture.easy.base.utils.glide.GlideHelper;
import com.eirture.easy.main.model.Journal;
import com.eirture.rxcommon.dates.DateUtil;
import com.eirture.rxcommon.texts.SpannableBuilder;
import com.eirture.rxcommon.utils.Views;
import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;

import java.util.Date;
import java.util.List;

/**
 * Created by eirture on 16-12-4.
 */
public class JournalHolder extends RecyclerView.ViewHolder {
    public ImageView ivPictureOne, ivPictureTwo;
    public TextView tvPictureCount, tvDescription, tvExtraDescription, tvDate;
    private int journalId, notebookId, position;

    public JournalHolder(View parent) {
        super(Views.inflate(parent, R.layout.i_journal));

        ivPictureOne = Views.find(itemView, R.id.iv_one);
        ivPictureTwo = Views.find(itemView, R.id.iv_two);

        tvPictureCount = Views.find(itemView, R.id.tv_photo_count);
        tvDescription = Views.find(itemView, R.id.tv_description);
        tvExtraDescription = Views.find(itemView, R.id.tv_extra_description);
        tvDate = Views.find(itemView, R.id.tv_date);
    }

    public void bindData(Journal journal, int position) {
        this.position = position;
        if (journal == null)
            return;
        this.journalId = journal.id;
        this.notebookId = journal.getNoteId();

        tvDescription.setText(SpannableBuilder.createBuilder(tvDescription.getContext())
                .createStyle().setSize(16).apply()
                .append(journal.getTitle())
                .createStyle().setSize(12).apply()
                .append("\n")
                .append(journal.getContentPreview())
                .build());

        Date date = journal.getDate();
        CharSequence dateStr = SpannableBuilder.createBuilder(tvDate.getContext())
                .append(DateUtil.getWeekName(date))
                .append("\n")
                .createStyle().setSize(32).setColorResId(R.color.textBlack).apply()
                .append(String.valueOf(DateUtil.getDayOfMonth(date)))
                .build();
        tvDate.setText(dateStr);
        updatePicture(journal.getPictures());
    }

    private void updatePicture(String picturesStr) {
        tvPictureCount.setVisibility(View.GONE);
        ivPictureOne.setVisibility(View.GONE);
        ivPictureTwo.setVisibility(View.GONE);

        if (Strings.isNullOrEmpty(picturesStr)) {
            return;
        }

        List<String> pictures = Lists.newArrayList(Splitter.on(",")
                .split(picturesStr));
        int count = pictures.size();
        if (count > 0) {
            ivPictureOne.setVisibility(View.VISIBLE);
            GlideHelper.loadPicture(pictures.get(0), ivPictureOne);
        }

        if (count > 1) {
            ivPictureTwo.setVisibility(View.VISIBLE);
            GlideHelper.loadPicture(pictures.get(1), ivPictureTwo);
        }

        if (count > 2) {
            tvPictureCount.setVisibility(View.VISIBLE);
            tvPictureCount.setText("+" + (count - 2));
        }
    }

    public int position() {
        return position;
    }

    public int journalId() {
        return journalId;
    }

    public int notebookId() {
        return notebookId;
    }
}
