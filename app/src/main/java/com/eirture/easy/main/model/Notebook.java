package com.eirture.easy.main.model;

import android.support.annotation.NonNull;
import android.text.format.DateUtils;

import com.eirture.rxcommon.dates.DateUtil;
import com.google.common.base.CharMatcher;
import com.google.common.base.Strings;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by eirture on 16-12-23.
 */

public class Notebook {
    public static final int DEFAULT_NOTEBOOK_ID = 1;
    private NotebookDB notebookDB;
    private List<Journal> journals;
    private int count = 0;
    private int days = 0;
    private int pictures = 0;
    private int weeklyCount = 0;
    private int todays = 0;

    public Notebook() {
        notebookDB = new NotebookDB();
        journals = new ArrayList<>();
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

    public int getCount() {
        return count;
    }

    public int getDays() {
        return days;
    }

    public int getPictures() {
        return pictures;
    }

    public int getWeeklyCount() {
        return weeklyCount;
    }

    public int getTodayCount() {
        return todays;
    }

    private Notebook(NotebookDB notebookDB, List<Journal> journals) {
        this.notebookDB = notebookDB;
        if ((this.journals = journals) == null) {
            this.journals = new ArrayList<>();
        }
        calculateData();
    }

    public void calculateData() {
        count = journals.size();
        Collections.sort(journals, (o1, o2) -> o1.getDate().compareTo(o2.getDate()));

        Date lastDate = null;
        for (Journal journal :
                journals) {
            if (lastDate == null || !DateUtil.sameDay(lastDate, journal.getDate())) {
                days++;
                lastDate = journal.getDate();
            }

            if (DateUtil.isThisWeek(journal.getDate())) {
                weeklyCount++;
            }

            if (DateUtils.isToday(journal.getDate().getTime())) {
                todays++;
            }

            String pStr = journal.getPictures();
            if (!Strings.isNullOrEmpty(pStr)) {
                int pCount = CharMatcher.is(',')
                        .countIn(pStr);
                pictures += pCount + 1;
            }
        }
    }

    public List<Journal> journals() {
        return journals;
    }

    public static Notebook createFromNotebookDB(@NonNull NotebookDB notebookDB, @NonNull List<Journal> journals) {
        return new Notebook(checkNotNull(notebookDB, "notebookDB == null"), journals);
    }
}
