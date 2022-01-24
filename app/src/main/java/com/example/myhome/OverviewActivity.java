package com.example.myhome;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myhome.activities.account.MembersActivity;
import com.example.myhome.activities.items.ItemsActivity;
import com.example.myhome.activities.kitchen.KitchenActivity;
import com.example.myhome.api.AccountService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class OverviewActivity extends AppCompatActivity {
    ImageButton btn_openMembers, btn_openItems;
    AccountService accountService = new AccountService();
    JSONArray accountNames = new JSONArray();
    Button btn_openKitchen;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview);
        btn_openMembers     =    findViewById(R.id.btn_openMembers_overview);
        btn_openItems       =    findViewById(R.id.btn_openItems_overview);
        btn_openKitchen     =    findViewById(R.id.btn_openKitchenActivity_overview);
        TextView mTextView = (TextView) findViewById(R.id.txt_name_overview);
        SharedPreferences sp = getApplicationContext().getSharedPreferences("MyUserPrefs", Context.MODE_PRIVATE);
        mTextView.setText(sp.getString("loggedAccount", ""));

        btn_openMembers.setOnClickListener(view -> {
             openMembersActivityAndGetMembers();
        });
        btn_openItems.setOnClickListener(view -> {
            openItemsActivity();
        });

        btn_openKitchen.setOnClickListener(view -> {
            openKitchenActivity();
        });
    }


    public void openMembersActivityAndGetMembers(){
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
            Toast.makeText(OverviewActivity.this, "Failed to get Members", Toast.LENGTH_LONG).show();
        }
    }
    public void parseMemberNamesAndImages(JSONArray members) throws JSONException {
        for (int i = 0; i < members.length(); i++) {
            JSONObject member = members.getJSONObject(i);
            this.accountNames.put(member.getString("name"));
        }
    }
    public void openMembersActivity(String[] members) {
        Intent intent = new Intent(this, MembersActivity.class);
        intent.putExtra("Members", members);
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

    public void openItemsActivity(){
        Intent intent = new Intent(this, ItemsActivity.class);
        startActivity(intent);
    }

    public void openKitchenActivity(){
        Intent intent = new Intent(this, KitchenActivity.class);
        startActivity(intent);
    }

}