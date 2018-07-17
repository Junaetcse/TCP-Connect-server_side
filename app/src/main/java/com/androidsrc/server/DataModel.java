package com.androidsrc.server;

/**
 * Created by tkb on 2017-04-04.
 */

public class DataModel {
    private String name;
    private String task;
    private boolean isChecked;
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
