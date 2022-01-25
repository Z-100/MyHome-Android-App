package com.example.myhome.activities.account;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myhome.Constants;
import com.example.myhome.R;
import com.example.myhome.api.AccountService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {
    private Button btn_logIn, btn_confirm;
    private EditText et_username, et_password, et_email;
    private AccountService accountService = new AccountService();
    private JSONArray accountNames = new JSONArray();
    private SharedPreferences sp;

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
                sp = getSharedPreferences("MyUserPrefs", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.putString("email", et_email.getText().toString());
                editor.putString("token",  result.getString("token"));
                editor.commit();

                getMembersFromApi();
            });
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(RegisterActivity.this, "Failed to sign you up", Toast.LENGTH_LONG).show();
        }
    }


    public void openLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
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
            Toast.makeText(RegisterActivity.this, "Failed to get Members", Toast.LENGTH_LONG).show();
        }
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