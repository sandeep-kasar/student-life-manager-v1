package com.studentlifemanager.database;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.util.Log;

import com.studentlifemanager.application.MyApplication;
import com.studentlifemanager.helper.CommonUtils;
import com.studentlifemanager.model.ExpActivityHeaderObj;
import com.studentlifemanager.model.ExpDateObj;
import com.studentlifemanager.model.ExpHeaderObj;
import com.studentlifemanager.model.ExpListObj;
import com.studentlifemanager.model.SearchExpObj;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;

/**
 * Created by Owner on 23/04/2017.
 */

public class MyExpenseOperation {

    private static final String APP_DIRECTORY_NAME = "Sdlm";

    //get SQLiteDatabase instance
    SQLiteDatabase db;
    //object list
    LinkedList<Object> linkedList = new LinkedList<>();

    public MyExpenseOperation() {
        //get database instance
        db = DBConnection.getInstance();
    }

    public void insertExpence(String ex_title, String ex_note, Integer ex_amount, Integer ex_type, String ex_date) {

        try {


            //insert query
            String sql = "INSERT  INTO MyExpence " +
                    "(ex_title,ex_note,ex_amount,ex_type,ex_date) " +
                    "VALUES('" + ex_title + "','" + ex_note + "','" + ex_amount + "'," +
                    "'" + ex_type + "','" + ex_date + "')";

            //execute query
            db.execSQL(sql);

            Log.e(MyApplication.TAG, "insertExpence" + ex_title);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Integer getExpense(int month) {

        int balance_plus = 0;

        try {
            String selectQuery = "select sum(ex_amount) As amount from MyExpence where ex_type = 0" +
                    " and ex_date like '%-" + month + "-%'";
            Cursor cursor = null;
            cursor = db.rawQuery(selectQuery, null);
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    do {
                        balance_plus = cursor.getInt(0);
                    } while (cursor.moveToNext());
                }
            }
            cursor.close();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        Log.e(MyApplication.TAG, "balance_plus" + balance_plus);
        return balance_plus;
    }

    public Integer getIncome(int month) {

        int balance_minus = 0;

        try {
            String selectQuery = "select sum(ex_amount) As amount from MyExpence where ex_date like '%-" + month + "-%' and ex_type=1";
            Cursor cursor = null;
            cursor = db.rawQuery(selectQuery, null);
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    do {
                        balance_minus = cursor.getInt(0);
                    } while (cursor.moveToNext());
                }
            }
            cursor.close();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return balance_minus;
    }

    public Integer getBalance() {

        int balance = 0;
        int balance_plus = 0;
        int balance_minus = 0;

        try {
            String selectQuery = "select sum(ex_amount) As amount from MyExpence where ex_type = 0";
            Cursor cursor = null;
            cursor = db.rawQuery(selectQuery, null);
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    do {
                        balance_plus = cursor.getInt(0);
                    } while (cursor.moveToNext());
                }
            }
            cursor.close();

        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }


        try {
            String selectQuery = "select sum(ex_amount) As amount from MyExpence where  ex_type=1";
            Cursor cursor = null;
            cursor = db.rawQuery(selectQuery, null);
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    do {
                        balance_minus = cursor.getInt(0);
                    } while (cursor.moveToNext());
                }
            }
            cursor.close();

        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }

        Log.e(MyApplication.TAG, "balance" + balance);

        balance = balance_minus - balance_plus;

        return balance;
    }

    public String getDateTotal(String date) {

        String date_total = "0";

        try {
            String selectQuery = "select sum(ex_amount) As amount from MyExpence where ex_date='" + date + "' and ex_type = 0";
            Cursor cursor = null;
            cursor = db.rawQuery(selectQuery, null);
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    do {
                        date_total = cursor.getString(0);
                    } while (cursor.moveToNext());
                }
            }
            cursor.close();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return date_total;
    }

    public String getDateExpense(String date, String title) {

        String date_total = "0";

        try {
            String selectQuery = "select ex_amount from MyExpence where ex_date='" + date + "' AND ex_title='" + title + "'";
            Cursor cursor = null;
            cursor = db.rawQuery(selectQuery, null);
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    do {
                        date_total = cursor.getString(0);
                    } while (cursor.moveToNext());
                }
            }
            cursor.close();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return date_total;
    }

    public String getMonthTotal(String month) {

        String date_total = "0";

        try {
            String selectQuery = "select sum(ex_amount) As amount from MyExpence where ex_date like '%-" + month + "-%' and ex_type = 0";
            Cursor cursor = null;
            cursor = db.rawQuery(selectQuery, null);
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    do {
                        date_total = cursor.getString(0);
                    } while (cursor.moveToNext());
                }
            }
            cursor.close();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return date_total;
    }

    public void getBody(String date) {

        try {
            String selectQuery = "SELECT * FROM MyExpence WHERE ex_date='" + date + "' ORDER BY ex_id DESC";
            Cursor cursor = null;
            cursor = db.rawQuery(selectQuery, null);
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    do {

                        //add in object
                        ExpListObj expListObj = new ExpListObj(cursor.getInt(0), cursor.getString(1),
                                cursor.getString(2), cursor.getInt(3), cursor.getInt(4), cursor.getString(5));

                        //add data
                        linkedList.add(expListObj);

                    } while (cursor.moveToNext());
                }
            }
            cursor.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getMonthBody(String month) {


        try {
            String selectQuery = "SELECT * FROM MyExpence WHERE ex_date like '%-" + month + "-%'";
            Cursor cursor = null;
            cursor = db.rawQuery(selectQuery, null);
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    do {
                        //add in object
                        ExpListObj expListObj = new ExpListObj(cursor.getInt(0), cursor.getString(1),
                                cursor.getString(2), cursor.getInt(3), cursor.getInt(4), cursor.getString(5));

                        //add data
                        linkedList.add(expListObj);

                    } while (cursor.moveToNext());
                }
            }
            cursor.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<String> getDate(int month) {

        ArrayList<String> arrayList = new ArrayList<>();

        try {
            //String selectQuery = "SELECT DISTINCT ex_date FROM MyExpence" ;
            //String selectQuery = "SELECT DISTINCT ex_date FROM MyExpence ORDER BY ex_date DESC";
            String selectQuery = "SELECT DISTINCT ex_date " +
                    "FROM MyExpence " +
                    "WHERE  ex_date like '%-" + month + "-%' " +
                    "ORDER BY ex_id DESC LIMIT 20";
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

    public LinkedList<Object> getData() {

        Calendar calender = Calendar.getInstance();
        int this_month = calender.get(Calendar.MONTH);
        int expense = getExpense(this_month + 1);
        int income = getIncome(this_month + 1);
        int balance = getBalance();

        if (balance < 0) {
            balance = 0;
        }

        //add data in ExpHeaderObj object
        ExpHeaderObj expHeaderObj = new ExpHeaderObj(expense, income, balance);

        //add in expHeaderObj
        linkedList.add(expHeaderObj);

        //get date arraylist
        ArrayList<String> arrayListDate = new ArrayList<>();

        //get dates
        arrayListDate = getDate(this_month + 1);

        for (String date : arrayListDate) {

            //add data in ExpDateObj object
            ExpDateObj expDateObj = new ExpDateObj(date, getDateTotal(date));

            //add in expDateObj
            linkedList.add(expDateObj);

            ////add in expenseList
            getBody(date);


        }


        return linkedList;
    }

    public ArrayList<SearchExpObj> getSearchData() {

        //init arraylist
        ArrayList<SearchExpObj> arrayList = new ArrayList<>();

        try {
            //String selectQuery = "SELECT DISTINCT ex_date FROM MyExpence" ;
            //String selectQuery = "SELECT DISTINCT ex_date FROM MyExpence ORDER BY ex_date DESC";
            String selectQuery = "SELECT ex_date,ex_note,ex_title FROM MyExpence";
            Cursor cursor = null;
            cursor = db.rawQuery(selectQuery, null);
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    do {
                        //add data
                        SearchExpObj searchExpObj = new SearchExpObj(getDateExpense(cursor.getString(0),
                                cursor.getString(2)), cursor.getString(0), cursor.getString(1), cursor.getString(2));
                        arrayList.add(searchExpObj);
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

    public LinkedList<Object> getTodaysData(String date) {

        //add data in ExpDateObj object
        ExpDateObj expDateObj = new ExpDateObj(date, getDateTotal(date));

        //add in expDateObj
        linkedList.add(expDateObj);

        ///add in expenseList
        getBody(date);

        return linkedList;
    }

    public LinkedList<Object> getMonthData(int month) {

        int expense = getExpense(month);
        int income = getIncome(month);


        //add data in ExpHeaderObj object
        ExpActivityHeaderObj expHeaderObj = new ExpActivityHeaderObj(income, expense);

        //add in expHeaderObj
        linkedList.add(expHeaderObj);

        //get date arraylist
        ArrayList<String> arrayListDate = new ArrayList<>();

        //get dates
        arrayListDate = getDate(month);

        for (String date : arrayListDate) {

            //add data in ExpDateObj object
            ExpDateObj expDateObj = new ExpDateObj(date, getDateTotal(date));

            //add in expDateObj
            linkedList.add(expDateObj);

            ////add in expenseList
            getBody(date);

        }

        return linkedList;
    }

    public void updateExpense(int id, String amount, String source, String note, String date, int ex_type) {


        String strSQL = "UPDATE MyExpence SET ex_amount ='" + amount + "', ex_title ='" + source + "',ex_note ='" + note + "',ex_date ='" + date + "' WHERE ex_id = " + id;

        db.execSQL(strSQL);
    }


    public void deleteTask(int id, int ex_type, int amount) {


        String strSQL = "Delete from MyExpence WHERE ex_id = " + id;

        db.execSQL(strSQL);


    }

    public void deleteData() {

        String strSQLExp = "Delete from MyExpence";
        db.execSQL(strSQLExp);

        String strSQLTask = "Delete from ToDoTask";
        db.execSQL(strSQLTask);
    }

    public String exportDB() {

        String savedFile = null;
        try {
            Cursor c = db.rawQuery("select ex_title,ex_amount,ex_type,ex_date from MyExpence", null);
            int rowcount = 0;
            int colcount = 0;

            // the name of the file to export with
            File saveFile = CommonUtils.getOutputMediaFile(APP_DIRECTORY_NAME);
            FileWriter fw = new FileWriter(saveFile);

            BufferedWriter bw = new BufferedWriter(fw);
            rowcount = c.getCount();
            colcount = c.getColumnCount();
            if (rowcount > 0) {
                c.moveToFirst();

                for (int i = 0; i < colcount; i++) {
                    if (i != colcount - 1) {

                        bw.write(c.getColumnName(i) + ",");

                    } else {

                        bw.write(c.getColumnName(i));

                    }
                }
                bw.newLine();

                for (int i = 0; i < rowcount; i++) {
                    c.moveToPosition(i);

                    for (int j = 0; j < colcount; j++) {
                        if (j != colcount - 1)
                            bw.write(c.getString(j) + ",");
                        else
                            bw.write(c.getString(j));
                    }
                    bw.newLine();
                }
                bw.flush();
                Log.e("TAG", "Exported Successfully.");
            }

            return saveFile.toString();

        } catch (Exception ex) {
            if (db.isOpen()) {
                db.close();
                //Log.e("TAG", ex.getMessage().toString());
            }

        } finally {

        }

        return savedFile;
    }


}
