package com.example.demo_06;

import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Spinner spinner;
    private String[] str = {"喜欢", "不喜欢", "一般般"};
    private String[] str1 = {"健身", "篮球", "游泳", "音乐", "吃饭", "旅游"};
    private Button alertDialog;
    private DialogInterface.OnClickListener dc = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which){
                case -1:
                    Toast.makeText(MainActivity.this, "喜欢"+which, Toast.LENGTH_SHORT).show();
                    break;
                case -2:
                    Toast.makeText(MainActivity.this, "不喜欢"+which, Toast.LENGTH_SHORT).show();
                    break;
                case -3:
                    Toast.makeText(MainActivity.this, "一般"+which, Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        alertDialog = (Button) findViewById(R.id.alertDialog);

    }
}
