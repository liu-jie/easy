package com.eirture.easy.calendar.view;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;

import com.eirture.easy.R;
import com.eirture.easy.calendar.adapter.CalendarAdapter;
import com.eirture.easy.main.view.MainFragment;
import com.jakewharton.rxbinding.view.RxView;

import org.androidannotations.annotations.AfterViews;
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
    @ViewById(R.id.btn_next)
    View btnNext;
    @ViewById(R.id.btn_last)
    View btnLast;

    CalendarAdapter mAdapter;

    @AfterViews
    protected void initViews() {
        if (mAdapter == null) {
            mAdapter = new CalendarAdapter();
        }
        gvCalendar.setAdapter(mAdapter);
        tvDayCount.setText(String.valueOf(21));

        initPageBtn();
    }

    private void initPageBtn() {
        RxView.clicks(btnNext)
                .doOnNext(aVoid -> {
                    btnNext.setActivated(mAdapter.nextMonth());
                    btnLast.setActivated(true);
                    clickPageBtn();
                }).subscribe();

        RxView.clicks(btnLast)
                .doOnNext(aVoid -> {
                    btnLast.setActivated(mAdapter.lastMonth());
                    btnNext.setActivated(true);
                    clickPageBtn();
                }).subscribe();


        btnLast.setActivated(true);
        btnNext.setActivated(false);
        clickPageBtn();
    }

    private void clickPageBtn() {
        tvTitleDate.setText(mAdapter.getTitleStr());
        btnNext.setClickable(btnNext.isActivated());
        btnLast.setClickable(btnLast.isActivated());
    }

    @Override
    protected void refreshNotebook() {

    }
}
