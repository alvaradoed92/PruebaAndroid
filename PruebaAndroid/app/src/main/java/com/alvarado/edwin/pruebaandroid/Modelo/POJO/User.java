package com.alvarado.edwin.pruebaandroid.Modelo.POJO;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;

/**
 * Created by edwinalvarado on 01/06/17.
 */

public class User {

    @DatabaseField(id = true)
    private Integer userId;
    @DatabaseField
    private String name;
    @DatabaseField
    private String lastName;
    @DatabaseField
    private String password;

    @DatabaseField(canBeNull = false, foreign = true)
    private UserType userType;


    public User() {
    }
    public User(Integer userId,String name, String lastName,String password, UserType userType)
    {
        this.userId= userId;
        this.name= name;
        this.lastName=lastName;
        this.password= password;
        this.userType = userType;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }
}
