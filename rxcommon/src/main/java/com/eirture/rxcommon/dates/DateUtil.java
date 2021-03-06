package com.eirture.rxcommon.dates;

import java.util.Calendar;
import java.util.Date;

import static com.eirture.rxcommon.base.Preconditions.checkNotNull;

public class DateUtil {
    private static final String[] WEEK_DAYS = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};

    public static boolean isThisWeek(Date date) {
        if (date == null)
            return false;

        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int beginWeekDay = c.get(Calendar.DAY_OF_YEAR) - c.get(Calendar.DAY_OF_WEEK) + 1;
        c.setTime(date);
        int dayOfYear = c.get(Calendar.DAY_OF_YEAR);
        return c.get(Calendar.YEAR) == year && dayOfYear >= beginWeekDay && dayOfYear - beginWeekDay < 7;
    }

    public static String getWeekName(Date date) {
        if (date == null)
            return "";

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return getChineseWeekName(cal);
    }

    public static int getDayOfMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(checkNotNull(date, "date == null"));
        return cal.get(Calendar.DAY_OF_MONTH);
    }

    private static String getChineseWeekName(Calendar cal) {
        return WEEK_DAYS[cal.get(Calendar.DAY_OF_WEEK) - 1];
    }

    public static boolean sameDay(Date dateOne, Date dateTwo) {
        if (dateOne == null || dateTwo == null)
            return false;

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateOne);

        int thenYear = calendar.get(Calendar.YEAR);
        int thenYearDay = calendar.get(Calendar.DAY_OF_YEAR);

        calendar.setTime(dateTwo);
        return (thenYear == calendar.get(Calendar.YEAR))
                && (thenYearDay == calendar.get(Calendar.DAY_OF_YEAR));
    }


    public static Date addMonth(Date date, int n) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, n);
        return cal.getTime();
    }

    private DateUtil() {
    }

}
