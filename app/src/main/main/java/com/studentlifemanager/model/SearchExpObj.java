package com.studentlifemanager.model;

/**
 * Created by Owner on 22/05/2017.
 */

public class SearchExpObj {

    String ex_date,ex_amount,ex_title,ex_note;

    public SearchExpObj(String ex_amount, String ex_date, String ex_note, String ex_title) {
        this.ex_amount = ex_amount;
        this.ex_date = ex_date;
        this.ex_note = ex_note;
        this.ex_title = ex_title;
    }

    public String getEx_amount() {
        return ex_amount;
    }

    public void setEx_amount(String ex_amount) {
        this.ex_amount = ex_amount;
    }

    public String getEx_date() {
        return ex_date;
    }

    public void setEx_date(String ex_date) {
        this.ex_date = ex_date;
    }

    public String getEx_note() {
        return ex_note;
    }

    public void setEx_note(String ex_note) {
        this.ex_note = ex_note;
    }

    public String getEx_title() {
        return ex_title;
    }

    public void setEx_title(String ex_title) {
        this.ex_title = ex_title;
    }
}
