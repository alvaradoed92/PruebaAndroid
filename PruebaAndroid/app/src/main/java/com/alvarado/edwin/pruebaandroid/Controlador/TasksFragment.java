package com.alvarado.edwin.pruebaandroid.Controlador;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.alvarado.edwin.pruebaandroid.Modelo.Adapters.ListAdapter;
import com.alvarado.edwin.pruebaandroid.Modelo.DB.DataBaseHelper;
import com.alvarado.edwin.pruebaandroid.Modelo.POJO.Task;
import com.alvarado.edwin.pruebaandroid.Modelo.POJO.TaskType;
import com.alvarado.edwin.pruebaandroid.Modelo.POJO.UserTasks;
import com.alvarado.edwin.pruebaandroid.R;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.GenericRawResults;
import com.j256.ormlite.dao.RawRowMapper;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by edwinalvarado on 02/06/17.
 */

public class TasksFragment extends Fragment {


    RecyclerView tasksList;
    DataBaseHelper dataBaseHelper = null;
    ListAdapter tasksAdapter ;
    ListView userTasksListView;
    ArrayList<UserTasks> userTaskses;

    public static String USERID_ARGUMENT = "userID";

    public static String USERTYPE_ARGUMENT = "userType";

    int userID = 0;
    int userType = 0;

    public TasksFragment() {

    }

    public static TasksFragment newInstance(int userID, int userType) {
        TasksFragment tasksFragment = new TasksFragment();
        Bundle args = new Bundle();
        args.putInt(USERID_ARGUMENT, userID);
        args.putInt(USERTYPE_ARGUMENT, userType);
        tasksFragment.setArguments(args);
        return tasksFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // Defines the xml file for the fragment
        return inflater.inflate(R.layout.tasks_layout, parent, false);


    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        userID = getArguments().getInt(USERID_ARGUMENT, 0);
        userType = getArguments().getInt(USERTYPE_ARGUMENT, 0);



        userTasksListView = (ListView) view.findViewById(R.id.rvTasks);


        try {

            final Dao<UserTasks, Integer> userTasksDao = getHelper().getUserTasksDao();

            final Dao<Task, Integer> taskDao = getHelper().getTaskDao();

            final Dao<TaskType, Integer> taskTypeDao = getHelper().getTaskTypeDao();

            List<UserTasks> userTasksList = new ArrayList<>();
            if (userType == 1)
                userTasksList = userTasksDao.queryForAll();
            else {
                if ((userTasksDao.queryBuilder()
                        .where()
                        .eq("user_id", userID)
                        .query().size() > 0)) {

                    userTasksList = userTasksDao.queryBuilder()
                            .where()
                            .eq("user_id", userID)
                            .query();

                }
            }

            for (UserTasks userTasks : userTasksList) {
                userTasks.setTask(taskDao.queryForId(userTasks.getTask().getTaskId()));
                userTasks.getTask().setTaskType(taskTypeDao.queryForId(userTasks.getTask().getTaskType().getTaskTypeId()));

            }


            userTaskses = (ArrayList<UserTasks>) userTasksList;
            LLenarUserTaskList();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }


        ;


    }

    public void LLenarUserTaskList()
    {
        tasksAdapter = (new ListAdapter(getActivity(),R.layout.task_row,userTaskses) {
            @Override
            public void onEntrada(Object entrada, View view) {

                UserTasks auxUserTasks = (UserTasks)entrada;

                if(auxUserTasks.getCompleted()==1)
                    view.setBackgroundResource(R.drawable.colorrowg);

                TextView farmer = (TextView) view.findViewById(R.id.tvUser);
                TextView description = (TextView) view.findViewById(R.id.tvDescription);
                TextView duration = (TextView) view.findViewById(R.id.tvHours);
                TextView taskType = (TextView)view.findViewById(R.id.tvtt);

                farmer.setText(String.valueOf(auxUserTasks.getUser().getUserId()));
                description.setText(auxUserTasks.getTask().getDescription());
                duration.setText(String.valueOf(auxUserTasks.getTask().getHours()));
                taskType.setText(auxUserTasks.getTask().getTaskType().getTaskName());

            }
        });

        userTasksListView.setAdapter(tasksAdapter);
        tasksAdapter.notifyDataSetChanged();
        userTasksListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                try {

                    if(userType==2) {
                        final Dao<UserTasks, Integer> userTasksDao = getHelper().getUserTasksDao();
                        UserTasks auxUserTasks = userTaskses.get(position);

                        auxUserTasks.setCompleted(1);
                        userTaskses.get(position).setCompleted(1);
                        //userTasksDao.update(auxUserTasks);


                        GenericRawResults<Integer> rawResults =
                                userTasksDao.queryRaw(
                                        " Update UserTasks set completed = 1 where user_id = " + String.valueOf(auxUserTasks.getUser().getUserId()) +
                                                "  AND task_id = " + String.valueOf(auxUserTasks.getTask().getTaskId()),
                                        new RawRowMapper<Integer>() {
                                            public Integer mapRow(String[] columnNames, String[] resultColumns) {

                                                return Integer.parseInt(resultColumns[0]);
                                            }
                                        });

                        LLenarUserTaskList();
                    }

                }
                catch (SQLException ex){ex.printStackTrace();}

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
