package com.example.am.mis;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDatabaseHelper extends SQLiteOpenHelper {

    public static final String CREATE_TABLE1 = "create table person (" +
            "_id integer primary key autoincrement," +
            "name text," +
            "num text," +
            "sex text," +
            "salary text)";
    public static final String CREATE_TABLE2 = "create table history (" +
            "_id integer primary key autoincrement," +
            "temp text)";

    private Context mContext;

    public MyDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE1);
        db.execSQL(CREATE_TABLE2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
