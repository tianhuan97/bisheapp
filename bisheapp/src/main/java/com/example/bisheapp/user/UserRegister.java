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
import com.example.bisheapp.model.user;
import com.google.gson.Gson;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class UserRegister extends AppCompatActivity {
    private EditText urid, urname, urpassword, urpassword1, ursex, urage, uraddress;
    private Button uregisterButton;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_register);

        progressDialog = new ProgressDialog(UserRegister.this);
        progressDialog.setMessage("请等待...");
        progressDialog.setIcon(R.drawable.image1);
        progressDialog.setTitle("返回登录界面");

        urid = (EditText) findViewById(R.id.urid);
        urname = (EditText) findViewById(R.id.urname);
        urpassword = (EditText) findViewById(R.id.urpassword);
        urpassword1 = (EditText) findViewById(R.id.urpassword1);
        ursex = (EditText) findViewById(R.id.ursex);
        urage = (EditText) findViewById(R.id.urage);
        uraddress = (EditText) findViewById(R.id.uraddress);

        uregisterButton = (Button) findViewById(R.id.uregisterButton);

        //使用异步任务，查看数据是否合理
        //如数据合理，注册到数据库中，返回用户登录界面


        class MyTask extends AsyncTask<String, Void, String> {
            String id, name, password, password1, sex, address, age;
            List<user> list = new ArrayList<>();

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                id = urid.getText().toString().trim();
                name = urname.getText().toString().trim();
                password = urpassword.getText().toString().trim();
                password1 = urpassword1.getText().toString().trim();
                sex = ursex.getText().toString().trim();
                address = uraddress.getText().toString().trim();
                age = urage.getText().toString().trim();
                progressDialog.show();
            }

            @Override
            protected String doInBackground(String... params) {
                try {
                    HttpURLConnection hc = (HttpURLConnection) new URL(params[0]).openConnection();
                    hc.setRequestMethod("POST");
                    hc.setReadTimeout(5000);
                    String value = "id=" + id + "&name=" + name + "&password=" + password + "&sex=" + sex + "&address=" + address + "&age=" + age;
                    if (password.equals(password1)) {
                        hc.getOutputStream().write(value.getBytes());
                    }

                    BufferedReader br = new BufferedReader(new InputStreamReader(hc.getInputStream()));
                    String str = "";
                    StringBuffer sf = new StringBuffer();
                    while ((str = br.readLine()) != null) {
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
                if (s.equals("该用户已存在")) {
                    Log.i("用户注册异步任务", "" + s);
                    Toast.makeText(UserRegister.this, s, Toast.LENGTH_SHORT).show();
                } else if (!(password.equals(password1))) {
                    Log.i("异步任务", "" + "密码不和规范");
                    Toast.makeText(UserRegister.this, "两次输入的密码必须相同", Toast.LENGTH_SHORT).show();
                } else {
                    Log.i("异步任务", "" + s);
                    Toast.makeText(UserRegister.this, s, Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(UserRegister.this, UserLogin.class));
                }
                progressDialog.dismiss();
            }
        }

        uregisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MyTask().execute(IpUtils.USERREGISTER_PATH);
            }
        });

    }
}
