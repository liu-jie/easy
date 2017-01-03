package com.eirture.easy.main.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by eirture on 16-12-6.
 */
@DatabaseTable
public class NotebookDB {
    @DatabaseField(generatedId = true, canBeNull = false)
    public int id = 1;
    @DatabaseField
    public String title;
    @DatabaseField
    public int color;


    public static NotebookDB default_note;

    public static NotebookDB generateDefaultNotebook() {
        if (default_note == null) {
            default_note = new NotebookDB();
            default_note.id = Notebook.DEFAULT_NOTEBOOK_ID;
            default_note.title = "全部";
        }

        return default_note;
    }
}
