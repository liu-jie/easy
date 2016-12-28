package com.eirture.easy.edit.view;

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
import com.eirture.easy.base.bus.Result;
import com.eirture.easy.base.utils.EditorUtil;
import com.eirture.easy.base.utils.Views;
import com.eirture.easy.base.widget.HorizontalScrollViewPro;
import com.eirture.easy.main.JournalP;
import com.eirture.easy.main.event.LoadJournalE;
import com.eirture.easy.main.model.Journal;
import com.squareup.otto.Subscribe;

import org.androidannotations.annotations.AfterTextChange;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.ViewById;

/**
 * Created by eirture on 16-12-6.
 */
@EFragment(R.layout.f_journal_edit)
public class JournalEditF extends AbstractEditFragment {

    @FragmentArg
    protected String journalID = "";

    @ViewById(R.id.et_content)
    JournalEditText etContent;
    @ViewById(R.id.btn_scroll)
    ImageView btnScrollEnd;

    @ViewById(R.id.hsv_edit_options)
    HorizontalScrollViewPro hsvEditOptionBar;

    @Bean
    JournalP journalP;

    private String contentStr;
    private Journal journal;
    private boolean textWatchable = true;

    @AfterViews
    void initViews() {
        etContent.setText(contentStr);
        initOptionBar();

        if (journal == null && "".equals(journalID)) {
            journalP.loadJournal(journalID);
        }
    }

    @AfterTextChange(R.id.et_content)
    protected void afterTextChanged(Editable editable) {
        if (!textWatchable)
            return;

        setTitleSpan(editable);
    }

    private Result<Journal> loadJournalResult;

    @Subscribe
    public void loadJournalEvent(LoadJournalE e) {
        if (loadJournalResult == null) {
            loadJournalResult = Result.<Journal>create()
                    .prelude(() -> textWatchable = false)
                    .successFunction(data -> {
                        journal = data;
                        refreshContent(false);
                    })
                    .finality(() -> textWatchable = true);
        }
        loadJournalResult.result(e);
    }

    private void initOptionBar() {
        hsvEditOptionBar.addOnScrollChangedListener((l, t, oldl, oldt) -> {
            View child = hsvEditOptionBar.getChildAt(hsvEditOptionBar.getChildCount() - 1);
            int diff = child.getRight() - (hsvEditOptionBar.getWidth() + l);

            if ((l == 0 && btnScrollEnd.getRotation() == 0) ||
                    (diff < 10 && btnScrollEnd.getRotation() == 180)) {
                rotationEditArrowButton(false);
            }
        });

        btnScrollEnd.setOnClickListener(v -> rotationEditArrowButton(true));

    }

    private void setTitleSpan(Editable editable) {
        // Set the note title to be a larger size
        // Remove any existing size spans
        RelativeSizeSpan spans[] = editable.getSpans(0, editable.length(), RelativeSizeSpan.class);
        for (RelativeSizeSpan span : spans) {
            editable.removeSpan(span);
        }
        int newLinePosition = getNoteContentString().indexOf("\n");
        if (newLinePosition == 0)
            return;
        editable.setSpan(new RelativeSizeSpan(1.227f), 0, (newLinePosition > 0) ? newLinePosition : editable.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
    }

    private String getNoteContentString() {
        if (etContent == null || etContent.getText() == null) {
            return "";
        } else {
            return etContent.getText().toString();
        }
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

    private void refreshContent(boolean isNoteUpdate) {
        if (journal == null)
            return;

        String contentStr;
        int cursorPosition = newCursorLocation(contentStr = journal.content(), getNoteContentString(), etContent.getSelectionEnd());
        etContent.setText(contentStr);

        if (isNoteUpdate) {
            // Save the note so any local changes get synced
            journal.save();

            if (etContent.hasFocus() && cursorPosition != etContent.getSelectionEnd()) {
                etContent.setSelection(cursorPosition);
            }
        }


    }

    private int newCursorLocation(String newText, String oldText, int cursorLocation) {
        // Ported from the iOS app :)
        // Cases:
        // 0. All text after cursor (and possibly more) was removed ==> put cursor at end
        // 1. Text was added after the cursor ==> no change
        // 2. Text was added before the cursor ==> location advances
        // 3. Text was removed after the cursor ==> no change
        // 4. Text was removed before the cursor ==> location retreats
        // 5. Text was added/removed on both sides of the cursor ==> not handled

        int newCursorLocation = cursorLocation;

        int deltaLength = newText.length() - oldText.length();

        // Case 0
        if (newText.length() < cursorLocation)
            return newText.length();

        boolean beforeCursorMatches = false;
        boolean afterCursorMatches = false;

        try {
            beforeCursorMatches = oldText.substring(0, cursorLocation).equals(newText.substring(0, cursorLocation));
            afterCursorMatches = oldText.substring(cursorLocation, oldText.length()).equals(newText.substring(cursorLocation + deltaLength, newText.length()));
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Cases 2 and 4
        if (!beforeCursorMatches && afterCursorMatches)
            newCursorLocation += deltaLength;

        // Cases 1, 3 and 5 have no change
        return newCursorLocation;
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
