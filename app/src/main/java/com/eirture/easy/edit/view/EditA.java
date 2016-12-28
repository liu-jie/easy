package com.eirture.easy.edit.view;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

import com.eirture.easy.R;
import com.eirture.easy.base.views.BaseActivity;
import com.eirture.easy.edit.EditP;
import com.eirture.easy.edit.event.QueryJournalE;
import com.eirture.easy.main.model.Journal;
import com.squareup.otto.Subscribe;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;

/**
 * Created by eirture on 16-12-6.
 */
@EActivity(R.layout.a_edit)
public class EditA extends BaseActivity {

    @Extra
    int journalId = -1;  // if create new journal extra journalId is empty;
    @Extra
    int notebookId = 0;

    @ViewById
    Toolbar toolbar;

    private AbstractEditFragment currentF;
    private JournalPreviewF previewF;
    private JournalEditF editF;
    private FragmentManager fm;

    private String editContent;
    private Journal journal;

    @Bean
    EditP editP;

    @AfterInject
    void init() {
        previewF = JournalPreviewF_.builder().build();
        editF = JournalEditF_.builder().build();
        fm = getSupportFragmentManager();
    }

    @AfterViews
    void initViews() {
        setSupportActionBar(toolbar);
        initToolbar();
        if (journalId != -1) {
            // read an existent journal
            editP.readJournal(journalId);

            // to do this when got journal data
            changeFragment(previewF);
        } else {
            // create new journal
            changeFragment(editF);
        }
    }

    @Subscribe
    public void receiveQueryJournal(QueryJournalE e) {
        
    }

    private void initToolbar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(v -> finish());
        toolbar.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.menu_btn:
                    if (currentF == editF) {
                        editContent = editF.getContent();
                    }
                    changeFragment(currentF == editF ? previewF : editF);
                    break;
            }
            return true;
        });
    }

    private void changeFragment(AbstractEditFragment fragment) {
        if (currentF == fragment)
            return;
        Menu menu;
        if ((menu = toolbar.getMenu()).size() > 0) {
            menu.getItem(0).setTitle(fragment == previewF ? R.string.edit : R.string.finish);
        }
        fragment.setContent(editContent);

        fm.beginTransaction()
                .replace(R.id.fl_container, currentF = fragment)
                .commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.editor, menu);
        return true;
    }

}
