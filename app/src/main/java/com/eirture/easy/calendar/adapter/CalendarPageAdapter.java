package com.eirture.easy.calendar.adapter;

import android.view.View;
import android.view.ViewGroup;

import com.eirture.easy.base.widget.RecyclingPagerAdapter;

/**
 * Created by eirture on 17-1-9.
 */

public class CalendarPageAdapter extends RecyclingPagerAdapter {
    private static final int PAGE_MAX_COUNT = 24;

    @Override
    public int getCount() {
        return PAGE_MAX_COUNT;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup container) {
        return null;
    }



}
