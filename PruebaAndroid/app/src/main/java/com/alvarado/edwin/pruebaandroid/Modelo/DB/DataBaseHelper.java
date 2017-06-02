package com.alvarado.edwin.pruebaandroid.Modelo.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.alvarado.edwin.pruebaandroid.Modelo.POJO.Category;
import com.alvarado.edwin.pruebaandroid.Modelo.POJO.Farmer;
import com.alvarado.edwin.pruebaandroid.Modelo.POJO.Item;
import com.alvarado.edwin.pruebaandroid.Modelo.POJO.LinkItem;
import com.alvarado.edwin.pruebaandroid.Modelo.POJO.Location1;
import com.alvarado.edwin.pruebaandroid.Modelo.POJO.Task;
import com.alvarado.edwin.pruebaandroid.Modelo.POJO.TaskType;
import com.alvarado.edwin.pruebaandroid.Modelo.POJO.User;
import com.alvarado.edwin.pruebaandroid.Modelo.POJO.UserTaskTypes;
import com.alvarado.edwin.pruebaandroid.Modelo.POJO.UserTasks;
import com.alvarado.edwin.pruebaandroid.Modelo.POJO.UserType;
import com.alvarado.edwin.pruebaandroid.R;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

/**
 * Created by edwinalvarado on 02/06/17.
 */

public class DataBaseHelper extends OrmLiteSqliteOpenHelper {



    private static final String DATABASE_NAME = "pruebaAndroid.db";
    private static final int DATABASE_VERSION = 1;

    private Dao<Task, Integer> taskDao;
    private Dao<TaskType, Integer> taskTypeDao;
    private Dao<User, Integer> userDao;
    private Dao<UserTasks, Integer> userTasksDao;
    private Dao<UserType, Integer> userTypeDao;
    private Dao<UserTaskTypes, Integer> userTaskTypesDao;

    //======================WEBSERVICE==================
    private Dao<Location1, Integer> location1Dao;
    private Dao<Farmer, Integer> farmerDao;
    private Dao<Category, Integer> categoryDao;
    private Dao<Item, Integer> itemDao;
    private Dao<LinkItem, Integer> linkItemDao;



    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION, R.raw.ormlite_config);
    }


    @Override
    public void onCreate(SQLiteDatabase sqliteDatabase, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, Task.class);
            TableUtils.createTable(connectionSource, TaskType.class);
            TableUtils.createTable(connectionSource, User.class);
            TableUtils.createTable(connectionSource, UserType.class);
            TableUtils.createTable(connectionSource, UserTaskTypes.class);
            TableUtils.createTable(connectionSource, UserTasks.class);

            TableUtils.createTable(connectionSource, Location1.class);
            TableUtils.createTable(connectionSource, Farmer.class);
            TableUtils.createTable(connectionSource, Category.class);
            TableUtils.createTable(connectionSource, Item.class);
            TableUtils.createTable(connectionSource, LinkItem.class);





        } catch (SQLException e) {
            Log.e(DataBaseHelper.class.getName(), "Unable to create datbases", e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqliteDatabase, ConnectionSource connectionSource, int oldVer, int newVer) {
        try {
            TableUtils.dropTable(connectionSource, Task.class, true);
            TableUtils.dropTable(connectionSource, TaskType.class, true);
            TableUtils.dropTable(connectionSource, User.class, true);
            TableUtils.dropTable(connectionSource, UserTasks.class, true);
            TableUtils.dropTable(connectionSource, UserType.class, true);
            TableUtils.dropTable(connectionSource, UserTaskTypes.class, true);

            TableUtils.dropTable(connectionSource, Location1.class,true);
            TableUtils.dropTable(connectionSource, Farmer.class,true);
            TableUtils.dropTable(connectionSource, Category.class,true);
            TableUtils.dropTable(connectionSource, Item.class,true);
            TableUtils.dropTable(connectionSource, LinkItem.class,true);

            onCreate(sqliteDatabase, connectionSource);
        } catch (SQLException e) {
            Log.e(DataBaseHelper.class.getName(), "Unable to upgrade database from version " + oldVer + " to new "
                    + newVer, e);
        }
    }


    public Dao<Task, Integer> getTaskDao() throws SQLException {
        if (taskDao == null) {
            taskDao = getDao(Task.class);
        }
        return taskDao;
    }

    public Dao<TaskType, Integer> getTaskTypeDao() throws SQLException {
        if (taskTypeDao == null) {
            taskTypeDao = getDao(TaskType.class);
        }
        return taskTypeDao;
    }

    public Dao<User, Integer> getUserDao() throws SQLException {
        if (userDao == null) {
            userDao = getDao(User.class);
        }
        return userDao;
    }

    public Dao<UserTasks, Integer> getUserTasksDao() throws SQLException {
        if (userTasksDao == null) {
            userTasksDao = getDao(UserTasks.class);
        }
        return userTasksDao;
    }

    public Dao<UserType, Integer> getUserTypeDao() throws SQLException {
        if (userTypeDao== null) {
            userTypeDao = getDao(UserType.class);
        }
        return userTypeDao;
    }
    public Dao<UserTaskTypes, Integer> getUserTaskTypesDao() throws SQLException {
        if (userTaskTypesDao== null) {
            userTaskTypesDao = getDao(UserTaskTypes.class);
        }
        return userTaskTypesDao;
    }

    public Dao<Location1, Integer> getLocation1Dao() throws SQLException {
        if (location1Dao == null) {
            location1Dao = getDao(Location1.class);
        }
        return location1Dao;
    }

    public Dao<Farmer, Integer> getFarmerDao() throws SQLException {
        if (farmerDao == null) {
            farmerDao = getDao(Farmer.class);
        }
        return farmerDao;
    }

    public Dao<Category, Integer> getCategoryDao() throws SQLException {
        if (categoryDao == null) {
            categoryDao = getDao(Category.class);
        }
        return categoryDao;
    }

    public Dao<Item, Integer> getItemDao() throws SQLException {
        if (itemDao== null) {
            itemDao = getDao(Item.class);
        }
        return itemDao;
    }
    public Dao<LinkItem, Integer> getLinkItemDao() throws SQLException {
        if (linkItemDao== null) {
            linkItemDao = getDao(LinkItem.class);
        }
        return linkItemDao;
    }
}
