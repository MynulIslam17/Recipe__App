package com.example.recipeapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

public class DetailsActivity extends AppCompatActivity {

    ImageView backToPrevious,zoomImage,expendMore,itemImage;
    TextView itemName,itemTime;
    TabLayout tabLay;
 LinearLayout linLay;
    Animation anim;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        //find's ids
        backToPrevious=findViewById(R.id.backToPrevious);
        zoomImage=findViewById(R.id.zoomImage);

        itemName=findViewById(R.id.itemName);
        itemTime=findViewById(R.id.itemTime);
        tabLay=findViewById(R.id.tabLay);
        itemImage=findViewById(R.id.itemImage);
      linLay=findViewById(R.id.linLay);





        zoomImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(itemImage.getTag().toString().contains("crop")){
                    itemImage.setScaleType(ImageView.ScaleType.FIT_CENTER);
                    itemImage.setTag("fit");
                    //changing icon color
                    zoomImage.setColorFilter(Color.RED);
                }
                else{
                    itemImage.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    itemImage.setTag("crop");
                    //changing icon color
                    zoomImage.setColorFilter(Color.BLACK);
                }
            }
        });



        //set back imageview click event
        backToPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



         // recieve all the string sends from (CategoryActivity or SearchActivitty or MainActivity )
        Intent intent=getIntent();
        String [] ingArray=intent.getStringArrayExtra("ingArray"); //first index of this array is time
        String name=intent.getStringExtra("title");
        String imageUrl=intent.getStringExtra("imgUrl");
        String description=intent.getStringExtra("description");
        String ingredient=intent.getStringExtra("ingredient");

        // now set all the value
        itemTime.setText(ingArray[0]);
        itemName.setText(name);
        Picasso.get().load(imageUrl).into(itemImage);



        //first fragment load by default when activity start
        LoadFragment(new IngredientFragment(),true);
        //and pass ingrediatn for that item
        IngredientFragment.ingredient=ingredient;

        // select fregment from tablaout
        tabLay.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position=tab.getPosition();

                if(position==0){
                    LoadFragment(new IngredientFragment(),false);
                    IngredientFragment.ingredient=ingredient;

                }
                else{
                    LoadFragment(new StepFragment(),false);
                    StepFragment.step=description;

                }


            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


    }


    // method for changing fragment
    public  void LoadFragment(Fragment fragment,boolean flag){

        FragmentManager fm=getSupportFragmentManager();
        FragmentTransaction ft=fm.beginTransaction();
        if(flag){
            ft.add(R.id.container,fragment);
        }
        else{
            ft.replace(R.id.container,fragment);
        }
        ft.commit();

    }


}