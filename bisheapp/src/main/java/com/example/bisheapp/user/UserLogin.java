package com.example.bisheapp.user;

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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class UserLogin extends AppCompatActivity {
    private Button ulogin, uregist;
    private EditText ulid, ulpassword;

    private ProgressDialog progressDialog;//进度条

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);

        progressDialog = new ProgressDialog(UserLogin.this);
        progressDialog.setMessage("请等待...");
        progressDialog.setIcon(R.drawable.image1);
        progressDialog.setTitle("正在登陆");

        ulogin = (Button) findViewById(R.id.ulogin);
        uregist = (Button) findViewById(R.id.uregist);

        ulid = (EditText) findViewById(R.id.ulid);
        ulpassword = (EditText) findViewById(R.id.ulpassword);

        //跳转到用户注册界面
        uregist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserLogin.this, UserRegister.class);
                startActivity(intent);
            }
        });

        class MyTask extends AsyncTask<String, Void, String> {
            String id;
            String password;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog.show();
                id = ulid.getText().toString().trim();
                password = ulpassword.getText().toString().trim();
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
                } catch (ProtocolException e) {
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
                    Toast.makeText(UserLogin.this, "密码必须小于16位", Toast.LENGTH_SHORT).show();
                }
                if (s.equals("登录成功")) {
                    Log.i("异步任务",""+s);
                    Toast.makeText(UserLogin.this, "登录成功", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(UserLogin.this, User.class);
                    intent.putExtra("userId",id);
                    startActivity(intent);
                } else if (s.equals("该用户不存在")) {
                    Log.i("异步任务",""+s);
                    Toast.makeText(UserLogin.this, "该用户名不存在", Toast.LENGTH_SHORT).show();
                } else if (s.equals("密码错误")) {
                    Log.i("异步任务",""+s);
                    Toast.makeText(UserLogin.this, "密码错误", Toast.LENGTH_SHORT).show();
                }
                progressDialog.dismiss();
            }
        }

        //获取登录信息，跳转到用户界面
        //使用异步任务
        //首先使用假数据进行测试
/*
        ulogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = ulid.getText().toString().trim();
                String password = ulpassword.getText().toString().trim();
                if (password.length() > 16)
                    Toast.makeText(UserLogin.this, "密码必须小于16位", Toast.LENGTH_SHORT).show();
                if ("admin".equals(id) && "123456".equals(password)) {
                    try {
                        progressDialog.show();
                        Thread.sleep(2000);
                        Intent intent = new Intent(UserLogin.this, User.class);
                        startActivity(intent);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(UserLogin.this, "用户名或密码错误", Toast.LENGTH_SHORT).show();
                }
            }
        });
*/
        ulogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MyTask().execute(IpUtils.USERLOGIN_PATH);
            }
        });

    }
}
