package com.eirture.easy.main;

import com.eirture.easy.base.ObserverImpl;
import com.eirture.easy.base.bus.SingleBus;
import com.eirture.easy.edit.EditP;
import com.eirture.easy.main.data.NotebookR;
import com.eirture.easy.main.event.GetNotebookE;
import com.eirture.easy.main.event.LoadAllNotebookE;
import com.eirture.easy.main.event.LoadJournalsE;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by eirture on 16-12-31.
 */
@EBean(scope = EBean.Scope.Singleton)
public class NotebookP {

    @Bean
    NotebookR notebookR;
    @Bean
    EditP editP;
    @Bean
    SingleBus bus;

    public void getJournals(int id) {
        notebookR.getJournals(id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ObserverImpl<>(bus, LoadJournalsE.class));
    }

    public void loadNotebooks() {
        notebookR.loadAllNotebook()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ObserverImpl<>(bus, LoadAllNotebookE.class));
    }

    public void getNotebookById(int id) {
        notebookR.getNotebook(id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ObserverImpl<>(bus, GetNotebookE.class));
    }

    public void deleteJournal(int journalId, int position) {
        editP.deleteJournal(journalId, position);
    }

}
