package com.studentlifemanager.model;

public class TaskObj {

    int tk_id;
    String tk_title;
    String tk_date;
    String tk_time;
    int tk_type;

    public TaskObj(int tk_id, String tk_title, String tk_date, String tk_time, int tk_type) {
        this.tk_id = tk_id;
        this.tk_title = tk_title;
        this.tk_date = tk_date;
        this.tk_time = tk_time;
        this.tk_type = tk_type;
    }

    public int getTk_id() {
        return tk_id;
    }

    public void setTk_id(int tk_id) {
        this.tk_id = tk_id;
    }

    public String getTk_title() {
        return tk_title;
    }

    public void setTk_title(String tk_title) {
        this.tk_title = tk_title;
    }

    public String getTk_date() {
        return tk_date;
    }

    public void setTk_date(String tk_date) {
        this.tk_date = tk_date;
    }

    public String getTk_time() {
        return tk_time;
    }

    public void setTk_time(String tk_time) {
        this.tk_time = tk_time;
    }

    public int getTk_type() {
        return tk_type;
    }

    public void setTk_type(int tk_type) {
        this.tk_type = tk_type;
    }
}
