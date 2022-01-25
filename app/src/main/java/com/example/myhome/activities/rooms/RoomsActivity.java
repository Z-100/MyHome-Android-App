package com.example.myhome.activities.rooms;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.myhome.R;

/**
 * @author Rad14nt
 * Class used to Display Rooms Page and get/use the given login data
 * */

public class RoomsActivity extends AppCompatActivity {

    /**
     * Method used to create and start the view
     *
     * @param savedInstanceState Standard bundle to start creation of the view
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rooms);
    }
}