package com.example.myhome;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {
    Button btn_logIn;
    Button btn_signUp;
    EditText et_username;
    EditText et_password;
    EditText et_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        btn_logIn       =    findViewById(R.id.btn_log_in);
        btn_signUp      =    findViewById(R.id.btn_sign_up);
        et_username     =    findViewById(R.id.et_register_username);
        et_password     =    findViewById(R.id.et_register_password);
        et_email        =    findViewById(R.id.et_register_email);

        btn_signUp.setOnClickListener(view -> {
            openMembersActivity();
        });


        btn_logIn.setOnClickListener(view -> {
            openLoginActivity();
        });
    }

    public void openMembersActivity() {
        Intent intent = new Intent(this, MembersActivity.class);
        startActivity(intent);
    }
    public void openLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}