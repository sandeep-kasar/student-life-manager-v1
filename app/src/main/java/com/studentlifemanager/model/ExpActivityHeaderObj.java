package com.studentlifemanager.model;

/**
 * Created by Owner on 14/04/2017.
 */

public class ExpActivityHeaderObj {

    Integer balance_plus,balance_minus;

    public ExpActivityHeaderObj(Integer balance_plus, Integer balance_minus) {
        this.balance_plus = balance_plus;
        this.balance_minus = balance_minus;
    }

    public Integer getBalance_plus() {
        return balance_plus;
    }

    public void setBalance_plus(Integer balance_plus) {
        this.balance_plus = balance_plus;
    }

    public Integer getBalance_minus() {
        return balance_minus;
    }

    public void setBalance_minus(Integer balance_minus) {
        this.balance_minus = balance_minus;
    }
}
