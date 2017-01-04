package com.eirture.easy.edit;

import com.eirture.easy.base.ObserverImpl;
import com.eirture.easy.base.bus.SingleBus;
import com.eirture.easy.edit.data.EditR;
import com.eirture.easy.edit.event.QueryJournalE;
import com.eirture.easy.main.model.Journal;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.androidannotations.api.BackgroundExecutor;

import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by eirture on 16-12-23.
 */
@EBean(scope = EBean.Scope.Singleton)
public class EditP {
    private static final String AUTO_SAVE_TASK_ID = "auto_save";
    private static final int AUTO_SAVE_DELAY_MILLIS = 2000;

    @Bean
    EditR editR;
    @Bean
    SingleBus bus;


    public void updateJournal(Journal journal) {
        if (journal == null)
            return;

        BackgroundExecutor.cancelAll(AUTO_SAVE_TASK_ID, true);
        autoSave(journal);
    }

    @Background(delay = AUTO_SAVE_DELAY_MILLIS, id = AUTO_SAVE_TASK_ID)
    protected void autoSave(Journal journal) {
        System.out.println("save journal: " + journal.getContent());
        editR.saveJournal(journal);
    }

    public void readJournal(int journalId) {
        editR.queryJournal(journalId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ObserverImpl<>(bus, QueryJournalE.class));
    }

    public void deleteJournal(int journalId) {
        
    }
}
