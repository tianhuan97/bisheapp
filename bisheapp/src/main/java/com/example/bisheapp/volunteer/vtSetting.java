package com.example.bisheapp.volunteer;

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
import com.example.bisheapp.R;
import com.example.bisheapp.model.volunteer;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class vtSetting extends AppCompatActivity {
    private TextView vsid;
    private EditText vsname,vspassword,vssex,vstype;
    private volunteer vt;
    private Button vsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vt_setting);

        Intent intent = getIntent();
        String json = intent.getStringExtra("json");
        Gson gson = new Gson();
        vt = gson.fromJson(json,volunteer.class);

        vsid = (TextView) findViewById(R.id.vsid);
        vsname = (EditText) findViewById(R.id.vsname);
        vspassword = (EditText) findViewById(R.id.vspassword);
        vssex = (EditText) findViewById(R.id.vssex);
        vstype = (EditText) findViewById(R.id.vstype);
        vsButton = (Button) findViewById(R.id.vsButton);

        vsid.setText("用户名： "+vt.getId());
        vsname.setHint("姓名 "+vt.getName());
        vspassword.setHint("密码 "+vt.getPassword());
        vssex.setHint("性别 "+vt.getSex());
        vstype.setHint("服务类别 "+vt.getType());

        vsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MyTask().execute(IpUtils.VTSETTING_PATH);
            }
        });
    }
    class MyTask extends AsyncTask<String,Void,Integer>{
        String id,name,password,sex,type;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            id = vt.getId();
            name = vsname.getText().toString().trim();
            password = vspassword.getText().toString().trim();
            sex = vssex.getText().toString().trim();
            type = vstype.getText().toString().trim();
        }

        @Override
        protected Integer doInBackground(String... params) {
            try {
                HttpURLConnection hc = (HttpURLConnection) new URL(params[0]).openConnection();
                hc.setRequestMethod("POST");
                hc.setReadTimeout(5000);
                volunteer vt1 = new volunteer();
                vt1.setId(id);
                vt1.setName(name);
                vt1.setPassword(password);
                vt1.setSex(sex);
                vt1.setType(type);
                Gson gson = new Gson();
                String json1 = gson.toJson(vt1);
                String value = "json="+json1;
                hc.getOutputStream().write(value.getBytes());

                BufferedReader br = new BufferedReader(new InputStreamReader(hc.getInputStream()));
                String str = "";
                StringBuffer sf = new StringBuffer();
                while ((str = br.readLine()) != null){
                    sf.append(str);
                }
                return Integer.valueOf(sf.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            if(integer == 1){
                Toast.makeText(vtSetting.this, "修改成功", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
