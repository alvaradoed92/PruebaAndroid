package com.alvarado.edwin.pruebaandroid.Controlador;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.alvarado.edwin.pruebaandroid.Modelo.DB.DataBaseHelper;
import com.alvarado.edwin.pruebaandroid.Modelo.POJO.User;
import com.alvarado.edwin.pruebaandroid.R;
import com.alvarado.edwin.pruebaandroid.info.hoang8f.widget.FButton;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;

/**
 * Created by edwinalvarado on 02/06/17.
 */

public class LogInFragment extends Fragment {

    FButton fbLogIn;
    EditText etUserId;
    EditText etPwd;
    public static String SIGNUP_FRAGMENT_TAG = "SignUpTag";
    public static String USERID = "UserId";
    public static String PASSWORD = "Password";
    public static final String PAPREFERENCES = "PruebaAndroidPreferences" ;
    public static final String USERLOGINPR = "userLogIn";
    public static final String ACTIVECONNECTION = "activeConnection";

    SharedPreferences sharedpreferences;



    DataBaseHelper dataBaseHelper = null;

    public LogInFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // Defines the xml file for the fragment
        return inflater.inflate(R.layout.login_layout, parent, false);


    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        fbLogIn = (FButton) view.findViewById(R.id.fblogin);
        etUserId = (EditText)view.findViewById(R.id.etUserId);
        etPwd = (EditText)view.findViewById(R.id.etPassword);
        sharedpreferences = getActivity().getSharedPreferences(PAPREFERENCES, Context.MODE_PRIVATE);

        etUserId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etUserId.setText("");
            }
        });
        etPwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etPwd.setText("");
            }
        });
        fbLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Integer vuserId = 0;
                String vPassword ="";
                try{
                    vuserId = Integer.valueOf(etUserId.getText().toString());
                    vPassword =etPwd.getText().toString();

                }catch (Exception ex){
                    Toast.makeText(getActivity(), getResources().getString(R.string.login_msg2), Toast.LENGTH_SHORT).show();
                    return;

                }

                if(IsAuthenticated(vuserId,vPassword)) {
                    startActivity(new Intent(getActivity(), MainActivity.class)
                            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP));
                    getActivity().finish();
                }
                else
                    Toast.makeText(getActivity(), getResources().getString(R.string.login_msg1), Toast.LENGTH_SHORT).show();



            }
        });



    }

    private boolean IsAuthenticated(Integer userId, String password) {

        try {
            final Dao<User, Integer> userDao = getHelper().getUserDao();
            if ((userDao.queryBuilder()
                    .where()
                    .eq(USERID, userId)
                    .and()
                    .eq(PASSWORD, password)
                    .query().size() > 0)) {

                SharedPreferences.Editor editor = sharedpreferences.edit();

                editor.putInt(USERLOGINPR, userId);
                editor.putInt(ACTIVECONNECTION, 1);
                editor.commit();
                return true;
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }


        return false;
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