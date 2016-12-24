package com.eirture.easy.main.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by eirture on 16-12-6.
 */
@DatabaseTable
public class NotebookDB {
    @DatabaseField(id = true, generatedId = true)
    public int id;
    @DatabaseField
    public String title;
    @DatabaseField
    public int color;
}
