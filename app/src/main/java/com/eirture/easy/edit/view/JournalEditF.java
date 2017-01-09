package com.eirture.easy.edit.view;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.Spanned;
import android.text.style.RelativeSizeSpan;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.eirture.easy.R;
import com.eirture.easy.base.utils.EditorUtil;
import com.eirture.easy.base.widget.HorizontalScrollViewPro;
import com.eirture.easy.main.model.Notebook;
import com.eirture.rxcommon.utils.Views;
import com.jakewharton.rxbinding.widget.RxTextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.OnActivityResult;
import org.androidannotations.annotations.ViewById;

import java.io.File;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by eirture on 16-12-6.
 */
@EFragment(R.layout.f_journal_edit)
public class JournalEditF extends AbstractEditFragment {
    private static final int REQUEST_SELECT_PHOTO = 1;

    @FragmentArg
    int journalId = -1;
    @FragmentArg
    int noteId = Notebook.DEFAULT_NOTEBOOK_ID;

    @ViewById(R.id.et_content)
    JournalEditText etContent;
    @ViewById(R.id.btn_scroll)
    ImageView btnScrollEnd;

    @ViewById(R.id.hsv_edit_options)
    HorizontalScrollViewPro hsvEditOptionBar;

    private String contentStr = "";
    private AutoSave autoSave;

    @AfterViews
    protected void initViews() {
        refresh();
        hsvEditOptionBar.addOnScrollChangedListener((l, t, oldl, oldt) -> {
            View child = hsvEditOptionBar.getChildAt(hsvEditOptionBar.getChildCount() - 1);
            int diff = child.getRight() - (hsvEditOptionBar.getWidth() + l);

            if ((l == 0 && btnScrollEnd.getRotation() == 0) ||
                    (diff < 10 && btnScrollEnd.getRotation() == 180)) {
                rotationEditArrowButton(false);
            }
        });

        btnScrollEnd.setOnClickListener(v -> rotationEditArrowButton(true));

        RxTextView.afterTextChangeEvents(etContent)
                .doOnNext(textViewAfterTextChangeEvent -> {
                    setTitleSpan(textViewAfterTextChangeEvent.editable());
                    if (autoSave != null)
                        autoSave.save(textViewAfterTextChangeEvent.editable().toString());
                })
                .subscribe();

        etContent.requestFocus();
    }

    public void setAutoSave(AutoSave autoSave) {
        this.autoSave = checkNotNull(autoSave);
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

    private void refresh() {
        if (!isVisible())
            return;
        etContent.setText(contentStr);
    }

    @Click(R.id.op_photo)
    void clickPhoto() {
//        Intent intent = new Intent();
//        intent.setType("image/*");
//        intent.setAction(Intent.ACTION_GET_CONTENT);
//        startActivityForResult(intent, );

        startActivityForResult(new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI), REQUEST_SELECT_PHOTO);
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

    @OnActivityResult(REQUEST_SELECT_PHOTO)
    protected void selectPhotoResult(int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK)
            return;

        Uri uri = data.getData();
        EditorUtil.insert2EditText(etContent, getImageMarkdownString(uri));
    }

    private String getImageMarkdownString(Uri contentURI) {
        String result = "";
        Cursor cursor = getActivity().getContentResolver().query(contentURI, null, null, null, null);
        if (cursor != null) { // Source is Dropbox or other similar local file path
            cursor.moveToFirst();
            int idx_path = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            int idx_name = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DISPLAY_NAME);
            result = String.format("\n![%s](%s)", cursor.getString(idx_name), Uri.fromFile(new File(cursor.getString(idx_path))));
            cursor.close();
        }
        return result;
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


    private void setTitleSpan(Editable editable) {
        // Set the note title to be a larger size
        // Remove any existing size spans
        RelativeSizeSpan spans[] = editable.getSpans(0, editable.length(), RelativeSizeSpan.class);
        for (RelativeSizeSpan span : spans) {
            editable.removeSpan(span);
        }
        int newLinePosition = getContent().indexOf("\n");
        if (newLinePosition == 0)
            return;
        editable.setSpan(new RelativeSizeSpan(1.227f), 0, (newLinePosition > 0) ? newLinePosition : editable.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
    }

    public String getContent() {
        if (etContent == null || etContent == null)
            return "";
        return etContent.getText().toString().trim();
    }

    @Override
    public void setContent(@NonNull String contentStr) {
        if (contentStr == null || this.contentStr.equals(contentStr))
            return;
        this.contentStr = contentStr;
    }
}
