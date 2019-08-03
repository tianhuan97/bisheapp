package com.example.demo_11;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import static com.example.demo_11.R.menu.one;

public class MainActivity extends AppCompatActivity {
    private ListView xinxi;
    private MyAdapter myAdapter;
    private GetPhoneNumberFromMobile getPhoneNumberFromMobile;
    private List<PhoneInfo> list = new ArrayList<PhoneInfo>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        xinxi = (ListView) findViewById(R.id.xinxi);/*
        getPhoneNumberFromMobile = new GetPhoneNumberFromMobile();
        list = getPhoneNumberFromMobile.getPhoneNumberFromMobile(this);*/
        String name1 = "志愿者：张三";
        String number1 = "12345678901";
        PhoneInfo phoneInfo1 = new PhoneInfo(name1,number1);
        String name2 = "志愿者：李四";
        String number2 = "13812345678";
        PhoneInfo phoneInfo2 = new PhoneInfo(name2,number2);
        String name3 = "志愿者：王五";
        String number3 = "15512345678";
        PhoneInfo phoneInfo3 = new PhoneInfo(name3,number3);
        list.add(phoneInfo1);
        list.add(phoneInfo2);
        list.add(phoneInfo3);


        xinxi.setAdapter(new MyAdapter(list,MainActivity.this));

        /*xinxi.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String number = list.get(position).getNumber();
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+number));
                if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                startActivity(intent);
            }
        });*/

        /*xinxi.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                String number = list.get(position).getNumber();
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+number));
                if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    return true;
                }
                startActivity(intent);
                return false;
            }
        });
*/
        xinxi.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String name = list.get(position).getName();
                String number = list.get(position).getNumber();
                Intent intent = new Intent(MainActivity.this,detailedInfomation.class);
                Bundle bundle = new Bundle();
                bundle.putString("name",name);
                bundle.putString("number",number);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        registerForContextMenu(xinxi);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.two,menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater mf = getMenuInflater();
        mf.inflate(R.menu.one,menu);
        return true;
    }

    /*@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int i = item.getItemId();
        switch (i){
            case R.id.call:
                Stri

        }
        return false;
    }*/
}
