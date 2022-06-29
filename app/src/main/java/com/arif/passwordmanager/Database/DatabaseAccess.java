package com.arif.passwordmanager.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;

public class DatabaseAccess {
    private static DatabaseAccess instance;
    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase database;

    private DatabaseAccess(Context context) {
        this.openHelper = new DatabaseOpenHelper(context);
    }

    public static DatabaseAccess getInstance(Context context) {

        if (instance == null) {
            instance = new DatabaseAccess(context);
        }
        return instance;
    }


    public void open() {
        this.database = openHelper.getWritableDatabase();
    }


    public void close() {
        if (database != null) {
            this.database.close();
        }
    }


    public boolean signUpUser(String userFullName, String email, String birthdayDate, String password) {

        ContentValues values = new ContentValues();


        values.put(Constant.NAME, userFullName);
        values.put(Constant.EMAIL, email);
        values.put(Constant.BIRTH_DATE, birthdayDate);
        values.put(Constant.PASSWORD, password);


        long check = database.insert("signup", null, values);

        //if data insert success, its return 1, if failed return -1
        if (check == -1) {
            return false;
        } else {
            return true;
        }
    }

    public boolean addUserData(String applicationName, String applicationUrl, String userEmail, String password, String others) {

        ContentValues values = new ContentValues();


        values.put(Constant.APPLICATION_NAME, applicationName);
        values.put(Constant.APPLICATION_URL, applicationUrl);
        values.put(Constant.USERNAME, userEmail);
        values.put(Constant.PASSWORD, password);
        values.put(Constant.OTHERS, others);


        long check = database.insert("userdata", null, values);

        //if data insert success, its return 1, if failed return -1
        if (check == -1) {
            return false;
        } else {
            return true;
        }
    }


    public ArrayList<HashMap<String, String>> getUserData() {

        ArrayList<HashMap<String, String>> user = new ArrayList<>();

        Cursor cursor = database.rawQuery("SELECT * FROM userdata", null);


        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> map = new HashMap<>();

                map.put(Constant.ID, cursor.getString(0));
                map.put(Constant.APPLICATION_NAME, cursor.getString(1));
                map.put(Constant.APPLICATION_URL, cursor.getString(2));
                map.put(Constant.USERNAME, cursor.getString(3));
                map.put(Constant.PASSWORD, cursor.getString(4));
                map.put(Constant.OTHERS, cursor.getString(5));
                user.add(map);

            } while (cursor.moveToNext());
        }

        cursor.close();
        database.close();
        return user;
    }


    public boolean updateData(String id, String applicationName, String applicationUrl, String userEmail, String password, String others) {

        ContentValues values = new ContentValues();

        values.put(Constant.APPLICATION_NAME, applicationName);
        values.put(Constant.APPLICATION_URL, applicationUrl);
        values.put(Constant.USERNAME, userEmail);
        values.put(Constant.PASSWORD, password);
        values.put(Constant.OTHERS, others);

        long check = database.update("userdata", values, "id=?", new String[]{id});

        //if data insert success, its return 1, if failed return -1
        if (check == -1) {
            return false;
        } else {
            return true;
        }
    }


    public boolean deleteData(String id) {


        long check = database.delete("userdata", "id=?", new String[]{id});

        database.close();

        if (check == 1) {
            return true;
        } else {
            return false;
        }

    }


    public String getUserAvailable() {

        String user = "";
        Cursor cursor = database.rawQuery("SELECT * FROM signup", null);


        if (cursor.moveToFirst()) {
            do {
                user = cursor.getString(0);

            } while (cursor.moveToNext());
        }

        cursor.close();
        database.close();
        return user;
    }

    public HashMap<String, String> checkLogin() {

        HashMap<String, String> user = new HashMap<>();
        Cursor cursor = database.rawQuery("SELECT * FROM signup", null);


        if (cursor.moveToFirst()) {
            do {
                user.put(Constant.NAME, cursor.getString(1));
                user.put(Constant.PASSWORD, cursor.getString(3));

            } while (cursor.moveToNext());
        }

        cursor.close();
        database.close();
        return user;
    }

    public HashMap<String, String> getProfileData() {

        HashMap<String, String> user = new HashMap<>();
        Cursor cursor = database.rawQuery("SELECT * FROM signup", null);


        if (cursor.moveToFirst()) {
            do {
                user.put(Constant.NAME, cursor.getString(0));
                user.put(Constant.EMAIL, cursor.getString(1));
                user.put(Constant.BIRTH_DATE, cursor.getString(2));
                user.put(Constant.PASSWORD, cursor.getString(3));

            } while (cursor.moveToNext());
        }

        cursor.close();
        database.close();
        return user;
    }


    public void updatePassword(String email, String password) {

        database.execSQL("UPDATE signup SET " + Constant.PASSWORD + " = '" + password + "' WHERE " + Constant.EMAIL + "='" + email + "'");
    }
}
