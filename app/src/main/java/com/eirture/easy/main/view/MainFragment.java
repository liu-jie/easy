package com.eirture.easy.main.view;

import com.eirture.easy.base.views.BusFragment;

/**
 * Created by eirture on 16-12-4.
 */

public abstract class MainFragment extends BusFragment {
    protected int notebookId = -1;
    protected boolean needRefreshData = false;

    public void updateNotebookId(int notebookId) {
        if (needRefreshData = (this.notebookId != notebookId)) {
            this.notebookId = notebookId;

            if (isVisible())
                refreshNotebook();
        }
    }

    protected abstract void refreshNotebook();

}
