package com.eirture.easy.calendar.view;

import android.widget.GridView;
import android.widget.TextView;

import com.eirture.easy.R;
import com.eirture.easy.calendar.adapter.CalendarAdapter;
import com.eirture.easy.main.view.MainFragment;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

/**
 * Created by eirture on 17-1-9.
 */
@EFragment(R.layout.f_calendar)
public class CalendarF extends MainFragment {

    @ViewById(R.id.tv_day_count)
    TextView tvDayCount;
    @ViewById(R.id.tv_title_date)
    TextView tvTitleDate;
    @ViewById(R.id.gv_calendar)
    GridView gvCalendar;

    CalendarAdapter mAdapter;

    @AfterViews
    protected void initViews() {
        if (mAdapter == null) {
            mAdapter = new CalendarAdapter();
        }
        gvCalendar.setAdapter(mAdapter);
        tvTitleDate.setText(mAdapter.getTitleStr());
    }


    @Click(R.id.btn_last)
    protected void clickLastMonth() {

    }

    @Click(R.id.btn_next)
    protected void clickNextMonth() {

    }

    @Override
    protected void refreshNotebook() {

    }
}