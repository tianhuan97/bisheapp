package com.example.demo_10;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.sql.SQLClientInfoException;

/**
 * Created by TianHuan on 2017/11/25.
 */
public class CreatDb extends SQLiteOpenHelper {
    private static final String DB_NAME = "data.db";
    private static final int VERSION = 2;

    public CreatDb(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i("sqlite数据库","onCreate()");
        boolean bol = false;
        db.execSQL("create table userinfo (id integer primary key autoincrement,username varchar(10))");
        bol = true;
        Log.i("sqlite数据库","状态"+bol);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i("sqlite数据库","onUpgrade");
        boolean bol = false;
        db.execSQL("alter table userinfo add password varchar(10)");
        bol = true;
        Log.i("sqlite数据库","更新状态"+bol);
    }
}
