package com.eirture.easy.edit.view;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

import com.eirture.easy.R;
import com.eirture.easy.base.views.BaseActivity;
import com.eirture.easy.base.views.BaseFragment;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

/**
 * Created by eirture on 16-12-6.
 */
@EActivity(R.layout.a_edit)
public class EditA extends BaseActivity {
    @ViewById
    Toolbar toolbar;

    private BaseFragment currentF;
    private JournalPreviewF previewF;
    private JournalEditF editF;
    private FragmentManager fm;

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

        changeFragment(editF);
    }

    private void initToolbar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(v -> finish());
        toolbar.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.menu_btn:
                    if (currentF != previewF) {
                        previewF.setContent(editF.getContent());
                        changeFragment(previewF);
                        item.setTitle(R.string.edit);
                    } else {
                        changeFragment(editF);
                        item.setTitle(R.string.finish);
                    }
                    break;
            }
            return true;
        });
    }

    private void changeFragment(BaseFragment fragment) {
        if (currentF == fragment)
            return;

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
