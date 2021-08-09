package com.studentlifemanager.model;

/**
 * Created by Owner on 14/04/2017.
 */

public class ExpListObj {

    int ex_id;
    String ex_title;
    String ex_note;
    int ex_amount;
    int ex_type;
    String ex_date;

    public ExpListObj(int ex_id, String ex_title, String ex_note, int ex_amount, int ex_type, String ex_date) {
        this.ex_id = ex_id;
        this.ex_title = ex_title;
        this.ex_note = ex_note;
        this.ex_amount = ex_amount;
        this.ex_type = ex_type;
        this.ex_date = ex_date;
    }

    public int getEx_id() {
        return ex_id;
    }

    public void setEx_id(int ex_id) {
        this.ex_id = ex_id;
    }

    public String getEx_title() {
        return ex_title;
    }

    public void setEx_title(String ex_title) {
        this.ex_title = ex_title;
    }

    public String getEx_note() {
        return ex_note;
    }

    public void setEx_note(String ex_note) {
        this.ex_note = ex_note;
    }

    public int getEx_amount() {
        return ex_amount;
    }

    public void setEx_amount(int ex_amount) {
        this.ex_amount = ex_amount;
    }

    public int getEx_type() {
        return ex_type;
    }

    public void setEx_type(int ex_type) {
        this.ex_type = ex_type;
    }

    public String getEx_date() {
        return ex_date;
    }

    public void setEx_date(String ex_date) {
        this.ex_date = ex_date;
    }
}
