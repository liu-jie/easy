package com.eirture.easy.main.view;

import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.eirture.easy.R;
import com.eirture.easy.base.bus.Result;
import com.eirture.easy.base.views.BusActivity;
import com.eirture.easy.calendar.view.CalendarF_;
import com.eirture.easy.main.NotebookP;
import com.eirture.easy.main.event.GetNotebookE;
import com.eirture.easy.main.model.Notebook;
import com.eirture.easy.mine.view.MineF;
import com.eirture.easy.mine.view.MineF_;
import com.jakewharton.rxbinding.support.design.widget.RxBottomNavigationView;
import com.jakewharton.rxbinding.view.RxView;
import com.squareup.otto.Subscribe;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

@EActivity(R.layout.a_main)
public class MainA extends BusActivity {

    @ViewById(R.id.navigation)
    BottomNavigationView navigationView;
    @ViewById(R.id.swipe)
    SwipeRefreshLayout swipeRefreshLayout;
    @ViewById
    Toolbar toolbar;

    @ViewById(R.id.btn_left)
    TextView tvTitle;

    @Bean
    NotebookP notebookP;

    FragmentManager fm;
    MainFragment currentF;
    List<MainFragment> fragments;

    private int notebookId;
    private Notebook notebook;

    @AfterInject
    void init() {
        fm = getSupportFragmentManager();
        fragments = new ArrayList<>();
        fragments.add(JournalF_.builder().build());
        fragments.add(CalendarF_.builder().build());
        fragments.add(MineF_.builder().build());
    }

    @AfterViews
    void initViews() {
        setupToolbar();
        initNavigationView();
        swipeRefreshLayout.setOnRefreshListener(() -> refreshNotebook());
        changePage(0);

        changeNotebook(Notebook.DEFAULT_NOTEBOOK_ID);

        RxView.clicks(tvTitle)
                .subscribe(aVoid -> {
                    Toast.makeText(this, "show ", Toast.LENGTH_SHORT).show();
                });
    }

    private void changeNotebook(int notebookId) {
        this.notebookId = notebookId;
        refreshNotebook();
    }

    private void refreshNotebook() {
        notebookP.getNotebookById(notebookId);
    }

    private Result<Notebook> getNotebookResult = Result.<Notebook>create()
            .prelude(() -> swipeRefreshLayout.setRefreshing(true))
            .successFunction(action -> refreshNotebook(action))
            .errorFunction(action -> Toast.makeText(this, action.message, Toast.LENGTH_SHORT).show())
            .finality(() -> swipeRefreshLayout.setRefreshing(false));

    @Subscribe
    public void getNotebook(GetNotebookE e) {
        getNotebookResult.result(e);
    }


    private void refreshNotebook(Notebook notebook) {
        this.notebook = checkNotNull(notebook);
        if (currentF != null) {
            currentF.updateNotebook(this.notebook);
        }
    }

    private void updateTitleAndRefresh(boolean agree) {
        tvTitle.setVisibility(agree ? View.VISIBLE : View.GONE);
        swipeRefreshLayout.setEnabled(agree);
    }


    private void initNavigationView() {
        RxBottomNavigationView.itemSelections(navigationView)
                .subscribe(menuItem -> {
                    switch (menuItem.getItemId()) {
                        case R.id.action_list:
                            changePage(0);
                            break;
                        case R.id.action_calendar:
                            changePage(1);
                            break;
                        case R.id.action_mine:
                            changePage(2);
                            break;
                    }
                });
    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
    }

    private void changePage(int index) {
        MainFragment f = fragments.get(index);
        if (currentF == f)
            return;
        updateTitleAndRefresh(!(f instanceof MineF));

        currentF = f;
        currentF.updateNotebook(notebook);
        fm.beginTransaction()
                .replace(R.id.container, currentF)
                .commit();
    }


}
