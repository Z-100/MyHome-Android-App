package com.example.myhome.activities.account;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.myhome.R;

public class MembersActivity extends AppCompatActivity {

    ImageButton btn_member1, btn_member2, btn_member3,btn_member4;
    Button btn_logout, btn_newmemeber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_members);


        btn_member1 = findViewById(R.id.btn_member1_members);
        btn_member2 = findViewById(R.id.btn_member2_members);
        btn_member3 = findViewById(R.id.btn_member3_members);
        btn_member4 = findViewById(R.id.btn_member4_members);

        btn_logout = findViewById(R.id.btn_logout_members);
        btn_newmemeber = findViewById(R.id.btn_newmember_members);



        btn_logout.setOnClickListener(view -> {
            openLoginActivity();
        });
    }

    public void openLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}