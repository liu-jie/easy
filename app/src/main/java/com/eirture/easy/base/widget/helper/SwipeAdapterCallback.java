package com.eirture.easy.base.widget.helper;

import android.support.v7.widget.RecyclerView;

/**
 * Created by eirture on 17-1-6.
 */

public interface SwipeAdapterCallback {

    void swiped(RecyclerView.ViewHolder holder, int direction);

}
