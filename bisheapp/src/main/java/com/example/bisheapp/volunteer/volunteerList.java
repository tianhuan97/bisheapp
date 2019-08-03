package com.example.bisheapp.volunteer;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.bisheapp.IpUtils;
import com.example.bisheapp.R;
import com.example.bisheapp.model.volunteer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class volunteerList extends AppCompatActivity {
    private ListView vtList;
    private ProgressDialog progressDialog;
    private List<volunteer> vlist = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vlounteer_list);

        progressDialog = new ProgressDialog(volunteerList.this);
        progressDialog.setMessage("请等待...");
        progressDialog.setIcon(R.drawable.image1);
        progressDialog.setTitle("正在查询");

        vtList = (ListView) findViewById(R.id.volunteerList);

        //开始异步任务
        new GetListByWeb().execute(IpUtils.VTSEL_PATH);

        vtList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String number = vlist.get(position).getId();
                Intent intent = new Intent(Intent.ACTION_DIAL);
                Uri data = Uri.parse("tel:" + number);
                intent.setData(data);
                startActivity(intent);
            }
        });
    }

    class GetListByWeb extends AsyncTask<String, Void, List<volunteer>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.show();
        }

        public List<volunteer> getListByJson(String json) {
            //转换json串
            List<volunteer> list = new ArrayList<>();
            try {
                JSONArray jsonArray = new JSONArray(json);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    volunteer vt = new volunteer();
                    vt.setId(""+jsonObject.getString("id"));
                    vt.setName(""+jsonObject.getString("name"));
                    vt.setSex(""+jsonObject.getString("sex"));
                    vt.setType(""+jsonObject.getString("type"));
                    list.add(vt);
                }
                vlist = list;
                return list;
            } catch (JSONException e) {
                e.printStackTrace();
            }


            return null;
        }

        @Override
        protected List<volunteer> doInBackground(String... params) {
            try {
                Thread.sleep(3000);
                HttpURLConnection hc = (HttpURLConnection) new URL(params[0]).openConnection();
                hc.setRequestMethod("POST");
                hc.setReadTimeout(5000);

                //获取值
                BufferedReader br = new BufferedReader(new InputStreamReader(hc.getInputStream()));
                String str = "";
                StringBuffer sf = new StringBuffer();
                while ((str = br.readLine()) != null) {
                    sf.append(str);
                }
                return getListByJson(sf.toString());

            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<volunteer> volunteers) {
            super.onPostExecute(volunteers);
            Log.i("查询表单异步任务", "" + volunteers.get(0).getName() + " " + volunteers.get(0).getId());
            System.out.print(volunteers);
            progressDialog.dismiss();
            vtList.setAdapter(new MyVolunteerAdapter(volunteers,volunteerList.this));
        }
    }

}
