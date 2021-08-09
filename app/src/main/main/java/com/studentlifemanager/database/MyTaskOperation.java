package com.studentlifemanager.database;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.studentlifemanager.application.MyApplication;
import com.studentlifemanager.model.SearchTaskObj;
import com.studentlifemanager.model.TaskObj;
import com.studentlifemanager.model.ToDoTaskObj;
import com.studentlifemanager.model.TodoDateObj;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by Owner on 01/05/2017.
 */

public class MyTaskOperation {
    //get SQLiteDatabase instance
    SQLiteDatabase db;
    //object list
    LinkedList<Object> linkedList = new LinkedList<>();

    public MyTaskOperation() {
        //get database instance
        db = DBConnection.getInstance();
    }

    public void insertTask(String tk_title, String tk_date, String tk_time, Integer tk_type) {

        try {
            //insert query
            String sql = "INSERT  INTO ToDoTask " +
                    "(tk_title,tk_date,tk_time,tk_type) " +
                    "VALUES('" + tk_title + "','" + tk_date + "','" + tk_time + "'," +
                    "'" + tk_type + "')";

            //execute query
            db.execSQL(sql);

            Log.e(MyApplication.TAG, "insertTask" + tk_title);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getToDoTask(String date) {

        int size = 0;

        try {
            String selectQuery = "SELECT tk_id,tk_title,tk_date,tk_time FROM ToDoTask WHERE tk_date='" + date + "' AND tk_type=0 ORDER BY tk_id DESC";
            Cursor cursor = null;
            cursor = db.rawQuery(selectQuery, null);
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    do {
                        //add in object
                        ToDoTaskObj toDoTaskObj = new ToDoTaskObj(cursor.getInt(0), cursor.getString(1),
                                cursor.getString(2), cursor.getString(3));

                        //add data
                        linkedList.add(toDoTaskObj);


                        //increase size
                        size++;

                    } while (cursor.moveToNext());
                }
            }
            cursor.close();

            return size;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return size;
    }

    public int getDoneTask(String date) {

        int size = 0;

        try {
            String selectQuery = "SELECT tk_id,tk_title,tk_date,tk_time FROM ToDoTask WHERE tk_date='" + date + "' AND tk_type=1 ORDER BY tk_id DESC";
            Cursor cursor = null;
            cursor = db.rawQuery(selectQuery, null);
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    do {
                        //add in object
                        ToDoTaskObj toDoTaskObj = new ToDoTaskObj(cursor.getInt(0), cursor.getString(1),
                                cursor.getString(2), cursor.getString(3));

                        //add data
                        linkedList.add(toDoTaskObj);

                        //increment size
                        size++;
                    } while (cursor.moveToNext());
                }

                return size;
            }
            cursor.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return size;
    }

    public int getTask(String date) {

        int size = 0;

        try {
            String selectQuery = "SELECT * FROM ToDoTask WHERE tk_date='" + date + "' ORDER BY tk_id DESC";
            Cursor cursor = null;
            cursor = db.rawQuery(selectQuery, null);
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    do {
                        //add in object
                        TaskObj taskObj = new TaskObj(cursor.getInt(0), cursor.getString(1),
                                cursor.getString(2), cursor.getString(3), cursor.getInt(4));

                        //add data
                        linkedList.add(taskObj);

                        //increment size
                        size++;
                    } while (cursor.moveToNext());
                }

                return size;
            }
            cursor.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return size;
    }

    public ArrayList<String> getDate() {

        ArrayList<String> arrayList = new ArrayList<>();

        try {

            String selectQuery = "SELECT DISTINCT tk_date FROM ToDoTask ORDER BY tk_id DESC";
            Cursor cursor = null;
            cursor = db.rawQuery(selectQuery, null);
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    do {
                        arrayList.add(cursor.getString(0));
                    } while (cursor.moveToNext());
                }
            }
            cursor.close();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return arrayList;
    }

    public LinkedList<Object> getToDoData() {

        //get date arraylist
        ArrayList<String> arrayListDate = new ArrayList<>();

        //get dates
        arrayListDate = getDate();

        for (String date : arrayListDate) {

            //add data in TodoDateObj object
            TodoDateObj todoDateObj = new TodoDateObj(date);

            //add in expDateObj
            linkedList.add(todoDateObj);

            ////add in tasklist
            int size = getToDoTask(date);

            //if no data available
            if (size == 0) {
                linkedList.remove(todoDateObj);
            }

        }


        return linkedList;
    }

    public LinkedList<Object> getDoneData() {

        //get date arraylist
        ArrayList<String> arrayListDate = new ArrayList<>();

        //get dates
        arrayListDate = getDate();

        for (String date : arrayListDate) {

            //add data in TodoDateObj object
            TodoDateObj todoDateObj = new TodoDateObj(date);

            //add in expDateObj
            linkedList.add(todoDateObj);

            ////add in tasklist
            int size = getDoneTask(date);

            //if no data available
            if (size == 0) {
                linkedList.remove(todoDateObj);
            }

        }


        return linkedList;
    }

    public LinkedList<Object> getTaskData() {

        //get date arraylist
        ArrayList<String> arrayListDate = new ArrayList<>();

        //get dates
        arrayListDate = getDate();

        for (String date : arrayListDate) {

            //add data in TodoDateObj object
            TodoDateObj todoDateObj = new TodoDateObj(date);

            //add in expDateObj
            linkedList.add(todoDateObj);

            ////add in tasklist
            int size = getTask(date);

            //if no data available
            if (size == 0) {
                linkedList.remove(todoDateObj);
            }

        }


        return linkedList;
    }

    public void updateTask(int id, String task, String date, String time) {

        String strSQL = "UPDATE ToDoTask SET tk_title ='" + task + "', tk_date ='" + date + "',tk_time ='" + time + "'WHERE tk_id = " + id;

        db.execSQL(strSQL);
    }

    public void deleteTask(int id) {

        String strSQL = "Delete from ToDoTask WHERE tk_id = " + id;

        db.execSQL(strSQL);
    }

    public void donetheTask(int id) {

        String strSQL = "UPDATE ToDoTask SET tk_type =1 WHERE tk_id = " + id;

        db.execSQL(strSQL);
    }

    public ArrayList<SearchTaskObj> getSearchData() {

        //init arraylist
        ArrayList<SearchTaskObj> arrayList = new ArrayList<>();

        try {
            //String selectQuery = "SELECT DISTINCT ex_date FROM MyExpence" ;
            //String selectQuery = "SELECT DISTINCT ex_date FROM MyExpence ORDER BY ex_date DESC";
            String selectQuery = "SELECT tk_title,tk_date,tk_time,tk_type FROM ToDoTask";
            Cursor cursor = null;
            cursor = db.rawQuery(selectQuery, null);
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    do {
                        //add data
                        SearchTaskObj searchTaskObj = new SearchTaskObj(cursor.getString(0), cursor.getString(1),
                                cursor.getString(2), cursor.getString(3));
                        arrayList.add(searchTaskObj);
                    } while (cursor.moveToNext());
                }
            }
            cursor.close();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }


        return arrayList;
    }

}

