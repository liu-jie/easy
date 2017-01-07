package com.eirture.easy.edit.data;

import android.support.annotation.NonNull;

import com.eirture.easy.base.bus.BusMessage;
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
            e.printStackTrace();
        }
    }

    public Observable<Journal> queryJournal(int id) {
        return Observable.<Journal>create(
                subscriber -> {
                    try {
                        Journal journal = journalDao.queryForId(id);
                        if (journal == null) {
                            subscriber.onError(new Throwable("journal is not exist which id == " + id));
                        } else {
                            subscriber.onNext(journal);
                        }
                    } catch (SQLException e) {
                        subscriber.onError(e);
                    }
                })
                .subscribeOn(Schedulers.io());
    }

    public Observable<BusMessage> delete(int journalId) {
        return Observable.<BusMessage>create(
                subscriber -> {
                    try {
                        int code = journalDao.deleteById(journalId);
                        if (1 == code)
                            subscriber.onNext(new BusMessage(0, ""));
                        else
                            subscriber.onError(new Exception("delete journal filed"));
                    } catch (SQLException e) {
                        subscriber.onError(e);
                    }
                    subscriber.onCompleted();
                })
                .subscribeOn(Schedulers.io());
    }
}
