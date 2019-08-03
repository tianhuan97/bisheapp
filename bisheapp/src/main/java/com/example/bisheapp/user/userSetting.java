package com.example.bisheapp.user;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bisheapp.IpUtils;
import com.example.bisheapp.model.user;

import com.example.bisheapp.R;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class userSetting extends AppCompatActivity {
    private TextView usid;
    private EditText usname, uspassword, ussex, usage, usaddress, usjName, usjNumber;
    private user user;
    private Button usButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_setting);

        Intent intent = getIntent();
        String json = intent.getStringExtra("json");
        Gson gson = new Gson();
        user = gson.fromJson(json, com.example.bisheapp.model.user.class);

        usid = (TextView) findViewById(R.id.usid);
        usname = (EditText) findViewById(R.id.usname);
        uspassword = (EditText) findViewById(R.id.uspassword);
        ussex = (EditText) findViewById(R.id.ussex);
        usage = (EditText) findViewById(R.id.usage);
        usaddress = (EditText) findViewById(R.id.usaddress);
        usjName = (EditText) findViewById(R.id.usjName);
        usjNumber = (EditText) findViewById(R.id.usjNumber);
        usButton = (Button) findViewById(R.id.usButton);

        usid.setText("用户名：" + user.getId());
        usname.setHint("姓名 " + user.getName());
        uspassword.setHint("密码 " + user.getPassword());
        ussex.setHint("性别 " + user.getSex());
        usaddress.setHint("地址 " + user.getAddress());
        usage.setHint("年龄 " + user.getAge());
        usjName.setHint("紧急联系人姓名 " + user.getjName());
        usjNumber.setHint("紧急联系人电话 " + user.getjNumber());

        usButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MyTask().execute(IpUtils.USERSETTING_PATH);
            }
        });
    }

    class MyTask extends AsyncTask<String, Void, Integer> {
        String id, name, password, sex, age, address, jName, jNumber;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            id = user.getId();
            name = usname.getText().toString().trim();
            password = uspassword.getText().toString().trim();
            sex = ussex.getText().toString().trim();
            age = usage.getText().toString().trim();
            address = usaddress.getText().toString().trim();
            jName = usjName.getText().toString().trim();
            jNumber = usjNumber.getText().toString().trim();
        }

        @Override
        protected Integer doInBackground(String... params) {
            try {
                HttpURLConnection hc = (HttpURLConnection) new URL(params[0]).openConnection();
                hc.setRequestMethod("POST");
                hc.setReadTimeout(5000);
                user user1 = new user();
                user1.setId(id);
                user1.setName(name);
                user1.setPassword(password);
                user1.setSex(sex);
                user1.setAge(age);
                user1.setAddress(address);
                user1.setjName(jName);
                user1.setjNumber(jNumber);
                Gson gson = new Gson();
                String json = gson.toJson(user1);
                String value = "json=" + json;
                hc.getOutputStream().write(value.getBytes());

                BufferedReader br = new BufferedReader(new InputStreamReader(hc.getInputStream()));
                String str = "";
                StringBuffer sf = new StringBuffer();
                while ((str = br.readLine()) != null){
                    sf.append(str);
                }
                return Integer.valueOf(sf.toString());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            if(integer == 1){
                Toast.makeText(userSetting.this, "修改成功", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
