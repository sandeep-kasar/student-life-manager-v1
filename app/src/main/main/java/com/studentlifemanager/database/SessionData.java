package com.studentlifemanager.database;

import android.content.Context;
import android.content.SharedPreferences;


public class SessionData {

    //class
    Context context;
    SharedPreferences pref;

    //static var
    static final String PREF_NAME = "appdata";

    //store values in shared preferences as session data

    public SessionData(Context context) {
        this.context = context;
        pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public boolean add(String name, String value) {
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(name, value);
        return editor.commit();
    }

    public boolean add(String name, int value) {
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt(name, value);
        return editor.commit();
    }

    public boolean add(String name, long value) {
        SharedPreferences.Editor editor = pref.edit();
        editor.putLong(name, value);
        return editor.commit();
    }

    public String getString(String name, String defaultValue) {
        return pref.getString(name, defaultValue);
    }

    public int getInt(String name, int defaultValue) {
        return pref.getInt(name, defaultValue);
    }

    public long getLong(String name, long defaultValue) {
        return Long.parseLong(getString(name, "" + defaultValue));
    }

    public long getLongEx(String name, long defaultValue) {
        return pref.getLong(name, defaultValue);
    }

    public boolean delete(String name) {
        SharedPreferences.Editor editor = pref.edit();
        editor.remove(name);
        return editor.commit();
    }

    public void clearSession() {
        SharedPreferences.Editor editor = pref.edit();
        editor.clear();
        editor.commit();
    }

    public void remove(String name) {
        SharedPreferences.Editor editor = pref.edit();
        editor.remove("name");
        editor.commit();
    }
}
