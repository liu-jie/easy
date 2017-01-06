package com.eirture.easy.main.adapter.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.eirture.easy.R;
import com.eirture.easy.base.widget.ClickableViewHolder;
import com.eirture.easy.edit.view.EditA_;
import com.eirture.easy.main.model.Journal;
import com.eirture.rxcommon.dates.DateUtil;
import com.eirture.rxcommon.texts.SpannableBuilder;
import com.eirture.rxcommon.utils.Views;

import java.util.Date;

/**
 * Created by eirture on 16-12-4.
 */
public class JournalHolder extends ClickableViewHolder {
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
    }

    public int position() {
        return position;
    }


    @Override
    public void onClick(View v) {
        EditA_.intent(v.getContext())
                .journalId(journalId)
                .notebookId(notebookId)
                .start();
    }


}
