package com.eirture.easy.base.utils;

import java.util.Calendar;
import java.util.Date;

public class DateUtil {
    private static final String[] WEEK_DAYS = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};


    public static boolean isThisWeek(Date date) {
        Calendar c = Calendar.getInstance();
        System.out.println("day of week: " + c.get(Calendar.DAY_OF_WEEK));
        c.add(Calendar.DATE, c.get(Calendar.DAY_OF_WEEK) - 1);
        return c.getTime().before(date);
    }

    public static String getJournalItemDate(Date date) {
        if (date == null)
            return "";

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return new StringBuffer(getChineseWeekName(cal))
                .append("\n")
                .append(cal.get(Calendar.DAY_OF_MONTH))
                .toString();
    }

    private static String getChineseWeekName(Calendar cal) {
        return WEEK_DAYS[cal.get(Calendar.DAY_OF_WEEK) - 1];
    }

    private DateUtil() {
    }

}
