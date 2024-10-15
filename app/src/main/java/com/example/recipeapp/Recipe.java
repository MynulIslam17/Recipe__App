package com.example.recipeapp;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Dao;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
@Entity(tableName = "recipe")

public class Recipe {


    @PrimaryKey
    @NonNull
    @ColumnInfo(name="uid")
    private int id;

    @NonNull
    @ColumnInfo(name = "img")
    private String image;

    @NonNull
    @ColumnInfo(name="tittle")
    private String title;

    @NonNull
    @ColumnInfo (name = "des")
    private String description;

    @NonNull
    @ColumnInfo (name = "ing")
    private String ingredient;

    @NonNull
    @ColumnInfo (name = "category")
    private String category;

    public Recipe(int id, @NonNull String image, @NonNull String title, @NonNull String description, @NonNull String ingredient, @NonNull String category) {
        this.id = id;
        this.image = image;
        this.title = title;
        this.description = description;
        this.ingredient = ingredient;
        this.category = category;
    }

    @NonNull
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NonNull
    public String getImage() {
        return image;
    }

    public void setImage(@NonNull String image) {
        this.image = image;
    }

    @NonNull
    public String getTitle() {
        return title;
    }

    public void setTitle(@NonNull String title) {
        this.title = title;
    }

    @NonNull
    public String getDescription() {
        return description;
    }

    public void setDescription(@NonNull String description) {
        this.description = description;
    }

    @NonNull
    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(@NonNull String ingredient) {
        this.ingredient = ingredient;
    }

    @NonNull
    public String getCategory() {
        return category;
    }

    public void setCategory(@NonNull String category) {
        this.category = category;
    }
}






