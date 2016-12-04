package com.eirture.easy.main.view;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.eirture.easy.R;
import com.eirture.easy.main.adapter.JournalAdapter;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

/**
 * Created by eirture on 16-12-4.
 */
@EFragment(R.layout.f_journal_list)
public class JournalListF extends MainFragment {

    @ViewById(R.id.rv_content)
    RecyclerView rvContent;

    JournalAdapter mAdapter;

    @AfterViews
    void initViews() {
        if (mAdapter == null) {
            mAdapter = new JournalAdapter();
            rvContent.setLayoutManager(new LinearLayoutManager(getContext()));
        }

        rvContent.setAdapter(mAdapter);
    }

}
