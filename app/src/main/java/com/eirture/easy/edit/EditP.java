package com.eirture.easy.edit;

import com.eirture.easy.base.bus.SingleBus;
import com.eirture.easy.edit.data.EditR;
import com.eirture.easy.main.model.Journal;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by eirture on 16-12-23.
 */
@EBean(scope = EBean.Scope.Singleton)
public class EditP {

    @Bean
    EditR editR;
    @Bean
    SingleBus bus;

    public void createJournal(String content, int noteId) {
        Journal journal = Journal.newInstance(content, noteId);
        editR.saveJournal(journal);
    }

    public void updateJournal(Journal journal) {
        editR.saveJournal(journal);
    }

    public void readJournal(int journalId) {
        editR.queryJournal(journalId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }

    public void deleteJournal(int journalId) {

    }
}
