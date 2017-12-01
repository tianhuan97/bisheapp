package com.example.demo_10;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button createDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createDatabase = (Button) findViewById(R.id.createDatabase);
        createDatabase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreatDb db = new CreatDb(MainActivity.this);
                SQLiteDatabase sqLiteDatabase = db.getReadableDatabase();
                sqLiteDatabase.execSQL("insert into userinfo (username) values (?)");
            }
        });
    }
}
