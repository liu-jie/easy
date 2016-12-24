package com.eirture.easy.main.adapter;

import android.view.ViewGroup;
import android.widget.Toast;

import com.eirture.easy.base.widget.HeadSuperRecyclerAdapter;
import com.eirture.easy.edit.view.EditA_;
import com.eirture.easy.main.adapter.holder.JournalHeadHolder;
import com.eirture.easy.main.adapter.holder.JournalHolder;
import com.eirture.easy.main.model.Journal;
import com.eirture.easy.main.model.NotebookDB;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by eirture on 16-12-4.
 */

public class JournalAdapter extends HeadSuperRecyclerAdapter<JournalHolder, JournalHeadHolder> {
    private NotebookDB notebook;
    private List<Journal> journals = new ArrayList<>();

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public void onBindHolder(JournalHolder holder, int position) {

    }

    @Override
    public void onBindHeadHolder(JournalHeadHolder headHolder, int position) {
        headHolder.btnAdd.setOnClickListener(v ->
                EditA_.intent(headHolder.btnAdd.getContext()).start());

        headHolder.btnPhoto.setOnClickListener(v ->
                Toast.makeText(headHolder.btnPhoto.getContext(), "new photo journal", Toast.LENGTH_SHORT).show());
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
