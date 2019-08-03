package com.example.bisheapp.volunteer;

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
import com.example.bisheapp.model.volunteer;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class vtMessage extends AppCompatActivity {
    private TextView vmid,vmpassword,vmname,vmsex,vmtype;
    private Button vmsetting;
    private ProgressDialog progressDialog;
    private String id;
    private volunteer vt1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vt_message);

        vmid = (TextView) findViewById(R.id.vmid);
        vmpassword = (TextView) findViewById(R.id.vmpassword);
        vmname = (TextView) findViewById(R.id.vmname);
        vmsex = (TextView) findViewById(R.id.vmsex);
        vmtype = (TextView) findViewById(R.id.vmtype);
        vmsetting = (Button) findViewById(R.id.vmsetting);

        Intent intent = getIntent();
        id = intent.getStringExtra("vtId");

        progressDialog = new ProgressDialog(vtMessage.this);
        progressDialog.setMessage("请等待...");
        progressDialog.setIcon(R.drawable.image1);
        progressDialog.setTitle("正在加载");

        new MyTask().execute(IpUtils.VTMESSAGE_PATH);
        vmsetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Gson gson = new Gson();
                String json = gson.toJson(vt1);
                Intent intent1 = new Intent(vtMessage.this,vtSetting.class);
                intent1.putExtra("json",json);
                startActivity(intent1);
            }
        });

    }
    class MyTask extends AsyncTask<String,Volunteer,volunteer>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.show();
        }

        @Override
        protected volunteer doInBackground(String... params) {
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
                volunteer vt = gson.fromJson(json, com.example.bisheapp.model.volunteer.class);
                return vt;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(volunteer volunteer) {
            super.onPostExecute(volunteer);
            vt1 = volunteer;
            vmid.setText("用户名： "+id);
            vmpassword.setText("密码： "+volunteer.getPassword());
            vmname.setText("姓名: "+volunteer.getName());
            vmsex.setText("性别： "+volunteer.getSex());
            vmtype.setText("服务种类： "+volunteer.getType());
            progressDialog.dismiss();
        }
    }
}
