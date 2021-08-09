package com.studentlifemanager.application;

import android.app.Application;
import android.util.Log;

import com.studentlifemanager.database.DBConnection;

/**
 * Created by Owner on 23/04/2017.
 */

public class MyApplication extends Application {

    //tag
    public static final String TAG = Application.class.getSimpleName();

    //instance
    private static MyApplication mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        DBConnection.openDB();
        Log.e(MyApplication.TAG, "Database opened successfully");
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        DBConnection.closeDB();
        Log.e(MyApplication.TAG, "Database closed successfully");
    }

    public static synchronized Application getInstance() {
        return mInstance;
    }
}


