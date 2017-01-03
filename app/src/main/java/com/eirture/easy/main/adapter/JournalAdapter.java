package com.eirture.easy.main.adapter;

import android.view.ViewGroup;
import android.widget.Toast;

import com.eirture.easy.base.widget.HeadSuperRecyclerAdapter;
import com.eirture.easy.edit.view.EditA_;
import com.eirture.easy.main.adapter.holder.JournalHeadHolder;
import com.eirture.easy.main.adapter.holder.JournalHolder;
import com.eirture.easy.main.model.Journal;
import com.eirture.easy.main.model.Notebook;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by eirture on 16-12-4.
 */

public class JournalAdapter extends HeadSuperRecyclerAdapter<JournalHolder, JournalHeadHolder> {
    private Notebook notebook;
    private List<Journal> journals = new ArrayList<>();


    public void updateNotebook(Notebook notebook) {
        this.notebook = notebook;
        journals = notebook.journals();
    }

    @Override
    public int getCount() {
        return journals.size();
    }

    @Override
    public void onBindHolder(JournalHolder holder, int position) {
        if (position < journals.size()) {
            holder.bindData(journals.get(position));
        } else {
            System.out.println("out of journals size!! ---------" + position);
        }
    }

    @Override
    public void onBindHeadHolder(JournalHeadHolder headHolder, int position) {
        headHolder.btnAdd.setOnClickListener(v ->
                EditA_.intent(headHolder.btnAdd.getContext()).start());
        headHolder.btnPhoto.setOnClickListener(v ->
                Toast.makeText(headHolder.btnPhoto.getContext(), "new photo journal", Toast.LENGTH_SHORT).show());

        headHolder.bindData(notebook);
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
