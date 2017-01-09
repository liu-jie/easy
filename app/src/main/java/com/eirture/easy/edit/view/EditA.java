package com.eirture.easy.edit.view;

import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

import com.eirture.easy.R;
import com.eirture.easy.base.bus.Result;
import com.eirture.easy.base.views.BusActivity;
import com.eirture.easy.edit.EditP;
import com.eirture.easy.edit.event.QueryJournalE;
import com.eirture.easy.main.model.Journal;
import com.eirture.easy.main.model.Notebook;
import com.squareup.otto.Subscribe;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;

import java.text.SimpleDateFormat;
import java.util.Date;

import static com.eirture.rxcommon.base.Preconditions.checkNotNull;

/**
 * Created by eirture on 16-12-6.
 */
@EActivity(R.layout.a_edit)
public class EditA extends BusActivity implements AutoSave {
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy年MM月dd日");

    @Extra
    int journalId = -1;  // if journalId == -1 that create new journal, and if journalId == -2, start as select photo mode;
    @Extra
    int notebookId = Notebook.DEFAULT_NOTEBOOK_ID;

    @ViewById
    Toolbar toolbar;

    private AbstractEditFragment currentF;
    private JournalPreviewF previewF;
    private JournalEditF editF;
    private FragmentManager fm;

    private boolean updated = false;
    private String editContent = "";
    private Journal journal;

    @Bean
    EditP editP;

    @AfterInject
    void init() {
        previewF = JournalPreviewF_.builder().build();
        editF = JournalEditF_.builder().journalId(journalId).noteId(notebookId).build();
        editF.setAutoSave(this);

        fm = getSupportFragmentManager();
    }

    @AfterViews
    void initViews() {
        setSupportActionBar(toolbar);
        initToolbar();

        if (journalId >= 0) {
            editP.readJournal(journalId);
        } else {
            journal = Journal.newInstance(notebookId);
            changeFragment(editF);
            if (journalId == -2) {
                editF.startAsSelectPhoto();
            }
        }
    }

    Result<Journal> result = Result.<Journal>create()
            .successFunction(action -> refreshJournal(action))
            .errorFunction(action -> System.err.println("journal is not exist! " + journalId));

    @Subscribe
    public void receiveQueryJournal(QueryJournalE e) {
        result.result(e);
    }

    private void initToolbar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        toolbar.setTitle(DATE_FORMAT.format(new Date()));
        toolbar.setNavigationOnClickListener(v -> finish());
        toolbar.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.menu_btn:
                    boolean isPreview = currentF == previewF;
                    item.setTitle(isPreview ? R.string.preview : R.string.edit);
                    changeFragment(isPreview ? editF : previewF);
                    break;
            }
            return true;
        });
    }

    private void refreshJournal(Journal journal) {
        if ((this.journal = journal) == null) {
            return;
        }

        this.journal = journal;
        toolbar.setTitle(DATE_FORMAT.format(journal.getDate()));
        editContent = journal.getContent();
        changeFragment(previewF);
    }

    private void changeFragment(AbstractEditFragment fragment) {
        if (currentF == fragment)
            return;
        if (currentF == editF) {
            editContent = editF.getContent();

        }
        fragment.setContent(editContent);
        fm.beginTransaction()
                .replace(R.id.fl_container, currentF = fragment)
                .commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.editor, menu);
        menu.getItem(0).setTitle(currentF == previewF ? R.string.edit : R.string.preview);
        return true;
    }

    @Override
    public void save(@NonNull String content) {
        if (checkNotNull(content).equals(journal.getContent()))
            return;

        editP.updateJournal(journal.setContent(content));
        updated = true;
    }

    @Override
    public void finish() {
        if (updated) {
            setResult(RESULT_OK);
        }
        super.finish();
    }
}
