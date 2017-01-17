package com.eirture.easy.mine.interfaces;

/**
 * Created by eirture on 17-1-17.
 */

public interface PasswordInputOption {
    void addInputFinishListener(PasswordInputFinishListener listener);

    void clean(boolean pass);
}
