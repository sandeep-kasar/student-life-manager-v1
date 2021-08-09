package com.studentlifemanager.model;

/**
 * Created by Owner on 14/04/2017.
 */

public class ExpHeaderObj {

    Integer balance_plus,balance_minus,balance_total;

    public ExpHeaderObj(Integer balance_minus, Integer balance_plus,Integer balance_total ) {
        this.balance_minus = balance_minus;
        this.balance_plus = balance_plus;
        this.balance_total = balance_total;
    }

    public Integer getBalance_minus() {
        return balance_minus;
    }

    public void setBalance_minus(Integer balance_minus) {
        this.balance_minus = balance_minus;
    }

    public Integer getBalance_plus() {
        return balance_plus;
    }

    public void setBalance_plus(Integer balance_plus) {
        this.balance_plus = balance_plus;
    }

    public Integer getBalance_total() {
        return balance_total;
    }

    public void setBalance_total(Integer balance_total) {
        this.balance_total = balance_total;
    }

}
