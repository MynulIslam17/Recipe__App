package com.example.recipeapp;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = Recipe.class,exportSchema = false,version = 1)

public abstract class AppDatabase extends RoomDatabase {


    private static AppDatabase instance;
    private static final String DbName="RecipeDatabase";


    public static synchronized  AppDatabase getDb(Context context){
         if(instance==null){
             instance= Room.databaseBuilder(context,AppDatabase.class,DbName)
                     .fallbackToDestructiveMigration()
                     .allowMainThreadQueries()
                     .createFromAsset("database/recipe.db")
                     .build();
         }
         return instance;
    }

    public abstract RecipeDao recipedao();



}
