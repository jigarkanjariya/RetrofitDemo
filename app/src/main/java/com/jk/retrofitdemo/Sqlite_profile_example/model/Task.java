package com.jk.retrofitdemo.Sqlite_profile_example.model;

/**
 * Created by Abc on 3/8/2018.
 */

public class Task {

    public String id;
    public String task;
    public Boolean check;
    public String name;
    public boolean isChecked;
    public int taskLabelid;
    public String taskLabelName;

    public int getTaskLabelid() {
        return taskLabelid;
    }

    public void setTaskLabelid(int taskLabelid) {
        this.taskLabelid = taskLabelid;
    }

    public String getTaskLabelName() {
        return taskLabelName;
    }

    public void setTaskLabelName(String taskLabelName) {
        this.taskLabelName = taskLabelName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Task(String s) {
        this.task=s;
    }

    public Task() {

    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public Boolean getCheck() {
        return check;
    }

    public void setCheck(Boolean check) {
        this.check = check;
    }
    public boolean getChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
