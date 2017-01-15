package com.eirture.easy.mine.view;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.eirture.easy.BuildConfig;
import com.eirture.easy.R;
import com.eirture.easy.base.views.BaseActivity;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

/**
 * Created by eirture on 17-1-15.
 */
@EActivity(R.layout.a_about)
public class AboutA extends BaseActivity {

    @ViewById(R.id.toolbar)
    protected Toolbar toolbar;
    @ViewById(R.id.tv_description)
    TextView tvDescription;

    @AfterViews
    protected void initViews() {
        setSupportActionBar(toolbar);
        setTitle(R.string.about);
        toolbar.setNavigationOnClickListener(v -> finish());

        tvDescription.setText(String.format("版本：%s", BuildConfig.VERSION_NAME));
    }

    @Click(R.id.item_author)
    protected void clickAuthor() {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://eirture.github.io"));
        startActivity(intent);
    }
}
