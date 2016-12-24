package com.eirture.easy.main.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;

/**
 * Created by eirture on 16-12-6.
 */
@DatabaseTable
public class Journal {
    @DatabaseField(generatedId = true)
    public int id;
    @DatabaseField
    public String title;
    @DatabaseField
    public Date date;
    @DatabaseField
    public String pictures; //对应图片的表
    @DatabaseField
    public int noteId; //笔记本
    @DatabaseField
    public String content;

    public static Journal newInstance(String content, int noteId) {
        Journal j = new Journal();
        j.content = content;
        j.date = new Date();
        j.noteId = noteId;

        return j;
    }
}
