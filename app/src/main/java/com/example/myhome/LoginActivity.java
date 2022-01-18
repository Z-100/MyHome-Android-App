package com.example.myhome;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class LoginActivity extends AppCompatActivity {
    FloatingActionButton btn_signIn;
    Button btn_signUp, btn_forgotPassword;
    EditText et_username, et_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btn_signIn   = findViewById(R.id.btn_log_in_login);
        btn_signUp = findViewById(R.id.btn_sign_up_login);
        btn_forgotPassword = findViewById(R.id.btn_forgot_password_login);
        et_username = findViewById(R.id.et_username_login);
        et_password = findViewById(R.id.et_password_login);

        btn_signIn.setOnClickListener(view -> {
            if (checkCredentials(et_username.getText().toString(), et_password.getText().toString())){
                openMembersActivity();
            }
        });

        btn_signUp.setOnClickListener(view -> {
            openRegisterActivity();
        });

        btn_forgotPassword.setOnClickListener(view -> {
            openResetPasswordActivity();
        });
    }



    public boolean checkCredentials(String username, String password){
        return true;
    }

    public void openResetPasswordActivity() {
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