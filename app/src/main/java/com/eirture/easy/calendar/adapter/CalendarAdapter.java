package com.eirture.easy.calendar.adapter;

import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.eirture.easy.R;
import com.eirture.rxcommon.dates.DateUtil;
import com.eirture.rxcommon.utils.Views;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by eirture on 17-1-9.
 */

public class CalendarAdapter extends BaseAdapter {
    static final String[] WEEK_NAME = {"日", "一", "二", "三", "四", "五", "六"};
    static final SimpleDateFormat DATE_TITLE_FORMAT = new SimpleDateFormat("yyyy年MM月");

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

        System.out.println("today: " + today);

        updateDate(new Date());
    }

    public void updateDate(Date date) {
        this.date = checkNotNull(date);
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);

        calculate();
        notifyDataSetChanged();
    }

    private void calculate() {
        preCounts = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        counts = WEEK_NAME.length + preCounts + calendar.getActualMaximum(Calendar.DATE);
        System.out.println("counts: " + (counts - WEEK_NAME.length - preCounts));
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
            convertView = Views.inflate(parent, R.layout.l_calendar_item);
            holder = new ViewHolder(convertView);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (position < WEEK_NAME.length) {
            holder.unclickable().setText(WEEK_NAME[position]);
        } else if (position >= WEEK_NAME.length + preCounts) {
            holder.clickable().setText("" + (position - (WEEK_NAME.length + preCounts) + 1));
        } else {
            holder.unclickable().setText("");
        }

        holder.tvContent.setSelected(year == currentYear && month == currentMonth && position - WEEK_NAME.length - preCounts == today - 1);
        return convertView;
    }

    public void lastMonth() {
        updateDate(DateUtil.addMonth(date, -1));
    }

    public boolean nextMonth() {
        updateDate(DateUtil.addMonth(date, 1));
        return calendar.get(Calendar.YEAR) <= currentYear && calendar.get(Calendar.MONTH) <= currentMonth;
    }

    private static class ViewHolder {
        private TextView tvContent;
        private boolean clickable;

        public ViewHolder(View parent) {
            parent.setTag(this);

            tvContent = Views.find(parent, R.id.tv_content);
            tvContent.setOnClickListener(v -> {
                if (clickable) {
                    tvContent.setSelected(!tvContent.isSelected());
                }
            });
        }

        private ViewHolder unclickable() {
            this.clickable = false;
            tvContent.setBackground(null);
            return this;
        }

        public ViewHolder clickable() {
            this.clickable = true;
            tvContent.setBackground(ContextCompat.getDrawable(tvContent.getContext(), R.drawable.sel_item_calendar));
            return this;
        }

        public ViewHolder setText(String str) {
            this.tvContent.setText(str);
            return this;
        }
    }
}
