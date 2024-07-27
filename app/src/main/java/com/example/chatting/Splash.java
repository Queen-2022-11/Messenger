package com.example.chatting;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class Splash extends AppCompatActivity {
    ImageView logo;
    TextView name,own1,own2;
    Animation topAnime,bottomAnime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        logo=findViewById(R.id.logo);
        name=findViewById(R.id.name);
        own1=findViewById(R.id.own1);
        own2=findViewById(R.id.own2);
        topAnime= AnimationUtils.loadAnimation(this,R.anim.top_animation);

        bottomAnime= AnimationUtils.loadAnimation(this,R.anim.bottom_animation);
        logo.setAnimation(topAnime);
        name.setAnimation(topAnime);
        own1.setAnimation(bottomAnime);
        own2.setAnimation(bottomAnime);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(Splash.this,Login.class);
                startActivity(intent);
                finish();
            }
        },4000);
    }
}