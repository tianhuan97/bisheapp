package com.example.bisheapp.volunteer;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.bisheapp.IpUtils;
import com.example.bisheapp.R;
import com.example.bisheapp.model.volunteer;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class VolunteerRegister extends AppCompatActivity {
    private EditText vid, vpassword, vpassword1, vsex;
    private Button vregister;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volunteer_register);

        progressDialog = new ProgressDialog(VolunteerRegister.this);
        progressDialog.setMessage("请等待...");
        progressDialog.setIcon(R.drawable.image1);
        progressDialog.setTitle("返回登录界面");

        vid = (EditText)findViewById(R.id.vid);
        vpassword = (EditText)findViewById(R.id.vpassword);
        vpassword1 = (EditText)findViewById(R.id.vpassword1);
        vsex = (EditText)findViewById(R.id.vsex);

        vregister = (Button)findViewById(R.id.vRegister);

        //使用异步任务，确认数据无误后，实现操作，返回登录界面

        class MyTask extends AsyncTask<String,Void,String>{
            String id,password1,password,sex;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog.show();
                id = vid.getText().toString().trim();
                password = vpassword.getText().toString().trim();
                password1 = vpassword1.getText().toString().trim();
                sex = vsex.getText().toString().trim();

            }

            @Override
            protected String doInBackground(String... params) {
                try {
                    HttpURLConnection hc = (HttpURLConnection) new URL(params[0]).openConnection();
                    hc.setRequestMethod("POST");
                    hc.setReadTimeout(5000);
                    String value = "id=" + id + "&password=" + password + "&sex=" + sex;
                    hc.getOutputStream().write(value.getBytes());

                    BufferedReader br = new BufferedReader(new InputStreamReader(hc.getInputStream()));
                    String str = "";
                    StringBuffer sf = new StringBuffer();
                    while ((str = br.readLine()) != null){
                        sf.append(str);
                    }
                    return sf.toString();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                return null;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                progressDialog.dismiss();
                if(!(password1.equals(password))){
                    Toast.makeText(VolunteerRegister.this, "两次的密码不一致", Toast.LENGTH_SHORT).show();
                }else if(s.equals("注册成功")){
                    Log.i("志愿者注册异步任务",""+id);
                    Toast.makeText(VolunteerRegister.this, "注册成功", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(VolunteerRegister.this,VolunteerLogin.class));
                }
            }
        }

        vregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MyTask().execute(IpUtils.VTREGISTER_PATH);
            }
        });

        /*vregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password = vpassword.getText().toString().trim();
                String password1 = vpassword1.getText().toString().trim();
                if(!password.equals(password1)){
                    Toast.makeText(VolunteerRegister.this, "该用户已存在", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(VolunteerRegister.this, "注册成功", Toast.LENGTH_SHORT).show();
                    progressDialog.show();
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    startActivity(new Intent(VolunteerRegister.this,Volunteer.class));
                }
            }
        });*/
    }
}
