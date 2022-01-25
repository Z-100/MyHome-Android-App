package com.example.myhome;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import com.example.myhome.activities.account.MembersActivity;

public class InfoActivity extends AppCompatActivity {
    private ImageButton backButton;

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

    public void returnToOverview(String[] accountName){
        Intent intent = new Intent(this, MembersActivity.class);
        intent.putExtra(Constants.MEMBER,accountName);
        startActivity(intent);
    }
}