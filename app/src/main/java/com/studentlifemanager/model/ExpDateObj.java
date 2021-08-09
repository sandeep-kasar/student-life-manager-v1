package com.studentlifemanager.model;

/**
 * Created by Owner on 14/04/2017.
 */

public class ExpDateObj {
    String exp_date,exp_total;

    public ExpDateObj(String exp_date, String exp_total) {
        this.exp_date = exp_date;
        this.exp_total = exp_total;
    }

    public String getExp_date() {
        return exp_date;
    }

    public void setExp_date(String exp_date) {
        this.exp_date = exp_date;
    }

    public String getExp_total() {
        return exp_total;
    }

    public void setExp_total(String exp_total) {
        this.exp_total = exp_total;
    }
}
