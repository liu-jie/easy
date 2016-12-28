package com.eirture.easy.edit.data;

import android.support.annotation.NonNull;

import com.eirture.easy.main.model.Journal;

import org.androidannotations.annotations.EBean;

import rx.Observable;

/**
 * Created by eirture on 16-12-23.
 */
@EBean(scope = EBean.Scope.Singleton)
public class EditR {


    public void saveJournal(@NonNull Journal journal) {
    }

    public Observable<Journal> queryJournal(int id) {
//        return Observable.<Journal>create(subscriber -> {
//            try {
//                Journal journal = journalDao.queryForId(id);
//                subscriber.onNext(journal);
//            } catch (SQLException e) {
//                subscriber.onError(e);
//            }
//        })
//                .subscribeOn(Schedulers.io());
        return null;
    }
}
