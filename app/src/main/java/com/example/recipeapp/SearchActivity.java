package com.example.recipeapp;

import androidx.annotation.ContentView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.internal.TextWatcherAdapter;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SearchActivity extends AppCompatActivity {

    SearchView searchView;
    EditText edSearch;
    ImageView backToHome;
    RecyclerView searchRecycleview;
    ExecutorService executorService = Executors.newSingleThreadExecutor();

    public static ArrayList<Recipe>newPopularList; // get pupular item list from maainActivity

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        //find id's
        edSearch=findViewById(R.id.edSearch);
        backToHome=findViewById(R.id.backToHome);
        searchRecycleview=findViewById(R.id.searchRecycleview);


       edSearch.requestFocus(); // this is for make focus on editext while acitivity get loaded

        // click back image
        backToHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        // this code for set rdcycle view
        SearchAdapter adapter=new SearchAdapter();
        searchRecycleview.setAdapter(adapter);
        searchRecycleview.setLayoutManager(new LinearLayoutManager(SearchActivity.this));


        // this code will execute when where type anything
        edSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                executorService.execute(new Runnable() {
                    @Override
                    public void run() {
                        // Database operations here
                        String search=s.toString();

                        AppDatabase database=AppDatabase.getDb(SearchActivity.this);

                        ArrayList<Recipe>filterList = (ArrayList<Recipe>) database.recipedao().search("%"+search+"%");

                        if(search.isEmpty()){  // when edittext empty show popular food
                            newPopularList= (ArrayList<Recipe>) database.recipedao().getByCategory("Popular");

                        }
                        else {  // if search any thing then show result regard that search
                            newPopularList=filterList;
                        }

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                adapter.notifyDataSetChanged();
                            }
                        });

                    }




                });



            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });







    }

    // adapter class for searchh reasult

    public class SearchAdapter extends  RecyclerView.Adapter<SearchAdapter.MyHolder>{



        public class MyHolder extends  RecyclerView.ViewHolder{

            ImageView searchFoodImage;
            TextView searchFoodName;
            LinearLayout itemBody;
            public MyHolder(@NonNull View itemView) {
                super(itemView);
                searchFoodImage=itemView.findViewById(R.id.searchFoodImage);
                searchFoodName=itemView.findViewById(R.id.searchFoodName);
                itemBody=itemView.findViewById(R.id.itemBody);

            }
        }





        @NonNull
        @Override
        public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            LayoutInflater layInflater=getLayoutInflater();
            View myView=layInflater.inflate(R.layout.search_item,parent,false);

            MyHolder holder =new MyHolder(myView);

            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull MyHolder holder, int position) {

            // get the pupular  item name & image from pupularlist

            String imgUrl=newPopularList.get(position).getImage();
            String itemName=newPopularList.get(position).getTitle();
            String  description=newPopularList.get(position).getDescription();
            String ingredient=newPopularList.get(position).getIngredient();
            //time of cooking defines in ingredient string so split from there
             String[] ingArray=ingredient.split("\n");

          //  String category=newPopularList.get(position).getCategory();  Note: not needed

            // assign the value
            holder.searchFoodName.setText(itemName);
            Picasso.get().load(imgUrl).into(holder.searchFoodImage);

           //search resutl item body click event

            holder.itemBody.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                Intent myIntent=new Intent(SearchActivity.this,DetailsActivity.class);

                myIntent.putExtra("title",itemName);
                myIntent.putExtra("description",description);
                myIntent.putExtra("ingredient",ingredient);
                myIntent.putExtra("imgUrl",imgUrl);
                myIntent.putExtra("ingArray",ingArray);
                startActivity(myIntent);

                }
            });


        }

        @Override
        public int getItemCount() {
            return newPopularList.size();
        }




    }




}