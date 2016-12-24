package com.eirture.easy.edit.data;

import android.support.annotation.NonNull;

import com.eirture.easy.base.db.DatabaseHelper;
import com.eirture.easy.main.model.Journal;
import com.j256.ormlite.dao.Dao;

import org.androidannotations.annotations.EBean;
import org.androidannotations.ormlite.annotations.OrmLiteDao;

import java.sql.SQLException;

import rx.Observable;
import rx.schedulers.Schedulers;

/**
 * Created by eirture on 16-12-23.
 */
@EBean(scope = EBean.Scope.Singleton)
public class EditR {

    @OrmLiteDao(helper = DatabaseHelper.class)
    Dao<Journal, Integer> journalDao;

    public void saveJournal(@NonNull Journal journal) {
        try {
            journalDao.createOrUpdate(journal);
        } catch (SQLException e) {
//            e.printStackTrace();
            System.err.println(e.toString());
        }
    }

    public Observable<Journal> queryJournal(int id) {
        return Observable.<Journal>create(subscriber -> {
            try {
                Journal journal = journalDao.queryForId(id);
                subscriber.onNext(journal);
            } catch (SQLException e) {
                subscriber.onError(e);
            }
        })
                .subscribeOn(Schedulers.io());
    }
}
