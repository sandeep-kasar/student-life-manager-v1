package com.studentlifemanager.model;

/**
 * Created by Owner on 23/05/2017.
 */

public class SearchTaskObj {

    String tk_title,tk_date,tk_time,tk_type;

    public SearchTaskObj(String tk_title, String tk_date, String tk_time, String tk_type) {
        this.tk_date = tk_date;
        this.tk_time = tk_time;
        this.tk_title = tk_title;
        this.tk_type = tk_type;
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

    public String getTk_title() {
        return tk_title;
    }

    public void setTk_title(String tk_title) {
        this.tk_title = tk_title;
    }

    public String getTk_type() {
        return tk_type;
    }

    public void setTk_type(String tk_type) {
        this.tk_type = tk_type;
    }
}
