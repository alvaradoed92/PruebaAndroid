package com.alvarado.edwin.pruebaandroid.Modelo.POJO;

import com.j256.ormlite.field.DatabaseField;

/**
 * Created by edwinalvarado on 01/06/17.
 */

public class UserTaskTypes {
    @DatabaseField(canBeNull = false, foreign = true)
    private User user;

    @DatabaseField(canBeNull = false, foreign = true)
    private TaskType taskType;

    public UserTaskTypes() {
    }
    public UserTaskTypes(User user,TaskType taskType)
    {
        this.user=user;
        this.taskType=taskType;
    }

    public User getUser() {
        return user;
    }


    public void setUser(User user) {
        this.user = user;
    }

    public TaskType getTaskType() {
        return taskType;
    }

    public void setTaskType(TaskType type) {
        this.taskType = taskType;
    }
}
