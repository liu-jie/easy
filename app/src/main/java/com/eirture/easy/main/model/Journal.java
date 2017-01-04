package com.eirture.easy.main.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;

/**
 * Created by eirture on 16-12-6.
 */
@DatabaseTable
public class Journal {
    public static final String FIELD_NOTE_ID = "noteId";
    public static final String NEW_LINE = "\n";
    private static final String SPACE = " ";


    @DatabaseField(generatedId = true, canBeNull = false)
    public int id;
    @DatabaseField
    private String title;
    @DatabaseField(canBeNull = false)
    private Date date;
    @DatabaseField
    private String pictures; //对应图片的表
    @DatabaseField(canBeNull = false)
    private int noteId; //笔记本
    @DatabaseField
    private String content = "";

    private String mTitle = "";
    private String mContentPreview = "";

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getPictures() {
        return pictures;
    }

    public void setPictures(String pictures) {
        this.pictures = pictures;
    }

    public int getNoteId() {
        return noteId;
    }

    public void setNoteId(int noteId) {
        this.noteId = noteId;
    }


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        refreshContent(content);
    }

    public Journal refreshContent(String content) {
        this.content = content;
        refreshPreviewContent();
        return this;
    }

    public String getContentPreview() {
        return mContentPreview;
    }

    private void refreshPreviewContent() {
        int firstNewLinePosition = content.indexOf(NEW_LINE);
        if (firstNewLinePosition > -1 && firstNewLinePosition < 200) {
            mTitle = content.substring(0, firstNewLinePosition).trim();

            if (firstNewLinePosition < content.length()) {
                mContentPreview = content.substring(firstNewLinePosition, content.length());
                mContentPreview = mContentPreview.replace(NEW_LINE, SPACE).replace(SPACE + SPACE, SPACE).trim();
            } else {
                mContentPreview = content;
            }
        } else {
            mTitle = content;
            mContentPreview = content;
        }
    }

    public static Journal newInstance(int noteId) {
        Journal j = new Journal();
        j.content = "";
        j.date = new Date();
        j.noteId = noteId;
        return j;
    }
}
