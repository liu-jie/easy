package com.eirture.easy.base.widget;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by eirture on 16-12-6.
 */

public abstract class ClickableViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public ClickableViewHolder(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);
    }
}
