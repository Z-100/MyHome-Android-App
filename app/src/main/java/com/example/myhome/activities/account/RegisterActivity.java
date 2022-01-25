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

/**
 * @author Rad14nt
 * Class used to Display Register Page and get/use the given data
 * */

public class RegisterActivity extends AppCompatActivity {
    private EditText et_username, et_password, et_email;
    private AccountService accountService = new AccountService();
    private JSONArray accountNames = new JSONArray();
    private SharedPreferences sp;

    /**
     * Method used to create and start the view
     *
     * @param savedInstanceState Standard bundle to start creation of the view
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Button btn_logIn = findViewById(R.id.btn_login_register);
        Button btn_confirm = findViewById(R.id.btn_confirm_register);
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

    /**
     * Method to create an account/ register
     */
    public void createAccount(){
        try {
            accountService.register(getApplicationContext(),et_email.getText().toString(), et_password.getText().toString(), et_username.getText().toString(), result -> {
                sp = getSharedPreferences(Constants.SHAREDPREFNAME, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.putString(Constants.EMAIL, et_email.getText().toString());
                editor.putString(Constants.TOKEN,  result.getString(Constants.TOKEN));
                editor.commit();

                getMembersFromApi();
            });
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(RegisterActivity.this, Constants.REGISTERERROR, Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Method to open the login activity
     */
    public void openLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    /**
     * Method to get Members from the account the user just created
     */
    public void getMembersFromApi(){
        SharedPreferences sp = getApplicationContext().getSharedPreferences(Constants.SHAREDPREFNAME, Context.MODE_PRIVATE);
        String email = sp.getString(Constants.EMAIL, Constants.EMPTYSTRING);
        String token = sp.getString(Constants.TOKEN,Constants.EMPTYSTRING);
        try {
            accountService.getMembers(getApplicationContext(), email, token, result -> {
                parseMemberNamesAndImages(result);
                openMembersActivity(toStringArray(accountNames));
            });
        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(RegisterActivity.this, Constants.MEMBERFAILEDERROR, Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Method to open the members Activity after registering
     * @param members the list of members to be dispalyed on the member Activity
     */
    public void openMembersActivity(String[] members) {
        Intent intent = new Intent(this, MembersActivity.class);
        intent.putExtra(Constants.MEMBER, members);
        startActivity(intent);
    }

    /**
     * Method parse Member name from the incoming jsonArray
     * @param members tje array of members to be parsed
     * @throws JSONException
     */
    public void parseMemberNamesAndImages(JSONArray members) throws JSONException {
        for (int i = 0; i < members.length(); i++) {
            JSONObject member = members.getJSONObject(i);
            this.accountNames.put(member.getString(Constants.NAME));
        }
    }

    /**
     * Method to turn Array into String List to be able to display them on front end
     * @param array array to be converted into String List
     * @return List of Strings to display in the next activity
     */
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