package com.eirture.easy.main.view;

import android.app.Activity;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.eirture.easy.R;
import com.eirture.easy.base.bus.BusMessage;
import com.eirture.easy.base.bus.Result;
import com.eirture.easy.edit.event.DeleteJournalE;
import com.eirture.easy.edit.view.EditA_;
import com.eirture.easy.edit.view.SelectPhotoA;
import com.eirture.easy.main.NotebookP;
import com.eirture.easy.main.adapter.JournalAdapter;
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
    private static final int REQUEST_SELECT_PHOTO = 2;
    @ViewById(R.id.rv_content)
    RecyclerView rvContent;
    @ViewById(R.id.group_lab)
    TextView groupLab;

    JournalAdapter mAdapter;
    @Bean
    NotebookP notebookP;

    private AlertDialog itemOptionDialog;
    private DividerItemDecoration decoration;

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
            mAdapter.addOnItemClickListener(data -> EditA_.intent(this).notebookId(notebook.id()).journalId(data).startForResult(REQUEST_EDIT_CODE));
            mAdapter.addOnClickAddListener(v -> EditA_.intent(this).notebookId(notebook.id()).startForResult(REQUEST_EDIT_CODE));
            mAdapter.addOnClickSelectPhotoListener(v -> SelectPhotoA.start(this, REQUEST_SELECT_PHOTO));

            decoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
            decoration.setDrawable(ContextCompat.getDrawable(getContext(), R.drawable.line_item_decoration));

        }
        rvContent.setLayoutManager(new LinearLayoutManager(getContext()));
        rvContent.addItemDecoration(decoration);
        rvContent.setAdapter(mAdapter);
        initGroupLab();
        refreshNotebook();
    }

    @OnActivityResult(REQUEST_SELECT_PHOTO)
    protected void resultSelectPhoto(int resultCode, @OnActivityResult.Extra(SelectPhotoA.RESULT_PATH_KEY) String path) {
        if (resultCode != Activity.RESULT_OK)
            return;
        EditA_.intent(this)
                .notebookId(notebook.id())
                .photoPath(path)
                .startForResult(REQUEST_EDIT_CODE);
    }

    private void initGroupLab() {
        rvContent.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int firstItemPosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition() - 1;

                groupLab.setVisibility(firstItemPosition >= 0 ? View.VISIBLE : View.GONE);
                if (mAdapter.changeGroupLabelStr(firstItemPosition)) {
                    groupLab.setText(mAdapter.getGroupLabelStr(firstItemPosition));
                }
            }
        });
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
        if (notebook == null)
            return;
        mAdapter.updateNotebook(notebook);
    }

    @OnActivityResult(REQUEST_EDIT_CODE)
    protected void editResult(int resultCode) {
        if (resultCode != Activity.RESULT_OK)
            return;
        System.out.println("refresh notebook.-----------------------");
        notebookP.getNotebookById(notebook.id());
        refreshNotebook();
    }

}
