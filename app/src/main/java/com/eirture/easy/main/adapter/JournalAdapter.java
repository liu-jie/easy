package com.eirture.easy.main.adapter;

import android.view.View;
import android.view.ViewGroup;

import com.eirture.easy.base.widget.HeadSuperRecyclerAdapter;
import com.eirture.easy.base.widget.OnItemClickListener;
import com.eirture.easy.base.widget.OnItemLongClickListener;
import com.eirture.easy.main.adapter.holder.JournalHeadHolder;
import com.eirture.easy.main.adapter.holder.JournalHolder;
import com.eirture.easy.main.model.Journal;
import com.eirture.easy.main.model.Notebook;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by eirture on 16-12-4.
 */

public class JournalAdapter extends HeadSuperRecyclerAdapter<JournalHolder, JournalHeadHolder> {
    private Notebook notebook;
    private List<Journal> journals = new ArrayList<>();

    private OnItemLongClickListener<Integer> onItemLongClickListener;
    private OnItemClickListener<Integer> onItemClickListener;
    private View.OnClickListener clickAddListener;
    private View.OnClickListener clickSelectPhotoListener;

    private Set<Integer> groupLabelIndexes = new HashSet<>();

    public void updateNotebook(Notebook notebook) {
        this.notebook = notebook;
        journals = notebook.journals();
        groupLabelIndexes.clear();
        notifyDataSetChanged();
    }

    /**
     * 判断是否需要更分组头信息
     *
     * @param position 减掉 head 后的索引值
     * @return
     */
    public boolean changeGroupLabelStr(int position) {
        return groupLabelIndexes.contains(new Integer(position));
    }

    public String getGroupLabelStr(int position) {
        return journals.get(position).getGroupTitle();
    }

    @Override
    public int getCount() {
        return journals.size();
    }

    @Override
    public void onBindHolder(JournalHolder holder, int position) {
        Journal journal = journals.get(position);
        boolean isDisplayGroupLabel = position == 0 || !journal.getGroupTitle().equals(journals.get(position - 1).getGroupTitle());
        if (isDisplayGroupLabel) {
            groupLabelIndexes.add(position); // head count == 1
            if (position > 1) {
                groupLabelIndexes.add(position - 1);
            }
        }
        holder.bindData(journal, position, isDisplayGroupLabel
        );
    }

    @Override
    public void onBindHeadHolder(JournalHeadHolder headHolder, int position) {
        headHolder.btnAdd.setOnClickListener(v -> {
            if (clickAddListener != null) clickAddListener.onClick(v);
        });
        headHolder.btnPhoto.setOnClickListener(v -> {
            if (clickSelectPhotoListener != null) clickSelectPhotoListener.onClick(v);
        });
        headHolder.bindData(notebook);
    }

    @Override
    public JournalHolder onCreateHolder(ViewGroup parent, int viewType) {
        JournalHolder holder = new JournalHolder(parent);
        holder.itemView.setOnLongClickListener(v -> {
            if (onItemLongClickListener == null)
                return false;
            return onItemLongClickListener.onLongClick(holder.position());
        });

        holder.itemView.setOnClickListener(v -> {
            if (onItemClickListener == null)
                return;
            onItemClickListener.onItemClick(holder.journalId());
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

    public void addOnItemClickListener(OnItemClickListener<Integer> listener) {
        this.onItemClickListener = listener;
    }

    public void addOnClickAddListener(View.OnClickListener listener) {
        this.clickAddListener = listener;
    }

    public void addOnClickSelectPhotoListener(View.OnClickListener listener) {
        this.clickSelectPhotoListener = listener;
    }
}
