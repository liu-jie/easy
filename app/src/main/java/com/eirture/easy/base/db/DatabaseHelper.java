package com.eirture.easy.base.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.eirture.easy.R;
import com.eirture.easy.main.model.Journal;
import com.eirture.easy.main.model.NotebookDB;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

/**
 * Created by Jie on 2016-07-14.
 */

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    public static final String DATABASE_NAME = "journal.db";

    private static final int DATABASE_VERSION = 2;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION, R.raw.ormlite_config);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, Journal.class);
            TableUtils.createTable(connectionSource, NotebookDB.class);
        } catch (SQLException e) {
            Log.e(DatabaseHelper.class.getName(), "Unable to create databases", e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqliteDatabase, ConnectionSource connectionSource, int oldVer, int newVer) {
        try {
            TableUtils.dropTable(connectionSource, Journal.class, true);
            TableUtils.dropTable(connectionSource, NotebookDB.class, true);
            onCreate(sqliteDatabase, connectionSource);
        } catch (SQLException e) {
            Log.e(DatabaseHelper.class.getName(), "Unable to upgrade database from version " + oldVer + " to new "
                    + newVer, e);
        }
    }
}
