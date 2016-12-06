package com.eirture.easy.edit.view;

import android.webkit.WebView;

import com.eirture.easy.R;
import com.eirture.easy.base.views.BusFragment;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

/**
 * Created by eirture on 16-12-6.
 */
@EFragment(R.layout.f_journal_preview)
public class JournalPreviewF extends BusFragment {

    @ViewById
    WebView wvMarkdown;

    @AfterViews
    void initViews() {

    }

}
