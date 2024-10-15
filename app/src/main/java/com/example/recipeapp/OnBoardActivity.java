package com.example.recipeapp;



import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class OnBoardActivity extends AppCompatActivity {

    ViewPager2 viewpager;
    TextView next,skip,start;
    LinearLayout layDots;
    TextView [] dots;

    SharedPreferences sPref;
    SharedPreferences.Editor editor;
    Animation anim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_board);
    //find id's
        viewpager=findViewById(R.id.viewpager);
         skip=findViewById(R.id.skip);
         next=findViewById(R.id.next);
         start=findViewById(R.id.start);
             layDots=findViewById(R.id.layDots);

             //create a animation
        anim= AnimationUtils.loadAnimation(OnBoardActivity.this,R.anim.btn_slide);

        // calling adapter
        MYAdapter adapter =new MYAdapter(getSupportFragmentManager(),getLifecycle());
        viewpager.setAdapter(adapter);


        addDots(0);


        //next click event
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int currentitem=viewpager.getCurrentItem();

                if(currentitem<adapter.getItemCount()-1){
                    viewpager.setCurrentItem(currentitem+1);
                }


            }
        });




    // when page change
      viewpager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
          @Override
          public void onPageSelected(int position) {

            addDots(position); // for dot idicator
            //this is for button hide and show
              if(position==adapter.getItemCount()-1){
                start.startAnimation(anim); //set anim

                  next.setVisibility(View.GONE);
                  start.setVisibility(View.VISIBLE);

              }
              else{

                  next.setVisibility(View.VISIBLE);
                  start.setVisibility(View.GONE);


              }

          }
      });

      // viewpager page swap effect
        viewpager.setPageTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                float scaleFactor = Math.max(0.4f, 1 - Math.abs(position));
                page.setScaleX(scaleFactor);
                page.setScaleY(scaleFactor);
                page.setAlpha(1 - Math.abs(position) * 0.3f);
            }
        });

     // skip click event
      skip.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
           // for store login flag value
              sPref=getSharedPreferences(Integer.toString(R.string.app_name),MODE_PRIVATE);
              editor=sPref.edit();
              editor.putBoolean("isLogin",true);
              editor.apply();
              // then change activity
              startActivity(new Intent(OnBoardActivity.this,MainActivity.class));
              finish();


          }
      });

      // start click event

      start.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {

              // for store login flag value
              sPref=getSharedPreferences(Integer.toString(R.string.app_name),MODE_PRIVATE);
              editor=sPref.edit();
              editor.putBoolean("isLogin",true);
              editor.apply();

              // then change activity
              startActivity(new Intent(OnBoardActivity.this,MainActivity.class));
              finish();
          }
      });






    }//end on creat

    public void addDots(int position) {
        dots = new TextView[3]; // Assuming you have 3 pages

        layDots.removeAllViews();

        for (int i=0; i < dots.length; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);

            layDots.addView(dots[i]);
        }

        // Highlight the current dot
        if (dots.length > 0) {
            dots[position].setTextColor(getResources().getColor(R.color.white)); // Highlight the current dot
        }


    }






}