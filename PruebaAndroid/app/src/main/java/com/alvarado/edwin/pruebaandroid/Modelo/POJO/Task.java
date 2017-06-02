package com.alvarado.edwin.pruebaandroid.Modelo.POJO;

import com.j256.ormlite.field.DatabaseField;

/**
 * Created by edwinalvarado on 01/06/17.
 */

public class Task {
    @DatabaseField(generatedId = true)
    private Integer taskId;

    @DatabaseField
    private String description;

    @DatabaseField
    private Integer hours;

    @DatabaseField(canBeNull = false, foreign = true)
    private TaskType taskType;

    public Task() {
    }
    public Task(String description, Integer hours, TaskType taskType){

        this.description = description;
        this.hours = hours;
        this.taskType = taskType;
    }

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getHours() {
        return hours;
    }

    public void setHours(Integer hours) {
        this.hours = hours;
    }

    public TaskType getTaskType() {
        return taskType;
    }

    public void setTaskType(TaskType taskType) {
        this.taskType = taskType;
    }
}
