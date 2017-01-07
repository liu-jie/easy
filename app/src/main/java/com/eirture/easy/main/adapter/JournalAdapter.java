package com.eirture.easy.main.adapter;

import android.view.ViewGroup;
import android.widget.Toast;

import com.eirture.easy.base.widget.HeadSuperRecyclerAdapter;
import com.eirture.easy.base.widget.OnItemLongClickListener;
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

    private OnItemLongClickListener<Integer> onItemLongClickListener;

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
            if (onItemLongClickListener == null)
                return false;
//            removePosition(holder.position());
//            return true;
            return onItemLongClickListener.onLongClick(holder.position());
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
        notebook.journals().remove(position);
        notifyItemRemoved(position + 1);
        notifyItemRangeChanged(position + 1, getItemCount());

    }

    public void addOnItemLongClickListener(OnItemLongClickListener<Integer> listener) {
        this.onItemLongClickListener = listener;
    }
}
