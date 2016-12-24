package com.eirture.easy.edit.view;

import android.webkit.WebView;

import com.commonsware.cwac.anddown.AndDown;
import com.eirture.easy.R;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

/**
 * Created by eirture on 16-12-6.
 */
@EFragment(R.layout.f_journal_preview)
public class JournalPreviewF extends AbstractEditFragment {

    @ViewById
    WebView wvMarkdown;


    private String contentStr, mCss;

    @AfterInject
    void init() {
        mCss = "<link rel=\"stylesheet\" type=\"text/css\" href=\"light.css\" />";
    }

    @AfterViews
    void initViews() {
        String content = mCss + new AndDown().markdownToHtml(contentStr);
        wvMarkdown.loadDataWithBaseURL("file:///android_asset/", content, "text/html", "utf-8", null);
        System.out.println(content);
    }

    @Override
    public void setContent(String contentStr) {
        this.contentStr = contentStr;
    }

}
