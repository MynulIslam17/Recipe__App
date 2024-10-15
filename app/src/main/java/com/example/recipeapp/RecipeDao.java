package com.example.recipeapp;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;


@Dao
public interface RecipeDao {


    @Query("Select * from recipe")
    List<Recipe> getAllData();

    @Query("Select * from recipe where category= :cat")
    List<Recipe>getByCategory(String cat);

    @Query("Select * from recipe where tittle like :tl")
    List<Recipe>search(String tl);





}


