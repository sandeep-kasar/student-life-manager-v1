package com.studentlifemanager.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.studentlifemanager.application.MyApplication;

/**
 * Created by Owner on 23/04/2017.
 */

public class DBConnection {

    //SQLiteDatabase
    private static SQLiteDatabase db;

    public static SQLiteDatabase getInstance() {
        if (db == null) {
            DbHelper helper = new DbHelper(MyApplication.getInstance().getApplicationContext());
            db = helper.getWritableDatabase();
        }

        return db;
    }

    public static void openDB() {
        DbHelper helper = new DbHelper(MyApplication.getInstance().getApplicationContext());
        db = helper.getWritableDatabase();
    }

    public static void closeDB() {
        db.close();
        db = null;
    }
}
