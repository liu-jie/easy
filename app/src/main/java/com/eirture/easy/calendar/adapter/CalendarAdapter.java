package com.eirture.easy.calendar.adapter;

import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.eirture.easy.R;
import com.eirture.easy.main.model.Journal;
import com.eirture.easy.main.model.Notebook;
import com.eirture.rxcommon.dates.DateUtil;
import com.eirture.rxcommon.utils.Views;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import rx.Observable;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by eirture on 17-1-9.
 */

public class CalendarAdapter extends BaseAdapter {
    static final String[] WEEK_NAME = {"日", "一", "二", "三", "四", "五", "六"};
    static final SimpleDateFormat DATE_TITLE_FORMAT = new SimpleDateFormat("yyyy年MM月");

    private SparseArray<List<Integer>> markedDays;
    private List<Integer> markeds;
    private Date date;
    private int counts = 0, preCounts = 0;
    private int currentYear, currentMonth, today, year, month;
    private Calendar calendar;

    public CalendarAdapter() {
        Calendar cal = Calendar.getInstance();
        currentYear = cal.get(Calendar.YEAR);
        currentMonth = cal.get(Calendar.MONTH);
        today = cal.get(Calendar.DAY_OF_MONTH);

        calendar = Calendar.getInstance();
        markedDays = new SparseArray<>();
        updateDate(new Date());
    }

    public void updateNotebook(Notebook notebook) {
        markedDays.clear();
        if (notebook == null)
            return;

        Observable.from(notebook.journals())
                .map(Journal::getDate)
                .doOnNext(date1 -> {
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(date1);
                    int key = generateKey(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH));
                    List<Integer> days = markedDays.get(key);
                    if (days == null) {
                        days = new ArrayList<>();
                        markedDays.put(key, days);
                    }
                    days.add(cal.get(Calendar.DAY_OF_MONTH));
                }).subscribe();

        notifyDataSetChanged();
    }

    public void updateDate(Date date) {
        this.date = checkNotNull(date);
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);

        markeds = markedDays.get(generateKey(year, month));
        if (markeds == null)
            markeds = Collections.emptyList();

        calculate();
        notifyDataSetChanged();
    }

    private int generateKey(int year, int month) {
        return year * 100 + month;
    }

    private void calculate() {
        preCounts = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        counts = WEEK_NAME.length + preCounts + calendar.getActualMaximum(Calendar.DATE);
    }

    public String getTitleStr() {
        return DATE_TITLE_FORMAT.format(date);
    }

    @Override
    public int getCount() {
        return counts;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder(convertView = Views.inflate(parent, R.layout.i_calendar));
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (position >= WEEK_NAME.length + preCounts) {
            holder.clickable().setText("" + (position - (WEEK_NAME.length + preCounts) + 1));
        } else {
            holder.unclickable().setText(position < WEEK_NAME.length ? WEEK_NAME[position] : "");
        }

        holder.tvContent.setSelected(markeds.contains(position - WEEK_NAME.length - preCounts + 1));
        return convertView;
    }

    public boolean lastMonth() {
        updateDate(DateUtil.addMonth(date, -1));
        return true;
    }

    public boolean nextMonth() {
        updateDate(DateUtil.addMonth(date, 1));
        return !(currentYear == year && currentMonth == month);
    }

    private static class ViewHolder {
        private TextView tvContent;

        public ViewHolder(View parent) {
            parent.setTag(this);
            tvContent = Views.find(parent, R.id.tv_content);
//            tvContent.setOnClickListener(v -> {
//                System.out.println(tvContent.getText().toString());
//            });
        }

        private ViewHolder unclickable() {
            this.tvContent.setEnabled(false);
            return this;
        }

        public ViewHolder clickable() {
            this.tvContent.setEnabled(true);
            return this;
        }

        public ViewHolder setText(String str) {
            this.tvContent.setText(str);
            return this;
        }
    }
}
