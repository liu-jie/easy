package com.eirture.easy.main.adapter.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
    private int journalId, notebookId;

    public JournalHolder(View parent) {
        super(Views.inflate(parent, R.layout.i_journal));

        ivPictureOne = Views.find(itemView, R.id.iv_one);
        ivPictureTwo = Views.find(itemView, R.id.iv_two);

        tvPictureCount = Views.find(itemView, R.id.tv_photo_count);
        tvDescription = Views.find(itemView, R.id.tv_description);
        tvExtraDescription = Views.find(itemView, R.id.tv_extra_description);
        tvDate = Views.find(itemView, R.id.tv_date);
    }

    public void bindData(Journal journal) {
        if (journal == null)
            return;
        this.journalId = journal.id;
        this.notebookId = journal.getNoteId();

        tvDescription.setText(journal.getContentPreview());

        Date date = journal.getDate();
        CharSequence dateStr = SpannableBuilder.createBuilder(tvDate.getContext())
                .append(DateUtil.getWeekName(date))
                .append("\n")
                .createStyle().setSize(32).setColorResId(R.color.textBlack).apply()
                .append(String.valueOf(DateUtil.getDayOfMonth(date)))
                .build();
        tvDate.setText(dateStr);

    }

    @Override
    public void onClick(View v) {
//        Toast.makeText(v.getContext(), "click journal item", Toast.LENGTH_SHORT).show();
        EditA_.intent(v.getContext())
                .journalId(journalId)
                .notebookId(notebookId)
                .start();
    }
}
