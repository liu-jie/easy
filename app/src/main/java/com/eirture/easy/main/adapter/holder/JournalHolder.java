package com.eirture.easy.main.adapter.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.eirture.easy.R;
import com.eirture.easy.base.utils.Views;
import com.eirture.easy.base.widget.ClickableViewHolder;

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

    @Override
    public void onClick(View v) {
        Toast.makeText(v.getContext(), "click journal item", Toast.LENGTH_SHORT).show();
    }
}
