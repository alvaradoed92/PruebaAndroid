package com.alvarado.edwin.pruebaandroid.Modelo.POJO;

import com.j256.ormlite.field.DatabaseField;

/**
 * Created by edwinalvarado on 01/06/17.
 */

public class TaskType {
    @DatabaseField(id = true)
    private Integer taskTypeId;

    @DatabaseField
    private String taskName;

    public TaskType() {
    }
    public TaskType(Integer taskTypeId, String taskName)
    {
        this.taskTypeId= taskTypeId;
        this.taskName=taskName;
    }

    public Integer getTaskTypeId() {
        return taskTypeId;
    }

    public void setTaskTypeId(Integer taskTypeId) {
        this.taskTypeId = taskTypeId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String toString() {
        return taskName;
    }
}
