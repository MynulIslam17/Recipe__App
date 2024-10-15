package com.example.recipeapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class IngredientFragment extends Fragment {

     TextView tvStep;
     public static String ingredient;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View myView= inflater.inflate(R.layout.fragment_ingredient, container, false);
        tvStep=myView.findViewById(R.id.tvStep);

        String[] ingArray2=ingredient.split("\n");

        for(int i=0;i<ingArray2.length ;i++){

            String ing=ingArray2[i].trim();

            if(!ing.isEmpty()){
                tvStep.append("\uD83D\uDFE3 "+ing);
                tvStep.append("\n");
            }






        }



     return  myView;
    }
}