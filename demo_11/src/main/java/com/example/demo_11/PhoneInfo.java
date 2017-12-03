package com.example.demo_11;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by TianHuan on 2017/12/1.
 */
public class PhoneInfo {
    private String name;
    private String number;
    public PhoneInfo(String name, String number){
        this.name = name;
        this.number = number;
    }
    public String getName(){
        return name;
    }
    public String getNumber(){
        return number;
    }
}
