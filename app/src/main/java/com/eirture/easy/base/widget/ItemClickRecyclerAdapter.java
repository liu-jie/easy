package com.eirture.easy.base.widget;

import android.support.v7.widget.RecyclerView;

/**
 * Created by eirture on 16-11-5.
 */

public abstract class ItemClickRecyclerAdapter<VH extends RecyclerView.ViewHolder, T> extends RecyclerView.Adapter<VH> {

    protected OnItemClickListener<T> listener;

    protected boolean onItemClick(T data) {
        if (listener == null)
            return false;
        listener.onItemClick(data);
        return true;
    }

    public void addOnItemClickListener(OnItemClickListener<T> listener) {
        this.listener = listener;
    }
}
