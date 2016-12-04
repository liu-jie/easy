package com.eirture.easy.base.widget;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

/**
 * Created by Jie on 2016-08-10.
 */

public abstract class HeadSuperRecyclerAdapter<VH extends RecyclerView.ViewHolder, HVH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int DATA_ITEM_TYPE = 1;
    public static final int TYPE_HEAD = 2;
    public static final int TYPE_LOADING = 3;

    private boolean isLoading = false;

    @Override
    public final int getItemViewType(int position) {

        if (isLoading && position == getCount()) {
            return TYPE_LOADING;
        } else if (position == 0) {
            return TYPE_HEAD;
        }
        int dataType = getItemType(position);
        if (dataType == TYPE_HEAD || dataType == TYPE_LOADING) {
            throw new RuntimeException("HeadSuperRecyclerAdapter item type can`t is 2 or 3");
        }
        return dataType;
    }

    public void updateLoading(boolean isLoading) {
        this.isLoading = isLoading;
    }

    public int getItemType(int position) {
        return DATA_ITEM_TYPE;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        RecyclerView.ViewHolder holder;
        switch (viewType) {
            case TYPE_HEAD:
                holder = onCreateHeadHolder(parent);
                break;
            case TYPE_LOADING:
                holder = new EndlessRecyclerAdapter.LoadingViewHolder(parent);
                break;
            default:
                holder = onCreateHolder(parent, viewType);
        }

        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof EndlessRecyclerAdapter.LoadingViewHolder) {
            return;
        }

        if (getItemViewType(position) == TYPE_HEAD) {
            onBindHeadHolder((HVH) holder, position);
        } else {
            onBindHolder((VH) holder, position - 1);
        }
    }


    @Override
    public final int getItemCount() {
        return getCount() + (isLoading ? 1 : 2);
    }

    public abstract int getCount();

    public abstract void onBindHolder(VH holder, int position);

    public abstract void onBindHeadHolder(HVH headHolder, int position);

    public abstract VH onCreateHolder(ViewGroup parent, int viewType);

    public abstract HVH onCreateHeadHolder(ViewGroup parent);
}
