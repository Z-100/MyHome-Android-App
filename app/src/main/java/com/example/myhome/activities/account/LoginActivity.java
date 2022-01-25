package com.example.myhome.activities.account;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myhome.models.Member;
import com.example.myhome.services.LoginService;
import com.example.myhome.R;
import com.example.myhome.models.Token;
import com.example.myhome.services.RequestMembersService;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class LoginActivity extends AppCompatActivity {
    FloatingActionButton btn_signIn;
    Button btn_signUp, btn_forgotPassword;
    EditText et_email, et_password;
    String username, password;
    SharedPreferences sp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btn_signIn         =    findViewById(R.id.btn_log_in_login);
        btn_signUp         =    findViewById(R.id.btn_sign_up_login);
        btn_forgotPassword =    findViewById(R.id.btn_forgot_password_login);
        et_email           =    findViewById(R.id.et_username_login);
        et_password        =    findViewById(R.id.et_password_login);


        btn_signIn.setOnClickListener(view -> {
            saveToken(getLoginToken(et_email.getText().toString(), et_password.getText().toString()), et_email.getText().toString());

                openMembersActivity();

        });

        btn_signUp.setOnClickListener(view -> {
            openRegisterActivity();
        });

        btn_forgotPassword.setOnClickListener(view -> {
            openResetPasswordActivity();
        });
    }

    public void saveToken(String token, String email){
        sp = getSharedPreferences("MyUserPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("token", token);
        editor.putString("email", email);
        editor.commit();
        Toast.makeText(LoginActivity.this, "Information successfully saved", Toast.LENGTH_LONG).show();
    }

    public String getLoginToken(String email, String password){
        final String uri = "http://10.10.21.139:8080/getAcc";
        try {
            Token token = new LoginService().execute(uri, email, password).get().getBody();
            return token.getToken();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Member> getMembers(){
        List<Member> members = new ArrayList<>();
        SharedPreferences sp = getApplicationContext().getSharedPreferences("MyUserPrefs", Context.MODE_PRIVATE);
        String email = sp.getString("email", "");
        String token = sp.getString("token", "");


        final String uri = "http://10.10.21.139:8080/getMembers";
        try {
            members = new RequestMembersService().execute(uri, email, token).get().getBody();
        }catch (InterruptedException | ExecutionException e){
            e.printStackTrace();
            return null;
        }

        return members;
    }


    public List<Member> getMembersAutomatically(){
        List<Member> members = new ArrayList<>();


        return members;
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