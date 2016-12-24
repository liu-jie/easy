package com.eirture.easy.base.db;

import com.eirture.easy.main.model.Journal;
import com.eirture.easy.main.model.NotebookDB;
import com.j256.ormlite.android.apptools.OrmLiteConfigUtil;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

/**
 * DatabaseConfigUtl writes a configuration file to avoid using annotation processing in runtime which is very slow
 * under Android. This gains a noticeable performance improvement.
 * <p>
 * The configuration file is written to /res/raw/ by default. More info at: http://ormlite.com/docs/table-config
 */
public class DatabaseConfigUtil extends OrmLiteConfigUtil {

    private static final Class<?>[] classes = new Class[]{
            Journal.class,
            NotebookDB.class
    };

    public static void main(String[] args) throws SQLException, IOException {
        writeConfigFile(new File("./app/src/main/res/raw", "ormlite_config.txt"), classes);
    }
}
