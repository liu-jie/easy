package com.eirture.easy.main.view;

import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.widget.TextView;
import android.widget.Toast;

import com.eirture.easy.R;
import com.eirture.easy.base.views.BaseActivity;
import com.jakewharton.rxbinding.support.design.widget.RxBottomNavigationView;
import com.jakewharton.rxbinding.view.RxView;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

@EActivity(R.layout.a_main)
public class MainA extends BaseActivity {


    @ViewById(R.id.navigation)
    BottomNavigationView navigationView;

    @ViewById(R.id.btn_left)
    TextView tvTitle;

    FragmentManager fm;
    MainFragment currentF;
    List<MainFragment> fragments;

    @AfterInject
    void init() {
        fm = getSupportFragmentManager();
        fragments = new ArrayList<>();
        fragments.add(JournalF_.builder().build());
    }

    @AfterViews
    void initViews() {
        changePage(0);
        RxBottomNavigationView.itemSelections(navigationView)
                .subscribe(menuItem -> changePage(0));
        RxView.clicks(tvTitle)
                .subscribe(aVoid -> {
                    Toast.makeText(this, "show ", Toast.LENGTH_SHORT).show();
                });
    }

    private void changePage(int index) {
        System.out.println("change tab index: " + index);
        if (currentF == fragments.get(0))
            return;

        currentF = fragments.get(0);
        fm.beginTransaction()
                .replace(R.id.container, currentF)
                .commit();
    }

}
