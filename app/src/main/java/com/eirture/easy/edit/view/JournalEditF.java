package com.eirture.easy.edit.view;

import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.eirture.easy.R;
import com.eirture.easy.base.utils.EditorUtil;
import com.eirture.easy.base.utils.Views;
import com.eirture.easy.base.widget.HorizontalScrollViewPro;
import com.jakewharton.rxbinding.widget.RxTextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

/**
 * Created by eirture on 16-12-6.
 */
@EFragment(R.layout.f_journal_edit)
public class JournalEditF extends AbstractEditFragment {

    @ViewById(R.id.et_content)
    JournalEditText etContent;
    @ViewById(R.id.btn_scroll)
    ImageView btnScrollEnd;

    @ViewById(R.id.hsv_edit_options)
    HorizontalScrollViewPro hsvEditOptionBar;

    private String contentStr;

    @AfterViews
    void initViews() {
        etContent.setText(contentStr);
        hsvEditOptionBar.addOnScrollChangedListener((l, t, oldl, oldt) -> {
            View child = hsvEditOptionBar.getChildAt(hsvEditOptionBar.getChildCount() - 1);
            int diff = child.getRight() - (hsvEditOptionBar.getWidth() + l);

            if ((l == 0 && btnScrollEnd.getRotation() == 0) ||
                    (diff < 10 && btnScrollEnd.getRotation() == 180)) {
                rotationEditArrowButton(false);
            }
        });

        btnScrollEnd.setOnClickListener(v -> rotationEditArrowButton(true));

//        RxTextView.textChangeEvents(etContent)

    }

    private void rotationEditArrowButton(boolean scrollbar) {
        float rAngle = 0 == btnScrollEnd.getRotation() ? 180 : 0;
        btnScrollEnd.animate().setInterpolator(new DecelerateInterpolator(1.5f))
                .rotationBy(180 - rAngle)
                .rotation(rAngle)
                .start();
        if (scrollbar) {
            hsvEditOptionBar.smoothScrollTo(rAngle == 0 ? hsvEditOptionBar.getRight() : hsvEditOptionBar.getLeft(), 0);
        }
    }

    @Click(R.id.op_bold)
    void clickBold() {
        insertText("**");
    }

    @Click(R.id.op_italic)
    void clickItalic() {
        insertText("*");
    }

    @Click(R.id.op_quote)
    void clickQuote() {
        insertStart(">", false);
    }

    @Click(R.id.op_head)
    void clickHead() {
        insertStart("#", true);
    }

    @Click(R.id.op_list_item)
    void clickListItem() {
        insertStart("-", false);
    }

    @Click(R.id.op_link)
    void clickLink() {
        initLinkDialog();
        addLinkDialog.show();
    }


    AlertDialog addLinkDialog;
    TextView etLinkTitle, etLinkAddress;

    private void initLinkDialog() {
        if (addLinkDialog != null) {
            etLinkAddress.setText("");
            etLinkTitle.setText("");
            return;
        }

        View content = getActivity().getLayoutInflater().inflate(R.layout.p_edit_link, null);
        etLinkAddress = Views.find(content, R.id.aet_link_address);
        etLinkTitle = Views.find(content, R.id.aet_link_name);

        addLinkDialog = new AlertDialog.Builder(getContext())
                .setView(content)
                .setPositiveButton(R.string.ok,
                        (dialog, which) -> {
                            String linkTitle = etLinkTitle.getText().toString();
                            String linkStr = etLinkAddress.getText().toString();
                            if ("".equals(linkTitle)) {
                                Toast.makeText(getContext(), R.string.link_name_cannot_empty, Toast.LENGTH_SHORT).show();
                                return;
                            }

                            if ("".equals(linkStr))
                                return;

                            if (!(linkStr.startsWith("http://") || linkStr.startsWith("https://"))) {
                                linkStr = "http://" + linkStr;
                            }
                            EditorUtil.insert2EditText(etContent, String.format("[%s](%s)", linkTitle, linkStr));
                        })
                .create();

    }


    private void insertText(@NonNull String l) {
        EditorUtil.option2EditText(etContent, l, l);
    }

    private void insertStart(@NonNull String startStr, boolean repeatable) {
        EditorUtil.insert2EditTextCurrentLine(etContent, startStr, repeatable);
    }

    public String getContent() {
        if (etContent == null)
            return "";

        return contentStr = etContent.getText().toString();
    }

    @Override
    public void setContent(@NonNull String contentStr) {
        if (contentStr == null)
            return;

        this.contentStr = contentStr;
    }
}
