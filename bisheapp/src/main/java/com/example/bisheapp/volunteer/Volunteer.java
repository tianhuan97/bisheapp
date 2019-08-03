package com.example.bisheapp.volunteer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.example.bisheapp.R;
import com.example.bisheapp.elderHouse;
import com.example.bisheapp.user.UserList;

public class Volunteer extends AppCompatActivity {
    private LinearLayout vusermessage,vmessage,volderhouse;
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volunteer);

        final Intent intent = getIntent();
        id = intent.getStringExtra("vtId");



        vusermessage = (LinearLayout) findViewById(R.id.vusermessage);
        vmessage = (LinearLayout) findViewById(R.id.vmessage);
        volderhouse = (LinearLayout) findViewById(R.id.volderhouse);

        volderhouse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Volunteer.this,elderHouse.class));
            }
        });

        vmessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(Volunteer.this,vtMessage.class);
                intent1.putExtra("vtId",id);
                startActivity(intent1);
            }
        });

        vusermessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Volunteer.this, UserList.class));
            }
        });
    }
}
