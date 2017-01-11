package com.eirture.easy.main.view;

import com.eirture.easy.base.views.BusFragment;
import com.eirture.easy.main.model.Notebook;

/**
 * Created by eirture on 16-12-4.
 */

public abstract class MainFragment extends BusFragment {
    protected Notebook notebook;

    public void updateNotebook(Notebook notebook) {
        this.notebook = notebook;
        refreshNotebook();
    }

    protected abstract void refreshNotebook();

}
