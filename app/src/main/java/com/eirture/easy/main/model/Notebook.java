package com.eirture.easy.main.model;

/**
 * Created by eirture on 16-12-23.
 */

public class Notebook {
    private NotebookDB notebookDB;
    public int count;
    public int days;
    public int pictures;
    public int weeklyCount;
    public int todays;

    public Notebook() {
    }

    public String title() {
        if (notebookDB == null) {
            return "";
        }
        return notebookDB.title;
    }

    public int id() {
        if (notebookDB == null)
            return -1;

        return notebookDB.id;
    }

    public int color() {
        if (notebookDB == null)
            return 0xffffff;
        return notebookDB.color;
    }

    public Notebook(NotebookDB notebookDB) {
        this.notebookDB = notebookDB;
    }
}
