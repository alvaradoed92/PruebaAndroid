package com.alvarado.edwin.pruebaandroid.Controlador;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.alvarado.edwin.pruebaandroid.Modelo.DB.DataBaseHelper;
import com.alvarado.edwin.pruebaandroid.Modelo.POJO.TaskType;
import com.alvarado.edwin.pruebaandroid.Modelo.POJO.User;
import com.alvarado.edwin.pruebaandroid.Modelo.POJO.UserTaskTypes;
import com.alvarado.edwin.pruebaandroid.Modelo.POJO.UserType;
import com.alvarado.edwin.pruebaandroid.R;
import com.alvarado.edwin.pruebaandroid.info.hoang8f.widget.FButton;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;

/**
 * Created by edwinalvarado on 01/06/17.
 */

public class InitialActivity extends AppCompatActivity {
   // Intent openMainActivity;

    public static String LOGIN_FRAGMENT_TAG="LogInTag";
    DataBaseHelper dataBaseHelper = null;

    SharedPreferences sharedpreferences;
    public static final String PAPREFERENCES = "PruebaAndroidPreferences" ;
    public static final String USERLOGINPR = "userLogIn";
    public static final String ACTIVECONNECTION = "activeConnection";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.initial_container);


        sharedpreferences = getSharedPreferences(PAPREFERENCES, MODE_PRIVATE);
        Integer activeConnection = sharedpreferences.getInt(ACTIVECONNECTION, 0);

        if(activeConnection!= 0)
        {
            startActivity(new Intent(InitialActivity.this, MainActivity.class)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP));
            finish();
        }
        else{

            initialValues();

            getSupportFragmentManager().beginTransaction().
                    replace(R.id.content, new LogInFragment(), LOGIN_FRAGMENT_TAG).
                    commit();

        }
    }


    private void initialValues()
    {
        User iniUser = new User();
        UserType iniUserType = new UserType();
        TaskType iniTaskType = new TaskType();
        UserTaskTypes iniUserTaskTypes = new UserTaskTypes();

       /* Tipo de tareas:
        1. Cobrar
        2. Envolver regalo
        3. Devolver mercancia
        4. Etcetera
        Anselmo Hernández, administrador
        Ruben Garcia, tecnico, tareas tipo 2
        Samantha Ruiz, tecnico, tareas tipo 1 y 2
        Luis Carvajal, tecnico, tareas tipo 1, 2 y 4*/

       try {

           final Dao<UserType, Integer> userTypeDao = getHelper().getUserTypeDao();
           //UserType
           iniUserType = new UserType(1, "Administrador");
           userTypeDao.create(iniUserType);
           iniUserType = new UserType(2, "Tecnico");
           userTypeDao.create(iniUserType);

           final Dao<TaskType, Integer> taskTypeDao = getHelper().getTaskTypeDao();
           iniTaskType = new TaskType(1,"Cobrar");
           taskTypeDao.create(iniTaskType);
           iniTaskType = new TaskType(2,"Envolver regalo");
           taskTypeDao.create(iniTaskType);
           iniTaskType = new TaskType(3,"Devolver mercancia");
           taskTypeDao.create(iniTaskType);
           iniTaskType = new TaskType(4,"Etcetera");
           taskTypeDao.create(iniTaskType);

           final Dao<User, Integer> userDao = getHelper().getUserDao();
           iniUser = new User(903327,"Anselmo","Hernández","naranya1",new UserType(1,"Administrador"));
           userDao.create(iniUser);
           iniUser = new User(903360,"Ruben","Garcia","naranya2",new UserType(2,"Tecnico"));
           userDao.create(iniUser);
           iniUser = new User(902309,"Samantha","Ruiz","naranya3",new UserType(2,"Tecnico"));
           userDao.create(iniUser);
           iniUser = new User(901327,"Luis","Carvajal","naranya4",new UserType(2,"Tecnico"));
           userDao.create(iniUser);

           final Dao<UserTaskTypes, Integer> userTaskTypes = getHelper().getUserTaskTypesDao();
           iniUserTaskTypes = new UserTaskTypes(userDao.queryForId(903360),taskTypeDao.queryForId(2));
           userTaskTypes.create(iniUserTaskTypes);
           iniUserTaskTypes = new UserTaskTypes(userDao.queryForId(902309),taskTypeDao.queryForId(1));
           userTaskTypes.create(iniUserTaskTypes);
           iniUserTaskTypes = new UserTaskTypes(userDao.queryForId(902309),taskTypeDao.queryForId(2));
           userTaskTypes.create(iniUserTaskTypes);
           iniUserTaskTypes = new UserTaskTypes(userDao.queryForId(901327),taskTypeDao.queryForId(1));
           userTaskTypes.create(iniUserTaskTypes);
           iniUserTaskTypes = new UserTaskTypes(userDao.queryForId(901327),taskTypeDao.queryForId(2));
           userTaskTypes.create(iniUserTaskTypes);
           iniUserTaskTypes = new UserTaskTypes(userDao.queryForId(901327),taskTypeDao.queryForId(4));
           userTaskTypes.create(iniUserTaskTypes);







       }
       catch (SQLException ex){ex.printStackTrace();}



    }

    private DataBaseHelper getHelper(){
        if(dataBaseHelper == null)
            dataBaseHelper = OpenHelperManager.getHelper(this,DataBaseHelper.class);
        return dataBaseHelper;
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();

        if(dataBaseHelper != null){
            OpenHelperManager.releaseHelper();
            dataBaseHelper = null;
        }

    }


}
