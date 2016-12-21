package com.eirture.easy.base.utils;

import android.support.annotation.NonNull;
import android.text.Editable;
import android.view.KeyEvent;
import android.widget.EditText;

/**
 * Created by Jie on 2016-08-03.
 */

public class EditorUtil {

    public static void insert2EditText(EditText editText, String value) {
        int start = editText.getSelectionStart();
        int end = editText.getSelectionEnd();

        Editable editable = editText.getText();
        editable.replace(start, end, value);
    }

    public static void insert2EditTextCurrentLine(EditText editText, @NonNull String value, boolean repeatable) {
        Editable editable = editText.getText();
        String string = editable.toString();
        int beginIndex = editText.getSelectionStart();
        int currentLineIndex = 0;
        if (beginIndex > 0) {
            currentLineIndex = string.lastIndexOf("\n", beginIndex - 1) + 1;
        }

        if (editable.toString().substring(currentLineIndex).startsWith(value)) {
            if (repeatable) {
                editable.insert(currentLineIndex, value);
            } else {
                editable.delete(currentLineIndex, currentLineIndex + value.length() + 1);
            }
        } else {
            editable.insert(currentLineIndex, value + " ");
        }
    }

    public static void option2EditText(EditText editText, String l, String r) {
        int start = editText.getSelectionStart();
        int end = editText.getSelectionEnd();

        Editable editable = editText.getText();

        editable.insert(end, r);
        editable.insert(start, l);
        editText.setSelection(end + l.length());
    }

    public static void deleteKeyEvent(EditText editText) {
        KeyEvent event = new KeyEvent(0, 0, 0, KeyEvent.KEYCODE_DEL, 0, 0, 0, 0, KeyEvent.KEYCODE_ENDCALL);
        editText.dispatchKeyEvent(event);
    }
}
