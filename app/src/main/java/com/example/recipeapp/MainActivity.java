package com.example.recipeapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Insert;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

   RecyclerView recyclerview;
   ArrayList<Recipe>popularList;
   EditText edSearch;
   ImageView desserCat,saladCat,mainCat,drinksCat,topMenu;

   BottomSheetDialog bottomSheet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //find id's
        recyclerview=findViewById(R.id.recycleview);
        desserCat=findViewById(R.id.dessertCat);
        saladCat=findViewById(R.id.saladCat);
        mainCat=findViewById(R.id.mainCat);
        drinksCat=findViewById(R.id.drinksCat);
     edSearch=findViewById(R.id.edSearch);
     topMenu=findViewById(R.id.topMenu);

      //get popular recipe form db
         AppDatabase database=AppDatabase.getDb(MainActivity.this);
          popularList= (ArrayList<Recipe>) database.recipedao().getByCategory("Popular");



        // popular item recycleview adapter

        PopularAdapter adapter=new PopularAdapter();
        recyclerview.setAdapter(adapter);
        recyclerview.setLayoutManager(new LinearLayoutManager(MainActivity.this,LinearLayoutManager.HORIZONTAL,false));


        //search edittext click event
        edSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myintent=new Intent(MainActivity.this,SearchActivity.class);

                SearchActivity.newPopularList=popularList; // pass the pupular list
                startActivity(myintent);

            }
        });

        //category click event =============================
      drinksCat.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              Intent myIntent=new Intent(MainActivity.this,CategoryActivity.class);
              myIntent.putExtra("Title","Drinks");
              myIntent.putExtra("Category","Drinks");
              startActivity(myIntent);

          }
      });

      mainCat.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {

              Intent myIntent=new Intent(MainActivity.this,CategoryActivity.class);
              myIntent.putExtra("Title","Main Dish");
              myIntent.putExtra("Category","Dish");
              startActivity(myIntent);


          }
      });


        saladCat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent myIntent=new Intent(MainActivity.this,CategoryActivity.class);
                myIntent.putExtra("Title","Salad Item");
                myIntent.putExtra("Category","Salad");
                startActivity(myIntent);


            }
        });


        desserCat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent myIntent=new Intent(MainActivity.this,CategoryActivity.class);
                myIntent.putExtra("Title","Dessert Item");
                myIntent.putExtra("Category","Desserts");
                startActivity(myIntent);


            }
        });

//====================================================================


        // top menu icon click event
        topMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                bottomSheet=new BottomSheetDialog(MainActivity.this);
                bottomSheet.setContentView(R.layout.bottom_sheet);
                bottomSheet.setCanceledOnTouchOutside(true);
                bottomSheet.setCancelable(true);
                // find id's of bottomsheet xml
                LinearLayout policyLay=bottomSheet.findViewById(R.id.policyLay);
                LinearLayout developerLay=bottomSheet.findViewById(R.id.developerLay);

                // click event on policylay
                policyLay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(MainActivity.this,"Policy will add soon",Toast.LENGTH_SHORT).show();
                    }
                });

                developerLay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(MainActivity.this,"Mynul Islam ",Toast.LENGTH_SHORT).show();
                    }
                });


                bottomSheet.show();

            }
        });



        // now check Internet connection available or not

          if(! CheckInternet()){  // check if not true

              Dialog dialog=new Dialog(MainActivity.this);
              dialog.setContentView(R.layout.internet_dialog);
              dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
              dialog.setCancelable(false);
              // find id's of dialog layout
              TextView tvRetry=dialog.findViewById(R.id.tvRetry);
              tvRetry.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View v) {
                      if(CheckInternet()){
                          dialog.dismiss();
                      }

                  }
              });
              dialog.show();

          }





    } // end oncreat



// method for backPressed

    @Override
    public void onBackPressed() {
       // super.onBackPressed();
        Dialog dialog=new Dialog(MainActivity.this);
        dialog.setContentView(R.layout.exit_layout);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        //find id's
        TextView tvYes=dialog.findViewById(R.id.tvYes);
        TextView tvNo=dialog.findViewById(R.id.tvNo);;

        tvYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
               finish();
               
            }
        });

        tvNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });



        dialog.show();
    }


    //adapter class for popular recipe

    public class PopularAdapter extends  RecyclerView.Adapter<PopularAdapter.Myholder>{



        public class Myholder extends  RecyclerView.ViewHolder{
            ImageView popularImg;
            TextView popularItem,popularTime;

            public Myholder(@NonNull View itemView) {
                super(itemView);
                popularImg=itemView.findViewById(R.id.popularImg);
                popularItem=itemView.findViewById(R.id.popularItem);
                popularTime=itemView.findViewById(R.id.popularTime);



            }
        }



        @NonNull
        @Override
        public Myholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            LayoutInflater layInflater= (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View myview=layInflater.inflate(R.layout.item_popular,parent,false);


            Myholder holder=new Myholder(myview);

            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull Myholder holder, int position) {

            //get the data from popular list
            String imgUrl=popularList.get(position).getImage();
             String popularItemName=popularList.get(position).getTitle();
             String ingredient=popularList.get(position).getIngredient();
             int uid=popularList.get(position).getId();
             String desciption=popularList.get(position).getDescription();
             String category=popularList.get(position).getCategory();


             //assign value all value
            holder.popularItem.setText(popularItemName);

           // split the time from ingredient string then  print the first line(string)
            String [] ingArray=ingredient.split("\n");
            holder.popularTime.setText(ingArray[0]);
            Picasso.get().load(imgUrl).into(holder.popularImg);



            //click event on itembody
            holder.popularImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    // go to DetaillsActivity when any racipe click with data
                    Intent myIntent=new Intent(MainActivity.this,DetailsActivity.class);
                    myIntent.putExtra("title",popularItemName);
                    myIntent.putExtra("description",desciption) ;
                    myIntent.putExtra("imgUrl",imgUrl);
                    myIntent.putExtra("ingredient",ingredient);
                    myIntent.putExtra("ingArray",ingArray);

                    startActivity(myIntent);

                }
            });


        }

        @Override
        public int getItemCount() {
            return popularList.size();
        }


    }


    // check internet Connection available or not
    public boolean CheckInternet(){

        ConnectivityManager cm= (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo=cm.getActiveNetworkInfo();

        if(netInfo != null && netInfo.isConnectedOrConnecting()){
            return  true;

        }
        else{
            return false;
        }



    }



}