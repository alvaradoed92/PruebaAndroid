package com.alvarado.edwin.pruebaandroid.Controlador;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.alvarado.edwin.pruebaandroid.Modelo.DB.DataBaseHelper;
import com.alvarado.edwin.pruebaandroid.Modelo.POJO.User;
import com.alvarado.edwin.pruebaandroid.Modelo.POJO.UserType;
import com.alvarado.edwin.pruebaandroid.R;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;

import info.hoang8f.widget.FButton;

public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;

    public static String NEWTASK_FRAGMENT_TAG = "NewTaskTag";
    public static String TASKS_FRAGMENT_TAG = "TasksTag";
    public static String QUERY_FRAGMENT_TAG = "QueryTag";
    public static final String PAPREFERENCES = "PruebaAndroidPreferences" ;
    public static final String USERLOGINPR = "userLogIn";
    public static final String ACTIVECONNECTION = "activeConnection";
    //public static String USERID = "UserId";
    public static String USERID_ARGUMENT = "userID";
    SharedPreferences sharedpreferences;
    DataBaseHelper dataBaseHelper = null;
    int userTypeId=0;
    int userID=0;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {


                case R.id.navigation_new_task: {
                  /*  getSupportFragmentManager().beginTransaction().
                            replace(R.id.content, new NewTaskFragment(), NEWTASK_FRAGMENT_TAG).
                            commit();*/

                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    NewTaskFragment newTaskFragment = NewTaskFragment.newInstance(userID);
                    ft.replace(R.id.content, newTaskFragment);
                    ft.commit();
                    // mTextMessage.setText(R.string.title_home);
                    return true;
                }
                case R.id.navigation_tasks: {
                    /*getSupportFragmentManager().beginTransaction().
                            replace(R.id.content, new TasksFragment(), TASKS_FRAGMENT_TAG).
                            commit();*/

                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    TasksFragment tasksFragment = TasksFragment.newInstance(userID,userTypeId);
                    ft.replace(R.id.content, tasksFragment);
                    ft.commit();
                    //mTextMessage.setText(R.string.title_dashboard);
                    return true;
                }
                case R.id.navigation_query: {
                    getSupportFragmentManager().beginTransaction().
                            replace(R.id.content, new QueryFragment(), QUERY_FRAGMENT_TAG).
                            commit();
                    // mTextMessage.setText(R.string.title_notifications);
                    return true;
                }
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar mainToolbar = (Toolbar)findViewById(R.id.mainToolbar);
        setSupportActionBar(mainToolbar);
         sharedpreferences = getSharedPreferences(PAPREFERENCES, MODE_PRIVATE);
        Integer activeUser = sharedpreferences.getInt(USERLOGINPR, 0);
       try {
           final Dao<User, Integer> userDao = getHelper().getUserDao();
           final Dao<UserType, Integer> userTypeDao = getHelper().getUserTypeDao();
           User auxUser = userDao.queryForId(activeUser);
           UserType auxType = userTypeDao.queryForId(auxUser.getUserType().getUserTypeId());
           userTypeId= auxType.getUserTypeId();
           userID= auxUser.getUserId();
           getSupportActionBar().setTitle(auxUser.getName() + " (" + auxType.getUserTypeName() + ")");
       }
       catch (SQLException ex){ex.printStackTrace();}


         //getSupportActionBar().setTitle("");



        if(userTypeId ==1) {
            BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
            navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
            navigation.setVisibility(View.VISIBLE);

          /*  getSupportFragmentManager().beginTransaction().
                    replace(R.id.content, new NewTaskFragment.newInstance(userID), NEWTASK_FRAGMENT_TAG).
                    commit();*/
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            NewTaskFragment newTaskFragment = NewTaskFragment.newInstance(userID);
            ft.replace(R.id.content, newTaskFragment);
            ft.commit();

        }
        else if(userTypeId == 2)
        {
            BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation_tecnico);
            navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
            navigation.setVisibility(View.VISIBLE);
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            TasksFragment tasksFragment = TasksFragment.newInstance(userID,userTypeId);
            ft.replace(R.id.content, tasksFragment);
            ft.commit();
           /* getSupportFragmentManager().beginTransaction().
                    replace(R.id.content, new TasksFragment(), TASKS_FRAGMENT_TAG).
                    commit();*/
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_bar, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_logout: {

                SharedPreferences.Editor editor = sharedpreferences.edit();

                editor.putInt(USERLOGINPR, 0);
                editor.putInt(ACTIVECONNECTION, 0);
                editor.commit();

                startActivity(new Intent(MainActivity.this, InitialActivity.class)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP));
                finish();

                return true;
            }

            default:
                return super.onOptionsItemSelected(item);
        }
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
