package com.example.recipeapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class StepFragment extends Fragment {
  TextView tvStep;
  public static String step="";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View myView= inflater.inflate(R.layout.fragment_step, container, false);

        //find id

        tvStep=myView.findViewById(R.id.tvStep);

        tvStep.setText("\uD83D\uDFE3 " +step);



        return myView;
    }
}