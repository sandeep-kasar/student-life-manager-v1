package com.studentlifemanager.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Owner on 23/04/2017.
 */

public class DbHelper extends SQLiteOpenHelper {

    //tag
    public static final String TAG = DbHelper.class.getSimpleName();

    //get context
    Context context;

    public DbHelper(Context context) {
        super(context, "SDLF", null, 2);
        this.context=context;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(Tables.MyExpence.CREATE_TABLE);
            db.execSQL(Tables.ToDoTask.CREATE_TABLE);
        }catch (Exception e){
            e.printStackTrace();
            Log.e(TAG,"problem while creating table"+e.toString());
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        Log.e(TAG,"oldVersion="+oldVersion);
        Log.e(TAG,"newVersion="+newVersion);

    }

    public void removeDB(){
        context.deleteDatabase("SDLF");
    }
}
