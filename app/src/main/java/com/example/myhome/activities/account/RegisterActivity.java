package com.example.myhome.activities.account;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myhome.Constants;
import com.example.myhome.R;
import com.example.myhome.api.AccountService;

import org.json.JSONException;

public class RegisterActivity extends AppCompatActivity {
    Button btn_logIn, btn_confirm;
    EditText et_username, et_password, et_email;
    AccountService accountService = new AccountService();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        btn_logIn   = findViewById(R.id.btn_login_register);
        btn_confirm = findViewById(R.id.btn_confirm_register);
        et_username = findViewById(R.id.et_username_register);
        et_password = findViewById(R.id.et_password_register);
        et_email    = findViewById(R.id.et_email_register);

        btn_logIn.setOnClickListener(view -> {
            openLoginActivity();
        });

        btn_confirm.setOnClickListener(view -> {
            createAccount();
        });

    }
    public void createAccount(){
        try {
            accountService.register(getApplicationContext(),et_email.getText().toString(), et_password.getText().toString(), et_username.getText().toString(), result -> {
                Log.d(Constants.TAG, result.toString());
                openMembersActivity();
            });
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(RegisterActivity.this, "Failed to sign you up", Toast.LENGTH_LONG).show();
        }
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