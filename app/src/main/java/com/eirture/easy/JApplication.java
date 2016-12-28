package com.eirture.easy;

import android.app.Application;

import com.eirture.easy.main.model.Journal;
import com.simperium.Simperium;
import com.simperium.client.Bucket;
import com.simperium.client.BucketNameInvalid;

import org.androidannotations.annotations.EApplication;

/**
 * Created by eirture on 16-12-28.
 */
@EApplication
public class JApplication extends Application {

    private Simperium mSimperium;
    private Bucket<Journal> mJournalBucket;

    @Override
    public void onCreate() {
        super.onCreate();
        initSimperium();
    }

    private void initSimperium() {
        mSimperium = Simperium.newClient(
                BuildConfig.SIMPERIUM_APP,
                BuildConfig.SIMPERIUM_KEY,
                this
        );
        try {
            mJournalBucket = mSimperium.bucket(new Journal.Schema());
        } catch (BucketNameInvalid e) {
            throw new RuntimeException("Could not create bucket", e);
        }
    }

    public Simperium getSimperium() {
        return mSimperium;
    }

    public Bucket<Journal> journalBucket() {
        return mJournalBucket;
    }

}
