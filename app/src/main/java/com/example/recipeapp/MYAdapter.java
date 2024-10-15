package com.example.recipeapp;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class MYAdapter extends FragmentStateAdapter {


    public MYAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {

        switch (position){

            case 0 :Fragment one=new Fragment1();
            return one;
            case 1 : Fragment two=new Fragment2();
            return two;
            case 2: Fragment three=new Fragment3();
            return three;
            default: return new Fragment1();


        }



    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
