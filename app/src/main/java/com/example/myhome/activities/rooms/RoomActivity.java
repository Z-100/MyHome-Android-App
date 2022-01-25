package com.example.myhome.activities.rooms;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myhome.Constants;
import com.example.myhome.R;

/**
 * @author Rad14nt
 * Class used to Display Room Page and get/use the given login data
 * */
public class RoomActivity extends AppCompatActivity {
    private TextView name;
    private ImageView image;
    /**
     * Method used to create and start the view
     *
     * @param savedInstanceState Standard bundle to start creation of the view
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);

        name = findViewById(R.id.roomdata);
        image= findViewById(R.id.imageView);

        Intent intent = getIntent();

        name.setText(intent.getStringExtra(Constants.ROOMNAME));

        int roomImageId= intent.getIntExtra(Constants.ROOMIMAGE, 1);

        if (roomImageId == 0 ){
            image.setImageResource(R.drawable.kitchen);
        } else if (roomImageId == 1) {
            image.setImageResource(R.drawable.bedroom);
        }else if (roomImageId == 2) {
            image.setImageResource(R.drawable.living);
        }else if (roomImageId == 3) {
            image.setImageResource(R.drawable.bathroom);
        }
        else if (roomImageId == 5) {
            image.setImageResource(R.drawable.garage);
        }
    }
}