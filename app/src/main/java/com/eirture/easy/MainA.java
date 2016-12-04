package com.eirture.easy;

import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;

import com.eirture.easy.base.views.BaseActivity;
import com.eirture.easy.main.view.JournalListF_;
import com.eirture.easy.main.view.MainFragment;
import com.jakewharton.rxbinding.support.design.widget.RxBottomNavigationView;

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

    FragmentManager fm;
    MainFragment currentF;
    List<MainFragment> fragments;

    @AfterInject
    void init() {
        fm = getSupportFragmentManager();
        fragments = new ArrayList<>();
        fragments.add(JournalListF_.builder().build());
    }

    @AfterViews
    void initViews() {
        changePage(0);
        RxBottomNavigationView.itemSelections(navigationView)
                .subscribe(menuItem -> changePage(0));
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
