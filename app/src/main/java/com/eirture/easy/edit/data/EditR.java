package com.eirture.easy.edit.data;

import android.support.annotation.NonNull;
import android.util.Log;

import com.avos.avoscloud.AVCloudQueryResult;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.eirture.easy.base.bus.BusMessage;
import com.eirture.easy.base.db.DatabaseHelper;
import com.eirture.easy.main.data.ConfigPrefs_;
import com.eirture.easy.main.model.Journal;
import com.eirture.rxcommon.BuildConfig;
import com.google.common.base.Strings;
import com.j256.ormlite.dao.Dao;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.sharedpreferences.Pref;
import org.androidannotations.api.BackgroundExecutor;
import org.androidannotations.ormlite.annotations.OrmLiteDao;

import java.sql.SQLException;
import java.util.regex.Pattern;

import rx.Observable;
import rx.schedulers.Schedulers;

/**
 * Created by eirture on 16-12-23.
 */
@EBean(scope = EBean.Scope.Singleton)
public class EditR {

    private static final String SYNC_UPLOAD_TASK_ID = "sync_upload";
    private static final String SYNC_DOWNLOAD_TAST_ID = "sync_download";

    @OrmLiteDao(helper = DatabaseHelper.class)
    Dao<Journal, Integer> journalDao;
    @Pref
    ConfigPrefs_ configPrefs;

    private final Pattern p = Pattern.compile("!\\[[^]]*]\\(([^)]+)\\)");

    public void saveJournal(@NonNull Journal journal) {
        if (Strings.isNullOrEmpty(journal.getContent())
                && journal.id == 0) {
            return;
        }

        if (configPrefs.openSync().getOr(false)) {
            AVQuery<AVObject> journalQuery = new AVQuery<>(Journal.CLASS_NAME);
            AVObject o = null;
            boolean existed = false;
            try {
                journalQuery.get(journal.getObjectId());
                o = Journal.refreshAVObjectFromJournal(journal);
            } catch (AVException e) {
                e.printStackTrace();
                o = Journal.createObject(journal);
            }
            try {
                o.save();
            } catch (AVException e) {
                e.printStackTrace();
            }
            journal.objectId = o.getObjectId();


        }

        try {

            journalDao.createOrUpdate(journal);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private static final String queryJournalCQL = "select * from " + Journal.CLASS_NAME
            + " where " + Journal.KEY_USER_ID + " = ?";

    public void syncDownload() {
        BackgroundExecutor.cancelAll(SYNC_DOWNLOAD_TAST_ID, true);
        backgroundDownload();
    }

    @Background(id = SYNC_DOWNLOAD_TAST_ID)
    protected void backgroundDownload() {
        AVUser currentUser = AVUser.getCurrentUser();
        if (currentUser == null) {
            if (BuildConfig.DEBUG) Log.d("EditR", "current user is null, sync failed.");
            return;
        }
        Observable.<AVObject>create(
                subscriber -> {
                    AVCloudQueryResult result = null;
                    try {
                        result = AVQuery.doCloudQuery(queryJournalCQL, currentUser.getObjectId());
                    } catch (Exception e) {
                        subscriber.onError(e);
                    }

                    assert result != null;
                    result.getResults().forEach(subscriber::onNext);
                    subscriber.onCompleted();
                })
                .map(Journal::createFromAVObject)
                .doOnNext(journal -> {
                    try {
                        journalDao.createOrUpdate(journal);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                })
                .subscribe();
    }


    public void syncUpload() {
        BackgroundExecutor.cancelAll(SYNC_UPLOAD_TASK_ID, true);
        backgroundUpload();
    }

    @Background(id = SYNC_UPLOAD_TASK_ID)
    protected void backgroundUpload() {
        AVUser currentUser = AVUser.getCurrentUser();
        if (currentUser == null)
            return;

        try {
            AVQuery journalQuery = new AVQuery(Journal.CLASS_NAME);
            journalDao.queryForAll().forEach(journal -> {
                AVObject o = null;
                try {
                    if (!Strings.isNullOrEmpty(journal.getObjectId())) {
                        o = journalQuery.get(journal.getObjectId());
                        Journal.refreshAVObjectFromJournal(journal).save();
                    }
                } catch (AVException e) {
                    e.printStackTrace();
                    try {
                        Journal.createObject(journal).save();
                    } catch (AVException e1) {
                        e1.printStackTrace();
                    }
                }

            });
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Observable<Journal> queryJournal(int id) {
        return Observable.<Journal>create(
                subscriber -> {
                    if (configPrefs.openSync().getOr(false)) {
                        syncDownload();
                    }
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
