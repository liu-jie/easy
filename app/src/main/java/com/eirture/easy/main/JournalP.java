package com.eirture.easy.main;

import com.eirture.easy.JApplication;
import com.eirture.easy.base.ObserverImpl;
import com.eirture.easy.base.bus.SingleBus;
import com.eirture.easy.main.event.LoadJournalE;
import com.eirture.easy.main.model.Journal;
import com.simperium.client.Bucket;
import com.simperium.client.BucketObjectMissingException;

import org.androidannotations.annotations.App;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by eirture on 16-12-28.
 */
@EBean(scope = EBean.Scope.Singleton)
public class JournalP {
    @App
    JApplication application;
    @Bean
    SingleBus bus;
    Bucket<Journal> journalBucket;

    public void loadJournal(String jID) {
        Observable.<Journal>create(subscriber -> {
            Journal journal = null;
            if (journalBucket == null) {
                journalBucket = application.journalBucket();
            }
            try {
                journal = journalBucket.get(jID);
            } catch (BucketObjectMissingException e) {
                subscriber.onError(e);
            }
            subscriber.onNext(journal);
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ObserverImpl<>(bus, LoadJournalE.class).prelude());

    }
}
