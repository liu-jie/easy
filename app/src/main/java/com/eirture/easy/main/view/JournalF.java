package com.eirture.easy.main.view;

import android.app.Activity;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.eirture.easy.R;
import com.eirture.easy.base.bus.BusMessage;
import com.eirture.easy.base.bus.Result;
import com.eirture.easy.edit.event.DeleteJournalE;
import com.eirture.easy.edit.view.EditA_;
import com.eirture.easy.main.NotebookP;
import com.eirture.easy.main.adapter.JournalAdapter;
import com.eirture.easy.main.event.GetNotebookE;
import com.eirture.easy.main.model.Notebook;
import com.squareup.otto.Subscribe;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.OnActivityResult;
import org.androidannotations.annotations.ViewById;

/**
 * Created by eirture on 16-12-4.
 */
@EFragment(R.layout.c_recycler_view)
public class JournalF extends MainFragment {
    private static final int REQUEST_EDIT_CODE = 1;

    @ViewById(R.id.swipe)
    SwipeRefreshLayout refreshLayout;

    @ViewById(R.id.rv_content)
    RecyclerView rvContent;
    JournalAdapter mAdapter;
    @Bean
    NotebookP notebookP;

    private AlertDialog itemOptionDialog;

    @AfterViews
    void initViews() {
        if (mAdapter == null) {
            mAdapter = new JournalAdapter();
            mAdapter.addOnItemLongClickListener(data -> {
                if (itemOptionDialog == null) {
                    itemOptionDialog = new AlertDialog.Builder(getContext())
                            .setItems(R.array.journal_item_options, (dialog, which) -> {
                                if (which == 0)
                                    notebookP.deleteJournal((int) mAdapter.getItemId(data), data);
                            })
                            .create();
                }
                itemOptionDialog.show();
                return true;
            });
            mAdapter.addOnItemClickListener(data -> EditA_.intent(this).notebookId(notebookId).journalId(data).startForResult(REQUEST_EDIT_CODE));

            rvContent.setLayoutManager(new LinearLayoutManager(getContext()));
            DividerItemDecoration decoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
            decoration.setDrawable(ContextCompat.getDrawable(getContext(), R.drawable.line_item_decoration));
            rvContent.addItemDecoration(decoration);

            refreshLayout.setOnRefreshListener(() -> refreshNotebook());
        }
        rvContent.setAdapter(mAdapter);
        refreshNotebook();
    }

    private Result<BusMessage> deleteJournalResult = Result.<BusMessage>create()
            .successFunction(action -> mAdapter.removePosition(action.code))
            .errorFunction(action -> Toast.makeText(getContext(), action.message, Toast.LENGTH_SHORT).show());

    @Subscribe
    public void deleteJournalEvent(DeleteJournalE e) {
        deleteJournalResult.result(e);
    }

    @Override
    protected void refreshNotebook() {
        notebookP.getNotebookById(notebookId);
    }

    private Result<Notebook> getNotebookResult = Result.<Notebook>create()
            .successFunction(action -> updateNotebook(action))
            .errorFunction(action -> {
                Toast.makeText(getContext(), "notebook is not exist", Toast.LENGTH_SHORT).show();
                System.err.println(action.message);
            })
            .finality(() -> {
                if (refreshLayout.isRefreshing()) refreshLayout.setRefreshing(false);
            });

    @Subscribe
    public void loadNotebookEvent(GetNotebookE e) {
        getNotebookResult.result(e);
    }

    private void updateNotebook(Notebook notebook) {
        mAdapter.updateNotebook(notebook);
    }

    @OnActivityResult(REQUEST_EDIT_CODE)
    protected void editResult(int resultCode) {
        if (resultCode != Activity.RESULT_OK)
            return;

        refreshNotebook();
    }

}
