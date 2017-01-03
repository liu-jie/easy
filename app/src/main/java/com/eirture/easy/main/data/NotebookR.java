package com.eirture.easy.main.data;

import com.eirture.easy.base.db.DatabaseHelper;
import com.eirture.easy.main.model.Journal;
import com.eirture.easy.main.model.Notebook;
import com.eirture.easy.main.model.NotebookDB;
import com.j256.ormlite.dao.Dao;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.EBean;
import org.androidannotations.ormlite.annotations.OrmLiteDao;

import java.sql.SQLException;
import java.util.List;

import rx.Observable;
import rx.schedulers.Schedulers;

/**
 * Created by eirture on 16-12-23.
 */
@EBean(scope = EBean.Scope.Singleton)
public class NotebookR {

    @OrmLiteDao(helper = DatabaseHelper.class)
    Dao<NotebookDB, Integer> nbDao;

    @OrmLiteDao(helper = DatabaseHelper.class)
    Dao<Journal, Integer> journalDao;

    @AfterInject
    protected void init() {
        try {
            nbDao.createIfNotExists(NotebookDB.generateDefaultNotebook());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateNotebook(NotebookDB dbNotebook) {
        try {
            nbDao.createOrUpdate(dbNotebook);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Observable<List<Journal>> getJournals(int notebookId) {
        return Observable.<List<Journal>>create(
                subscriber -> {
                    try {
                        subscriber.onNext(journalDao.queryForEq(Journal.FIELD_NOTE_ID, notebookId));
                    } catch (SQLException e) {
                        subscriber.onError(e);
                    }
                })
                .subscribeOn(Schedulers.io());
    }

    public Observable<List<Notebook>> loadAllNotebook() {
        return Observable.<NotebookDB>create(
                subscriber -> {
                    try {
                        List<NotebookDB> notes = nbDao.queryForAll();
                        if (notes != null) {
                            for (NotebookDB note :
                                    notes) {
                                subscriber.onNext(note);
                            }
                        }
                    } catch (SQLException e) {
                        subscriber.onError(e);
                    }
                    subscriber.onCompleted();
                })
                .flatMap(notebookDB -> calculateNotebookData(notebookDB))
                .toList()
                .subscribeOn(Schedulers.io());
    }

    private Observable<Notebook> calculateNotebookData(NotebookDB notebookDB) {
        return Observable.create(subscriber -> {
            try {
                subscriber.onNext(Notebook.createFromNotebookDB(
                        notebookDB,
                        notebookDB.id == Notebook.DEFAULT_NOTEBOOK_ID ?
                                journalDao.queryForAll() :
                                journalDao.queryForEq(Journal.FIELD_NOTE_ID, notebookDB.id)));
            } catch (SQLException e) {
                subscriber.onError(e);
            }
        });

    }

    public Observable<Notebook> getNotebook(int id) {
        return Observable.<NotebookDB>create(
                subscriber -> {
                    try {
                        NotebookDB nb = nbDao.queryForId(id);
                        if (id == Notebook.DEFAULT_NOTEBOOK_ID && nb == null) {
                            nbDao.createOrUpdate(nb = NotebookDB.generateDefaultNotebook());
                        }
                        subscriber.onNext(nb);
                    } catch (SQLException e) {
                        subscriber.onError(e);
                    }
                    subscriber.onCompleted();
                })
                .flatMap(notebookDB -> calculateNotebookData(notebookDB))
                .subscribeOn(Schedulers.io());
    }

}
