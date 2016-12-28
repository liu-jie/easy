package com.eirture.easy.main.model;

import com.simperium.client.BucketObject;
import com.simperium.client.BucketSchema;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by eirture on 16-12-6.
 */
public class Journal extends BucketObject {
//    public int id;
//    public String title;
//    public Date date;
//    public String pictures; //对应图片的表
//    public int noteId; //笔记本
//    public String content;

    public static final String BUCKET_NAME = "journal";
    public static final String NEW_LINE = "\n";
    private static final String SPACE = " ";


    public static final String TITLE_PROPERTY = "title";
    public static final String DATE_PROPERTY = "date";
    public static final String PICTURES_PROPERTY = "pictures";
    public static final String NOTE_ID_PROPERTY = "note_id";
    public static final String CONTENT_PROPERTY = "content";
    public static final String TITLE_INDEX_NAME = "title";
    public static final String CONTENT_PREVIEW_INDEX_NAME = "contentPreview";

    public static final String BLANK_CONTENT = " ";


    private static final int MAX_PREVIEW_CHARS = 300;
    protected String mTitle = null;
    protected String mContentPreview = null;

    public Journal(String key, JSONObject properties) {
        super(key, properties);
    }

    public Journal(String key) {
        super(key);
    }

    protected void updateTitleAndPreview() {
        String content = content().trim();
        if (content.length() > MAX_PREVIEW_CHARS) {
            content = content.substring(0, MAX_PREVIEW_CHARS - 1);
        }

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

    public String title() {
        if (mTitle == null) {
            updateTitleAndPreview();
        }
        return mTitle;
    }

    public String content() {
        Object content = getProperty(CONTENT_PROPERTY);
        if (content == null) {
            return BLANK_CONTENT;
        }
        return (String) content;
    }

    public void setContent(String content) {
        mTitle = null;
        mContentPreview = null;
        setProperty(CONTENT_PROPERTY, content);
    }

    public String getContentPreview() {
        if (mContentPreview == null) {
            updateTitleAndPreview();
        }
        return mContentPreview;
    }

    public static class Schema extends BucketSchema<Journal> {

        public Schema() {
            autoIndex();
            setDefault(CONTENT_PROPERTY, "");
            setDefault(PICTURES_PROPERTY, new JSONArray());
        }

        @Override
        public String getRemoteName() {
            return BUCKET_NAME;
        }

        @Override
        public Journal build(String key, JSONObject properties) {
            return new Journal(key, properties);
        }

        @Override
        public void update(Journal journal, JSONObject properties) {
            journal.setProperties(properties);
            journal.mTitle = null;
            journal.mContentPreview = null;
        }
    }
}
