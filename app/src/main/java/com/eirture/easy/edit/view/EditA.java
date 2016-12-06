package com.eirture.easy.edit.view;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;

import com.eirture.easy.R;
import com.eirture.easy.base.views.BaseActivity;

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

    private JournalPreviewF previewF;
    private FragmentManager fm;

    @AfterInject
    void init() {
        previewF = JournalPreviewF_.builder().build();
        fm = getSupportFragmentManager();


    }

    @AfterViews
    void initViews() {
        setSupportActionBar(toolbar);
        initToolbar();


        fm.beginTransaction()
                .replace(R.id.fl_container, previewF)
                .commit();
    }

    private void initToolbar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(v -> finish());
    }
}
