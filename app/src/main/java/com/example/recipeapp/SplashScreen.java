package com.example.recipeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

public class SplashScreen extends AppCompatActivity {
    TextView tvAppName,tvMade;
    Animation anim;
    SharedPreferences sPref;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        //find id
        tvAppName=findViewById(R.id.tvAppName);
        tvMade=findViewById(R.id.tvMade);

        // set animation
        anim= AnimationUtils.loadAnimation(SplashScreen.this,R.anim.textanim);

        tvAppName.startAnimation(anim);
        tvMade.startAnimation(anim);



        // now go to main activity after a delay

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                sPref=getSharedPreferences(Integer.toString(R.string.app_name),MODE_PRIVATE);
                boolean checkLogin=sPref.getBoolean("isLogin",false);

                if(!checkLogin){  //if user not skip onboardscreen or for the first time app load
                    Intent myIntent=new Intent(SplashScreen.this,OnBoardActivity.class);
                    startActivity(myIntent);
                    finish();
                }
                else{  // if user skip on get started from onboardscreen then go to mainactivity when app launch
                    Intent myIntent=new Intent(SplashScreen.this,MainActivity.class);
                    startActivity(myIntent);
                    finish();
                }



            }
        },3100);


    }
}