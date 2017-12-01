package com.example.demo_05;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    private Button alpha,scale,translate,rotate;
    private ImageView imageView;
    private Animation animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        alpha = (Button) findViewById(R.id.alpha);
        scale = (Button) findViewById(R.id.scale);
        translate = (Button) findViewById(R.id.translate);
        rotate = (Button) findViewById(R.id.rotate);
        imageView = (ImageView) findViewById(R.id.imageView2);
        translate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animation = AnimationUtils.loadAnimation(MainActivity.this,R.anim.translate);
                imageView.startAnimation(animation);
            }
        });
        alpha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* Intent intent = new Intent(MainActivity.this,Main2Activity.class);
                startActivity(intent);*/
                animation = AnimationUtils.loadAnimation(MainActivity.this,R.anim.alpha);
                imageView.startAnimation(animation);
            }
        });
        scale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* Intent intent = new Intent(MainActivity.this,Main2Activity.class);
                startActivity(intent);*/
                animation = AnimationUtils.loadAnimation(MainActivity.this,R.anim.scale);
                imageView.startAnimation(animation);
            }
        });
        rotate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent intent = new Intent(MainActivity.this,Main2Activity.class);
                startActivity(intent);*/
                animation = AnimationUtils.loadAnimation(MainActivity.this,R.anim.rotate);
                imageView.startAnimation(animation);
            }
        });
    }
}
