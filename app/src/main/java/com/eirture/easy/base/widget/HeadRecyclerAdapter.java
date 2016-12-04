package com.eirture.easy.base.widget;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

/**
 * Created by Jie on 2016-08-10.
 */

public abstract class HeadRecyclerAdapter<VH extends RecyclerView.ViewHolder, HVH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int DATA_ITEM_TYPE = 1;
    public static final int TYPE_HEAD = 2;

    @Override
    public final int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_HEAD;
        }
        return getItemType(position);
    }

    public int getItemType(int position) {
        return DATA_ITEM_TYPE;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_HEAD) {
            return onCreateHeadHolder(parent);
        }
        return onCreateHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == TYPE_HEAD) {
            onBindHeadHolder((HVH) holder, position);
        } else {
            onBindHolder((VH) holder, position - 1);
        }
    }


    @Override
    public final int getItemCount() {
        return getCount() + 1;
    }

    public abstract int getCount();

    public abstract void onBindHolder(VH holder, int position);

    public abstract void onBindHeadHolder(HVH headHolder, int position);

    public abstract VH onCreateHolder(ViewGroup parent, int viewType);

    public abstract HVH onCreateHeadHolder(ViewGroup parent);
}
