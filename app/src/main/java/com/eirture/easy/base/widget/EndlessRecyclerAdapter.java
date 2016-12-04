package com.eirture.easy.base.widget;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eirture.easy.R;
import com.eirture.easy.base.utils.Views;

/**
 * Created by Jie on 2016-08-10.
 */

public abstract class EndlessRecyclerAdapter<VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int DATA_ITEM_TYPE = 1;
    public static final int LOADING_ITEM_TYPE = 0;

    private boolean isLoading;


    @Override
    public final int getItemViewType(int position) {
        if (position == getCount()) {
            if (isLoading) {
                return LOADING_ITEM_TYPE;
            }
        }
        return getItemType(position);
    }

    public int getItemType(int position) {
        return DATA_ITEM_TYPE;
    }

    @Override
    public final RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == LOADING_ITEM_TYPE) {
            return new LoadingViewHolder(
                    LayoutInflater.from(parent.getContext())
                            .inflate(R.layout.i_loading, parent, false));
        }
        return onCreateHolder(parent, viewType);
    }

    @Override
    public final void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof LoadingViewHolder) {
            return;
        } else {
            onBindHolder((VH) holder, position);
        }
    }

    public void updateLoading(boolean isLoading) {
        this.isLoading = isLoading;
    }

    @Override
    public final int getItemCount() {
        return getCount() + (isLoading ? 1 : 0);
    }

    public abstract int getCount();

    public abstract void onBindHolder(VH holder, int position);

    public abstract VH onCreateHolder(ViewGroup parent, int viewType);

    public static class LoadingViewHolder extends RecyclerView.ViewHolder {
        public LoadingViewHolder(View parent) {
            super(Views.inflate(parent, R.layout.i_loading));
        }
    }
}
