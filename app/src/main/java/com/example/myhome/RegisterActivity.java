package com.example.myhome;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class RegisterActivity extends AppCompatActivity {
    Button btn_logIn;
    Button btn_register;
    EditText et_username;
    EditText et_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        btn_logIn       =     findViewById(R.id.btn_login_register);
        btn_register    =     findViewById(R.id.btn_log_in_register);


        btn_logIn.setOnClickListener(view -> {
            openMembersActivity();
        });


        btn_register.setOnClickListener(view -> {
            openRegisterActivity();
        });

    }
    public void openMembersActivity() {
        Intent intent = new Intent(this, MembersActivity.class);
        startActivity(intent);
    }
    public void openRegisterActivity() {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }
}