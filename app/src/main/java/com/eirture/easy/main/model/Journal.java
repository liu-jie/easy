package com.eirture.easy.main.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by eirture on 16-12-6.
 */
@DatabaseTable
public class Journal {
    private static final SimpleDateFormat GROUP_TITLE_FORMAT = new SimpleDateFormat("yyyy年MM月");

    private final Pattern p = Pattern.compile("!\\[[^]]*]\\(([^)]+)\\)");

    public static final String FIELD_NOTE_ID = "noteId";
    public static final String NEW_LINE = "\n";
    private static final String SPACE = " ";

    @DatabaseField(generatedId = true, canBeNull = false)
    public int id;
    @DatabaseField(canBeNull = false)
    private Date date;
    @DatabaseField(canBeNull = false)
    private int noteId; //笔记本
    @DatabaseField
    private String content = "";

    private String pictures = null; //对应图片的表
    private String mContentPreview = null;
    private String mTitle = null;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getPictures() {
        if (pictures == null) {
            refreshPictures();
        }
        return pictures;
    }

    public void refreshPictures() {
        Matcher r = p.matcher(getContent());
        StringBuffer sb = new StringBuffer();
        while (r.find()) {
            if (sb.length() > 0) {
                sb.append(",");
            }
            sb.append(r.group(1));
        }
        this.pictures = sb.toString();
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

    public Journal setContent(String content) {
        this.content = content;
        this.pictures = null; // should refresh pictures when next time to call getPictures().
        this.mContentPreview = null; //
        return this;
    }

    public String getContentPreview() {
        if (mContentPreview == null) {
            refreshPreviewContent();
        }
        return mContentPreview;
    }

    private void refreshPreviewContent() {
        int firstNewLinePosition = content.indexOf(NEW_LINE);
        if (firstNewLinePosition > -1 && firstNewLinePosition < 100) {
            mTitle = content.substring(0, firstNewLinePosition).trim();

            if (firstNewLinePosition < content.length()) {
                mContentPreview = content.substring(firstNewLinePosition, content.length());
                mContentPreview = mContentPreview.replace(NEW_LINE, SPACE).replace(SPACE + SPACE, SPACE).trim();
            } else {
                mContentPreview = content;
            }
        } else {
            mTitle = content;
            mContentPreview = "";
        }
    }


    public String getTitle() {
        if (mTitle == null) {
            refreshPreviewContent();
        }
        return mTitle;
    }


    public static Journal newInstance(int noteId) {
        Journal j = new Journal();
        j.content = "";
        j.date = new Date();
        j.noteId = noteId;
        return j;
    }

    public String getGroupTitle() {
        return date == null ? "#" : GROUP_TITLE_FORMAT.format(date);
    }
}
