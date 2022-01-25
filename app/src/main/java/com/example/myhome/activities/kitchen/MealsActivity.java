package com.example.myhome.activities.kitchen;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.myhome.R;
/**
 * @author Rad14nt
 * Class used to Display Meals Page and get/use the given data
 * */

public class MealsActivity extends AppCompatActivity {

    /**
     * Method used to create and start the view
     *
     * @param savedInstanceState Standard bundle to start creation of the view
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meals);
    }
}