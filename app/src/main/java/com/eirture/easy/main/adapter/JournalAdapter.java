package com.eirture.easy.main.adapter;

import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.Toast;

import com.eirture.easy.base.widget.HeadSuperRecyclerAdapter;
import com.eirture.easy.base.widget.helper.SwipeAdapterCallback;
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

public class JournalAdapter extends HeadSuperRecyclerAdapter<JournalHolder, JournalHeadHolder> implements SwipeAdapterCallback {
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
        System.out.println("bind holder: " + position);
        holder.bindData(journals.get(position), position);
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
        JournalHolder holder = new JournalHolder(parent);
        holder.itemView.setOnLongClickListener(v -> {
            removePosition(holder.position());
            return true;
        });
        return holder;
    }

    @Override
    public JournalHeadHolder onCreateHeadHolder(ViewGroup parent) {
        return new JournalHeadHolder(parent);
    }

    @Override
    public long getItemId(int position) {
        Journal j = notebook.journals().get(position);
        return j == null ? super.getItemId(position) : j.id;
    }

    public void removePosition(int position) {
        if (position > 1) {
            notebook.journals().remove(position - 1);
        }
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, getItemCount());
    }

    @Override
    public void swiped(RecyclerView.ViewHolder holder, int direction) {
        new AlertDialog.Builder(holder.itemView.getContext())
                .setMessage("确认删除？")
                .setPositiveButton("删除", (dialog, which) -> removePosition(holder.getAdapterPosition()))
                .setNegativeButton("取消", (dialog, which) -> notifyItemChanged(holder.getAdapterPosition()))
                .show();
    }
}
