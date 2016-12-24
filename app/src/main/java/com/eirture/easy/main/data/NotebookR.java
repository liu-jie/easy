package com.eirture.easy.main.data;

import com.eirture.easy.base.db.DatabaseHelper;
import com.eirture.easy.main.model.NotebookDB;
import com.j256.ormlite.dao.Dao;

import org.androidannotations.annotations.EBean;
import org.androidannotations.ormlite.annotations.OrmLiteDao;

import java.sql.SQLException;

/**
 * Created by eirture on 16-12-23.
 */
@EBean(scope = EBean.Scope.Singleton)
public class NotebookR {

    @OrmLiteDao(helper = DatabaseHelper.class)
    Dao<NotebookDB, Integer> nbDao;

    public void updateNotebook(NotebookDB dbNotebook) {
        try {
            nbDao.createOrUpdate(dbNotebook);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
