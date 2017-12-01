package com.example.demo_01;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Button loginButton;
    private EditText username,password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loginButton = (Button) findViewById(R.id.login);
        username = (EditText)findViewById(R.id.username);
        password = (EditText)findViewById(R.id.password);
        loginButton.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View v){
                String name = username.getText().toString().trim();
                String psd = password.getText().toString().trim();
               /* if("admin".equals(name) && "admin".equals(psd)){
                    Toast.makeText(One.this, "登录成功!", Toast.LENGTH_SHORT).show();
                    Toast.makeText(One.this, "登录失败!", Toast.LENGTH_SHORT).show();
                }*/
            }

        });

    }
}
