package com.eirture.easy.base.widget.helper;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

/**
 * Created by eirture on 17-1-6.
 */

public class SimpleItemTouchCallback extends ItemTouchHelper.Callback {

    private SwipeAdapterCallback callback;

    public SimpleItemTouchCallback(SwipeAdapterCallback callback) {
        this.callback = callback;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        return makeMovementFlags(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        if (callback == null)
            return;
        callback.swiped(viewHolder, direction);
    }
}
