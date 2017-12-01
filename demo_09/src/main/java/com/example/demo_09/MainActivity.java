package com.example.demo_09;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
     private Button time,date,jindu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        date = (Button) findViewById(R.id.date);
        time = (Button) findViewById(R.id.time);
        jindu = (Button) findViewById(R.id.jindu);
        date.setOnClickListener(new View.OnClickListener() {
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        String sj = year+"-"+(monthOfYear+1)+"-"+dayOfMonth;
                        Toast.makeText(MainActivity.this, sj, Toast.LENGTH_SHORT).show();
                    }
                },year,month,day);
                datePickerDialog.show();
            }
        });

        time.setOnClickListener(new View.OnClickListener() {
            Calendar calendar = Calendar.getInstance();
            int hour = calendar.get(Calendar.HOUR);
            int min = calendar.get(Calendar.MINUTE);
            @Override
            public void onClick(View v) {
                TimePickerDialog tpd = new TimePickerDialog(MainActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        String sj = hourOfDay+":"+minute;
                        Toast.makeText(MainActivity.this, sj, Toast.LENGTH_SHORT).show();
                    }
                },hour+9,min,true);
                tpd.show();
            }
        });

        jindu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog pd = new ProgressDialog(MainActivity.this);
                pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                pd.setTitle("数据读取");
                pd.setMessage("LOADING.....");
                pd.setMax(100);
                pd.show();

                class MyThread extends Thread{
                    @Override
                    public void run() {
                        int i = 0;
                        while (true){
                            pd.setProgress(i+=10);
                            try {
                                sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            if(i==100){
                                pd.dismiss();
                                break;
                            }
                        }
                    }
                }
                new MyThread().start();
            }
        });
    }
}
