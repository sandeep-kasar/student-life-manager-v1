package com.studentlifemanager.database;

/**
 * Created by Owner on 23/04/2017.
 */

public class Tables {

    public static class MyExpence {

        public static final String CREATE_TABLE = "create table MyExpence" +
                "(" +
                "ex_id integer primary key," +
                "ex_title text," +
                "ex_note text," +
                "ex_amount integer," +
                "ex_type integer," +
                "ex_date text" +
                ")";
    }

    public static class ToDoTask {

        public static final String CREATE_TABLE = "create table ToDoTask" +
                "(" +
                "tk_id integer primary key," +
                "tk_title text," +
                "tk_date text," +
                "tk_time text," +
                "tk_type integer" +
                ")";
    }
}
