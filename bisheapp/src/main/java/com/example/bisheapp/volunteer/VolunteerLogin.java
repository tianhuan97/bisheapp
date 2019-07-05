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
import com.example.bisheapp.user.User;
import com.example.bisheapp.user.UserLogin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class VolunteerLogin extends AppCompatActivity {
    private Button vlogin,vregist;
    private EditText vlusername,vlpassword;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volunteer_login);

        progressDialog = new ProgressDialog(VolunteerLogin.this);
        progressDialog.setMessage("请等待...");
        progressDialog.setIcon(R.drawable.image1);
        progressDialog.setTitle("正在登陆");

        vlogin = (Button)findViewById(R.id.vlogin);
        vregist = (Button)findViewById(R.id.vregist);

        vlusername = (EditText)findViewById(R.id.vlusername);
        vlpassword = (EditText)findViewById(R.id.vlpassword);

        vregist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VolunteerLogin.this,VolunteerRegister.class);
                startActivity(intent);
            }
        });

        class MyTask extends AsyncTask<String,Void,String>{
            String id;
            String password;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog.show();
                id = vlusername.getText().toString().trim();
                password = vlpassword.getText().toString().trim();
            }

            @Override
            protected String doInBackground(String... params) {
                try {
                    Thread.sleep(100);
                    HttpURLConnection hc = (HttpURLConnection) new URL(params[0]).openConnection();
                    hc.setRequestMethod("POST");
                    hc.setReadTimeout(5000);
                    //传值
                    String value = "id=" + id + "&password=" + password;
                    hc.getOutputStream().write(value.getBytes());

                    //接值
                    BufferedReader br = new BufferedReader(new InputStreamReader(hc.getInputStream()));
                    String str = "";
                    StringBuffer sf = new StringBuffer();
                    while ((str = br.readLine()) != null){
                        sf.append(str);
                    }
                    return sf.toString();

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                if (password.length() > 16) {
                    Log.i("异步任务",""+s);
                    Toast.makeText(VolunteerLogin.this, "密码必须小于16位", Toast.LENGTH_SHORT).show();
                }
                if (s.equals("登录成功")) {
                    Log.i("异步任务",""+s);
                    Toast.makeText(VolunteerLogin.this, "登录成功", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(VolunteerLogin.this,Volunteer.class);
                    intent.putExtra("vtId",id);
                    startActivity(intent);
                } else if (s.equals("该用户不存在")) {
                    Log.i("异步任务",""+s);
                    Toast.makeText(VolunteerLogin.this, "该用户名不存在", Toast.LENGTH_SHORT).show();
                } else if (s.equals("密码错误")) {
                    Log.i("异步任务",""+s);
                    Toast.makeText(VolunteerLogin.this, "密码错误", Toast.LENGTH_SHORT).show();
                }
                progressDialog.dismiss();
            }
        }

        vlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //启动异步任务，连接服务器获取数据
                new MyTask().execute(IpUtils.VTLOGIN_PATH);
            }
        });
    }
}
