package com.alvarado.edwin.pruebaandroid.Modelo.DB;

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

import java.io.IOException;
import java.sql.SQLException;

import static com.j256.ormlite.android.apptools.OrmLiteConfigUtil.writeConfigFile;

/**
 * Created by edwinalvarado on 02/06/17.
 */

public class DataBaseConfigUtil {

    public static final Class<?>[] classes = new Class[]{
            Farmer.class,Category.class,Item.class,LinkItem.class,Location1.class,
            User.class,Task.class,TaskType.class,UserTasks.class,UserType.class,UserTaskTypes.class
    };

    public static void main(String[] args) throws SQLException, IOException {
        writeConfigFile("ormlite_config.txt",classes);
    }


}
