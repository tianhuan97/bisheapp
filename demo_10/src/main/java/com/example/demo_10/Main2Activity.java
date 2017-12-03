package com.example.demo_10;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class Main2Activity extends AppCompatActivity {
    private Button insertDataByProvider,queryDataByProvider;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        insertDataByProvider = (Button) findViewById(R.id.insertDataByProvider);
        queryDataByProvider = (Button) findViewById(R.id.queryDataByProvider);
        insertDataByProvider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uri = "content://com.example.demo_10.MyContentProvider/userinfo";
                ContentResolver cr = getContentResolver();
                ContentValues contentValues = new ContentValues();
                contentValues.put("username","大黄蜂");
                contentValues.put("password","擎天柱小弟");
                Uri newUri = cr.insert(Uri.parse(uri),contentValues);
                Log.i("内容提供者","@@@"+newUri);
            }
        });

        queryDataByProvider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uri = "content://com.example.demo_10.MyContentProvider/userinfo";
                ContentResolver cr = getContentResolver();
                Cursor cursor = cr.query(Uri.parse(uri),new String[]{"username","password"},null,null,null);
                while (cursor.moveToNext()){
                    String name = cursor.getString(cursor.getColumnIndex("username"));
                    String password = cursor.getString(cursor.getColumnIndex("password"));
                    Log.i("内容提供者",name+"@@@"+password);
                }
            }
        });
    }
}
