package com.example.bisheapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;

public class welcome extends AppCompatActivity {
    private ImageView welcome;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        welcome = (ImageView)findViewById(R.id.welcome);
        AlphaAnimation animation = new AlphaAnimation(0.3f,0.4f);
        animation.setDuration(2000);
        welcome.startAnimation(animation);
        animation.setAnimationListener(new AnimationImpl());
    }

    private class AnimationImpl implements Animation.AnimationListener{

        @Override
        public void onAnimationStart(Animation animation) {
            welcome.setBackgroundResource(R.drawable.page);
        }

        @Override
        public void onAnimationEnd(Animation animation) {
            startActivity(new Intent(welcome.this,MainActivity.class));
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    }
}
