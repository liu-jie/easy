package com.eirture.easy.main.view;

import android.app.Activity;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.widget.Toast;

import com.eirture.easy.R;
import com.eirture.easy.base.bus.Result;
import com.eirture.easy.base.widget.helper.SimpleItemTouchCallback;
import com.eirture.easy.main.NotebookP;
import com.eirture.easy.main.adapter.JournalAdapter;
import com.eirture.easy.main.event.GetNotebookE;
import com.eirture.easy.main.model.Notebook;
import com.squareup.otto.Subscribe;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

/**
 * Created by eirture on 16-12-4.
 */
@EFragment(R.layout.c_recycler_view)
public class JournalF extends MainFragment {

    @ViewById(R.id.rv_content)
    RecyclerView rvContent;
    JournalAdapter mAdapter;
    @Bean
    NotebookP notebookP;

    private int notebookId = -1;

    @AfterViews
    void initViews() {
        if (mAdapter == null) {
            mAdapter = new JournalAdapter();
            rvContent.setLayoutManager(new LinearLayoutManager(getContext()));
            DividerItemDecoration decoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
            decoration.setDrawable(ContextCompat.getDrawable(getContext(), R.drawable.line_item_decoration));
            rvContent.addItemDecoration(decoration);
            ItemTouchHelper helper = new ItemTouchHelper(new SimpleItemTouchCallback(mAdapter));
            helper.attachToRecyclerView(rvContent);
        }
        rvContent.setAdapter(mAdapter);
        refreshNotebookId();
    }

    private void refreshNotebookId() {
        Activity activity = getActivity();
        if (activity instanceof MainA) {
            int nbId = ((MainA) activity).getNotebookId();
            if (nbId != notebookId) {
                // notebook is changed. must refresh data;
                notebookP.getNotebookById(notebookId = nbId);
            }
        }
    }

    private Result<Notebook> getNotebookResult = Result.<Notebook>create()
            .successFunction(action -> updateNotebook(action))
            .errorFunction(action -> Toast.makeText(getContext(), "notebook is not exist", Toast.LENGTH_SHORT).show());

    @Subscribe
    public void loadNotebookEvent(GetNotebookE e) {
        getNotebookResult.result(e);
    }

    private void updateNotebook(Notebook notebook) {
//        System.out.println("updateNotebook: " + notebook.getCount());
        mAdapter.updateNotebook(notebook);
    }


}
