package com.eirture.easy.main.adapter;

import android.view.ViewGroup;

import com.eirture.easy.base.widget.HeadSuperRecyclerAdapter;
import com.eirture.easy.main.adapter.holder.JournalHeadHolder;
import com.eirture.easy.main.adapter.holder.JournalHolder;

/**
 * Created by eirture on 16-12-4.
 */

public class JournalAdapter extends HeadSuperRecyclerAdapter<JournalHolder, JournalHeadHolder> {
    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public void onBindHolder(JournalHolder holder, int position) {

    }

    @Override
    public void onBindHeadHolder(JournalHeadHolder headHolder, int position) {

    }

    @Override
    public JournalHolder onCreateHolder(ViewGroup parent, int viewType) {
        return new JournalHolder(parent);
    }

    @Override
    public JournalHeadHolder onCreateHeadHolder(ViewGroup parent) {
        return new JournalHeadHolder(parent);
    }
}
