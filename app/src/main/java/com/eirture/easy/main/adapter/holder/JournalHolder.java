package com.eirture.easy.main.adapter.holder;

import android.text.Editable;
import android.text.Spanned;
import android.text.style.RelativeSizeSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.eirture.easy.R;
import com.eirture.easy.base.utils.DateUtil;
import com.eirture.easy.base.utils.Views;
import com.eirture.easy.base.widget.ClickableViewHolder;
import com.eirture.easy.main.model.Journal;

/**
 * Created by eirture on 16-12-4.
 */

public class JournalHolder extends ClickableViewHolder {
    public ImageView ivPictureOne, ivPictureTwo;
    public TextView tvPictureCount, tvDescription, tvExtraDescription, tvDate;

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
        tvDescription.setText(journal.getContentPreview());
        setDateSpan(tvDate.getEditableText(), DateUtil.getJournalItemDate(journal.getDate()));

    }

    private void setDateSpan(Editable editable, String content) {
        RelativeSizeSpan spans[] = editable.getSpans(0, editable.length(), RelativeSizeSpan.class);
        for (RelativeSizeSpan span : spans) {
            editable.removeSpan(span);
        }
        int newLinePosition = content.indexOf("\n");
        if (newLinePosition == 0)
            return;
        editable.setSpan(new RelativeSizeSpan(1.8f), 0, (newLinePosition > 0) ? newLinePosition : editable.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);

    }

    @Override
    public void onClick(View v) {
        Toast.makeText(v.getContext(), "click journal item", Toast.LENGTH_SHORT).show();
    }
}
