package com.example.myhome;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import com.example.myhome.activities.account.MembersActivity;

/**
 * @author Rad14nt
 * Class used to Display Info Page and get/use the necessary data
 * */

public class InfoActivity extends AppCompatActivity {
    private ImageButton backButton;


    /**
     * Method used to create and start the view
     *
     * @param savedInstanceState Standard bundle to start creation of the view
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        backButton = findViewById(R.id.btn_backButton_info);
        String[] accountName = getIntent().getStringArrayExtra(Constants.MEMBER);

        backButton.setOnClickListener(view -> {
            returnToOverview(accountName);
        });
    }

    /**
     * Method used to return to Overview activity
     * @param accountName account name given so overview knows what account to show
     */
    public void returnToOverview(String[] accountName){
        Intent intent = new Intent(this, MembersActivity.class);
        intent.putExtra(Constants.MEMBER,accountName);
        startActivity(intent);
    }
}