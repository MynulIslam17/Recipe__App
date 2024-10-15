package com.example.recipeapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class CategoryActivity extends AppCompatActivity {


     ImageView backToHomeFromCategroy;
     TextView tvTopTitle;
     RecyclerView categoryRecycleview;

      ArrayList<Recipe>itemList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        backToHomeFromCategroy=findViewById(R.id.backToHomeFromCategroy);
        categoryRecycleview=findViewById(R.id.categoryRecycleview);
        tvTopTitle=findViewById(R.id.tvTopTitle);

        //get category and title forom mainActivity
        Intent intent=getIntent();
        String title=intent.getStringExtra("Title");
        String category=intent.getStringExtra("Category");

        // set the string as title
        tvTopTitle.setText(title);

        // call method which search fromm database
        PerformSearch(category);

        // now call adapter class
        CategoryAdapter adapter=new CategoryAdapter();
        categoryRecycleview.setAdapter(adapter);
        categoryRecycleview.setLayoutManager(new LinearLayoutManager(CategoryActivity.this));


        // go back home when click
        backToHomeFromCategroy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

    // method for search anything fromdatabase
    public void PerformSearch(String category){

        AppDatabase database=AppDatabase.getDb(CategoryActivity.this);
        itemList= (ArrayList<Recipe>) database.recipedao().getByCategory(category);
    }

    // adapter class for recycleview
    public class CategoryAdapter extends  RecyclerView.Adapter<CategoryAdapter.MyHolder>{

        public class MyHolder extends  RecyclerView.ViewHolder{
            TextView catTitle,catTime;
            ImageView catImage;
            CardView cardBody;

            public MyHolder(@NonNull View itemView) {
                super(itemView);

                catTitle=itemView.findViewById(R.id.catTitle);
                catTime=itemView.findViewById(R.id.catTime);
                catImage=itemView.findViewById(R.id.catImage);
                cardBody=itemView.findViewById(R.id.cardBody);


            }
        }




        @NonNull
        @Override
        public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            LayoutInflater  layinflater= (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View myview=layinflater.inflate(R.layout.category_item,parent,false);


            MyHolder holder=new MyHolder(myview);
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull MyHolder holder, int position) {
            int uid=itemList.get(position).getId();
            String imgUrl=itemList.get(position).getImage();
            String title=itemList.get(position).getTitle();
            String description =itemList.get(position).getDescription();
            String ingredient=itemList.get(position).getIngredient();
            String category=itemList.get(position).getCategory();

            //set all the value
            Picasso.get().load(imgUrl).into(holder.catImage);
            holder.catTitle.setText(title);

            //step1 : get time from ingredient
            // step 2 : time defines in ingredient string first line  so split all line and shows line1
            String [] ingArray=ingredient.split("\n");
            holder.catTime.setText(ingArray[0]);

            holder.cardBody.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent myIntent=new Intent(CategoryActivity.this,DetailsActivity.class);
                    //pass all the value of selected item to DetailsActivity
                    myIntent.putExtra("imgUrl",imgUrl);
                    myIntent.putExtra("title",title);
                    myIntent.putExtra("description",description);
                    myIntent.putExtra("ingredient",ingredient);
                    myIntent.putExtra("category",category);
                    myIntent.putExtra("ingArray",ingArray);
                    startActivity(myIntent);





                }
            });



        }

        @Override
        public int getItemCount() {
            return itemList.size();
        }



    }



}