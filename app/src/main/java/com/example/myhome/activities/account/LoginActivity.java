package com.example.myhome.activities.account;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myhome.Constants;
import com.example.myhome.api.AccountService;
import com.example.myhome.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.api.LogDescriptor;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class LoginActivity extends AppCompatActivity {
    FloatingActionButton btn_signIn;
    Button btn_signUp, btn_forgotPassword;
    EditText et_email, et_password;
    SharedPreferences sp;
    AccountService accountService = new AccountService();
    JSONArray accountNames = new JSONArray();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btn_signIn         =    findViewById(R.id.btn_log_in_login);
        btn_signUp         =    findViewById(R.id.btn_sign_up_login);
        btn_forgotPassword =    findViewById(R.id.btn_forgot_password_login);
        et_email           =    findViewById(R.id.et_username_login);
        et_password        =    findViewById(R.id.et_password_login);
        sp = getSharedPreferences("MyUserPrefs", Context.MODE_PRIVATE);

        //TODO remove this stuff when login works again
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("token", "sananas");
        editor.commit();
        //till here


        if (!sp.getString("token", "").equals("")){
            getMembersFromApi();
        }

        btn_signIn.setOnClickListener(view -> {
            getAndSaveToken(et_email.getText().toString(), et_password.getText().toString());

        });
        btn_signUp.setOnClickListener(view -> {
            openRegisterActivity();
        });

        btn_forgotPassword.setOnClickListener(view -> {
            openResetPasswordActivity();
        });
    }



    public void getAndSaveToken(String email, String password){
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("email", email);
        editor.putString("password", password);
        editor.commit();
        try {
            accountService.getLoginToken(getApplicationContext(),email, password, result ->{
                Log.d(Constants.TAG, "String result for token " +   result.getString("token"));
                editor.putString("token", result.getString("token"));
                editor.commit();
                Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_LONG).show();
                getMembersFromApi();
            });
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(LoginActivity.this, "Failed to log in", Toast.LENGTH_LONG).show();
        }
    }

    public void getMembersFromApi(){
        SharedPreferences sp = getApplicationContext().getSharedPreferences("MyUserPrefs", Context.MODE_PRIVATE);
        String email = sp.getString("email", "");
        String token = sp.getString("token", "");
        try {
            accountService.getMembers(getApplicationContext(), email, token, result -> {
                parseMemberNamesAndImages(result);
                openMembersActivity(toStringArray(accountNames));
            });
        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(LoginActivity.this, "Failed to get Members", Toast.LENGTH_LONG).show();
        }
    }

    public void openResetPasswordActivity() {

    }

    public void openMembersActivity(String[] members) {
        Intent intent = new Intent(this, MembersActivity.class);
        intent.putExtra("Members", members);
        startActivity(intent);
    }
    public void parseMemberNamesAndImages(JSONArray members) throws JSONException {
        for (int i = 0; i < members.length(); i++) {
            JSONObject member = members.getJSONObject(i);
            this.accountNames.put(member.getString("name"));
        }
    }

    public void openRegisterActivity() {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    public static String[] toStringArray(JSONArray array) {
        if(array==null)
            return null;

        String[] arr=new String[array.length()];
        for(int i=0; i<arr.length; i++) {
            arr[i]=array.optString(i);
        }
        return arr;
    }



}