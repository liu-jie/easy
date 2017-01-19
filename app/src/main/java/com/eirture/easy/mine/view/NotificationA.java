package com.eirture.easy.mine.view;

import android.app.TimePickerDialog;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.eirture.easy.R;
import com.eirture.easy.base.Constant;
import com.eirture.easy.base.views.BaseActivity;
import com.eirture.easy.mine.data.AlarmPrefs_;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;

/**
 * Created by eirture on 17-1-18.
 */
@EActivity(R.layout.a_notification)
public class NotificationA extends BaseActivity {
    @ViewById(R.id.toolbar)
    Toolbar toolbar;

    @ViewById(R.id.tv_alarm_time)
    TextView tvAlarmTime;

    @ViewById(R.id.switch_alarm)
    SwitchCompat switchAlarm;

    @ViewById(R.id.item_alarm_time)
    View itemAlarmTime;

    @Pref
    AlarmPrefs_ alarmPrefs;

    TimePickerDialog timePickerDialog;

    @AfterViews
    protected void initViews() {
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> back(v));

        int times = alarmPrefs.alarmTime().getOr(Constant.DEFAULT_ALARM_TIME);
        int hour = times / 100, minutes = times % 100;
        refreshAlarmTime(hour, minutes);
        boolean isOpen = alarmPrefs.isOpen().get();
        switchAlarm.setChecked(isOpen);
        refreshAlarmActive(isOpen);

        timePickerDialog = new TimePickerDialog(this,
                (view, hourOfDay, minute) -> {
                    alarmPrefs.edit().alarmTime().put(hourOfDay * 100 + minute).apply();
                    refreshAlarmTime(hourOfDay, minute);
                },
                hour, minutes, true);

        switchAlarm.setOnCheckedChangeListener((buttonView, isChecked) -> refreshAlarmActive(isChecked));
        itemAlarmTime.setOnClickListener(v -> timePickerDialog.show());

    }

    private void refreshAlarmActive(boolean isActive) {
        itemAlarmTime.setEnabled(isActive);
        tvAlarmTime.setTextColor(ContextCompat.getColor(this, isActive ? R.color.colorPrimary : R.color.pressGray));
        alarmPrefs.edit().isOpen().put(isActive).apply();
    }

    private void refreshAlarmTime(int hourOfDay, int minute) {
        tvAlarmTime.setText(String.format("每天 %02d:%02d", hourOfDay, minute));
    }
}
