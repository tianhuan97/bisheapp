package com.example.demo_10;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by TianHuan on 2017/12/2.
 */
public class MyContentProvider extends ContentProvider {

    private static final UriMatcher URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);
    private CreatDb creatDb;

    static {
        URI_MATCHER.addURI("com.example.demo_10.MyContentProvider","userinfo",1);
        URI_MATCHER.addURI("com.example.demo_10.MyContentProvider","userinfo/#",2);
        URI_MATCHER.addURI("com.example.demo_10.MyContentProvider","userinfo/#/*",3);
    }

    @Override
    public boolean onCreate() {
        creatDb = new CreatDb(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        int i = URI_MATCHER.match(uri);
        switch (i){
            case 1:
                SQLiteDatabase sqLiteDatabase = creatDb.getReadableDatabase();
                Cursor cursor = sqLiteDatabase.query("userinfo",projection,selection,selectionArgs,null,null,sortOrder);
                return cursor;
        }
        return null;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        Log.i("内容提供者","@@@"+uri);
        int i = URI_MATCHER.match(uri);
        Log.i("内容提供者","@@@"+i);
        switch (i){
            case 1:
                SQLiteDatabase sqLiteDatabase = creatDb.getReadableDatabase();
                long id = sqLiteDatabase.insert("userinfo",null,values);
                Uri newUri = ContentUris.withAppendedId(uri,id);
                return newUri;
            case 2:
                break;
            case 3:
                break;

        }
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}
