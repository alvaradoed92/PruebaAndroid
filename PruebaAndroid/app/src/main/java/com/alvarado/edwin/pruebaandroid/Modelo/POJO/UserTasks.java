package com.alvarado.edwin.pruebaandroid.Modelo.POJO;

import com.j256.ormlite.field.DatabaseField;

/**
 * Created by edwinalvarado on 01/06/17.
 */

public class UserTasks {
    @DatabaseField(canBeNull = false, foreign = true)
    private User user;

    @DatabaseField(canBeNull = false, foreign = true)
    private Task task;

    @DatabaseField
    private Integer completed;

    public UserTasks() {
    }
    public UserTasks(User user, Task task, Integer completed)
    {
        this.user= user;
        this.task = task;
        this.completed = completed;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public Integer getCompleted() {
        return completed;
    }

    public void setCompleted(Integer completed) {
        this.completed = completed;
    }
}
