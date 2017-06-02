package com.alvarado.edwin.pruebaandroid.Controlador;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.alvarado.edwin.pruebaandroid.Modelo.DB.DataBaseHelper;
import com.alvarado.edwin.pruebaandroid.Modelo.POJO.Task;
import com.alvarado.edwin.pruebaandroid.Modelo.POJO.TaskType;
import com.alvarado.edwin.pruebaandroid.Modelo.POJO.User;
import com.alvarado.edwin.pruebaandroid.Modelo.POJO.UserTasks;
import com.alvarado.edwin.pruebaandroid.R;
import com.alvarado.edwin.pruebaandroid.info.hoang8f.widget.FButton;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.GenericRawResults;
import com.j256.ormlite.dao.RawRowMapper;
import com.j256.ormlite.support.DatabaseResults;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by edwinalvarado on 02/06/17.
 */

public class NewTaskFragment extends Fragment {


    public static String USERID_ARGUMENT = "userID";

    int userTypeId = 2; //Administradores
    int userID = 0;
    DataBaseHelper dataBaseHelper = null;
    Spinner spTaskList;
    FButton fbSave;
    FButton fbClean;
    EditText etHours;
    EditText etDescription;


    public NewTaskFragment() {

    }

    public static NewTaskFragment newInstance(int userID) {
        NewTaskFragment newTaskFragment = new NewTaskFragment();
        Bundle args = new Bundle();
        args.putInt(USERID_ARGUMENT, userID);
        newTaskFragment.setArguments(args);
        return newTaskFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // Defines the xml file for the fragment
        return inflater.inflate(R.layout.new_task_layout, parent, false);


    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        userID = getArguments().getInt(USERID_ARGUMENT, 0);
        spTaskList = (Spinner) view.findViewById(R.id.spTaskType);
        fbSave = (FButton) view.findViewById(R.id.fbSave);
        fbClean = (FButton) view.findViewById(R.id.fbClean);
        etHours = (EditText)view.findViewById(R.id.ethours);
        etDescription = (EditText)view.findViewById(R.id.etDescription);

        try {
            final Dao<TaskType, Integer> taskTypeDao = getHelper().getTaskTypeDao();
            List<TaskType> taskTypeList = taskTypeDao.queryForAll();
            ArrayAdapter spinnerArrayAdapter = new ArrayAdapter(getActivity(),
                    R.layout.view_spinner_item,taskTypeList);
            spTaskList.setAdapter(spinnerArrayAdapter);

        } catch (SQLException ex) {
            ex.printStackTrace();
        }


        fbClean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etDescription.setText(null);
                etHours.setText(null);


            }
        });
        fbSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Integer vHours = 0;
                String vDescription ="";
                try{
                    vHours = Integer.valueOf(etHours.getText().toString());
                    vDescription = etDescription.getText().toString();

                }catch (Exception ex){
                    Toast.makeText(getActivity(), getResources().getString(R.string.newtask_msg1), Toast.LENGTH_SHORT).show();
                    return;

                }

                try {
                    final Dao<Task, Integer> taskDao = getHelper().getTaskDao();
                    final Dao<User, Integer> userDao = getHelper().getUserDao();
                    final Dao<UserTasks, Integer> userTasksDao = getHelper().getUserTasksDao();
                    TaskType auxTaskType = (TaskType)spTaskList.getSelectedItem();
                    Task auxTask = new Task(vDescription,vHours,auxTaskType);
                    taskDao.create(auxTask);
                    GenericRawResults<Integer> rawResults =
                            taskDao.queryRaw(
                                    " SELECT  A.UserID FROM( "+
                                            " SELECT u.userid,SUM(ifnull(t.hours,0)) AS suma " +
                                            " FROM User u" +
                                            " INNER JOIN UserType uy ON u.userType_Id = uy.UserTypeId and uy.UserTypeId = 2 " +
                                            " INNER JOIN UserTaskTypes utt ON u.userId = utt.user_id and utt.TaskType_id = " + String.valueOf(auxTaskType.getTaskTypeId()) +
                                            " LEFT JOIN UserTasks ut ON u.userId = ut.user_Id and ut.completed = 0   " +
                                            " LEFT JOIN Task t ON ut.task_id = t.taskId " +
                                            " GROUP BY U.userid ) A ORDER BY A.suma ASC "
                                            ,
                                    new RawRowMapper<Integer>() {
                                        public Integer mapRow(String[] columnNames, String[] resultColumns){

                                            return Integer.parseInt(resultColumns[0]);
                                        }
                                    });

                    int auxUserId = 0;
                    try{
                         auxUserId = rawResults.getFirstResult();
                    }catch (Exception ex){}

                    if(auxUserId != 0) {
                        User auxUser = userDao.queryForId(auxUserId);
                        UserTasks auxUserTasks = new UserTasks(auxUser, auxTask, 0);
                        userTasksDao.create(auxUserTasks);
                    }else
                        Toast.makeText(getActivity(),getResources().getString(R.string.newtask_msg2),Toast.LENGTH_SHORT).show();

                    fbClean.callOnClick();

                } catch (SQLException ex) {
                    ex.printStackTrace();
                }



            }
        });





    }

    private DataBaseHelper getHelper() {
        if (dataBaseHelper == null)
            dataBaseHelper = OpenHelperManager.getHelper(getActivity(), DataBaseHelper.class);
        return dataBaseHelper;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (dataBaseHelper != null) {
            OpenHelperManager.releaseHelper();
            dataBaseHelper = null;
        }
    }
}
