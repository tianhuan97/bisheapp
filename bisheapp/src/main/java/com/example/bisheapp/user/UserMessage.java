package com.example.bisheapp.user;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.bisheapp.IpUtils;
import com.example.bisheapp.R;
import com.example.bisheapp.model.user;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class UserMessage extends AppCompatActivity {
    private TextView umid, umname, umpassword, umsex, umage, umaddress, umjName, umjNumber;
    private Button umsetting;
    private ProgressDialog progressDialog;
    private String id;
    private user user1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_message);

        umid = (TextView) findViewById(R.id.umid);
        umname = (TextView) findViewById(R.id.umname);
        umpassword = (TextView) findViewById(R.id.umpassword);
        umsex = (TextView) findViewById(R.id.umsex);
        umage = (TextView) findViewById(R.id.umage);
        umaddress = (TextView) findViewById(R.id.umaddress);
        umjName = (TextView) findViewById(R.id.umjName);
        umjNumber = (TextView) findViewById(R.id.umjNumber);

        umsetting = (Button) findViewById(R.id.umsetting);

        final Intent intent = getIntent();
        id = intent.getStringExtra("userId");

        progressDialog = new ProgressDialog(UserMessage.this);
        progressDialog.setMessage("请等待...");
        progressDialog.setIcon(R.drawable.image1);
        progressDialog.setTitle("正在加载");

        new MyTask().execute(IpUtils.USERMESSAGE_PATH);

        umsetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Gson gson = new Gson();
                String json = gson.toJson(user1);
                Intent intent1 = new Intent(UserMessage.this,userSetting.class);
                intent1.putExtra("json",json);
                startActivity(intent1);
            }
        });
    }

    class MyTask extends AsyncTask<String, Void, user> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.show();
        }

        @Override
        protected user doInBackground(String... params) {
            try {
                HttpURLConnection hc = (HttpURLConnection) new URL(params[0]).openConnection();
                hc.setRequestMethod("POST");
                hc.setReadTimeout(5000);
                String value = "id=" + id;
                hc.getOutputStream().write(value.getBytes());

                BufferedReader br = new BufferedReader(new InputStreamReader(hc.getInputStream()));
                String str = "";
                StringBuffer sf = new StringBuffer();
                while ((str = br.readLine()) != null) {
                    sf.append(str);
                }
                String json = sf.toString();
                Gson gson = new Gson();
                user user = gson.fromJson(json, com.example.bisheapp.model.user.class);
                return user;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(user user) {
            super.onPostExecute(user);
            user1 = user;
            umid.setText("用户名： " + id);
            umname.setText("姓名： " + user.getName());
            umpassword.setText("密码： " + user.getPassword());
            umsex.setText("性别： " + user.getSex());
            umage.setText("年龄： " + user.getAge());
            umaddress.setText("地址： " + user.getAddress());
            umjName.setText("紧急联系人姓名： " + user.getjName());
            umjNumber.setText("紧急联系人电话： " + user.getjNumber());
            progressDialog.dismiss();
        }
    }
}
