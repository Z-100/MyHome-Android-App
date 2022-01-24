package com.example.myhome.activities.account;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myhome.GridAdapter;
import com.example.myhome.OverviewActivity;
import com.example.myhome.R;
import com.example.myhome.api.AccountService;
import com.example.myhome.databinding.ActivityMainBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MembersActivity extends AppCompatActivity {


    private AlertDialog.Builder dialogbuilder;
    private AlertDialog dialog;
    private EditText newMemberName;
    private Button newMemberSaveButton, newMemberCancelButton;
    private JSONArray accountNames = new JSONArray();

    private Button btn_logout, btn_newmemeber;
    private ActivityMainBinding binding;
    private AccountService ac = new AccountService();
    int[] accountImages = {R.drawable.avataaar1, R.drawable.avataaar2, R.drawable.avataaar3, R.drawable.avataaar4, R.drawable.avataaar5, R.drawable.avataaar6, R.drawable.avataaar7, R.drawable.avataaar8, R.drawable.avataaar9 ,R.drawable.avataaar10};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        btn_logout = findViewById(R.id.btn_logout_members);
        btn_newmemeber = findViewById(R.id.btn_newmember_members);
        String[] accountName = getIntent().getStringArrayExtra("Members");
        SharedPreferences sp = getApplicationContext().getSharedPreferences("MyUserPrefs", Context.MODE_PRIVATE);

        GridAdapter gridAdapter = new GridAdapter(MembersActivity.this, accountName, accountImages);
        binding.gridView.setAdapter(gridAdapter);


        btn_logout.setOnClickListener(view -> {
            SharedPreferences.Editor editor = sp.edit();
            editor.putString("email", "");
            editor.putString("token", "");
            editor.commit();
            openLoginActivity();
        });


        btn_newmemeber.setOnClickListener(view -> {
            createNewMemberDialog(MembersActivity.this);
        });

        binding.gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                openOverviewActivity(getApplicationContext(),accountName[position]);

            }
        });
    }
    public void openLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    public void openOverviewActivity(Context context, String accountName) {
        SharedPreferences sp = context.getSharedPreferences("MyUserPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("loggedAccount", accountName);
        editor.commit();
        Intent intent = new Intent(this, OverviewActivity.class);
        startActivity(intent);
    }

    public void createNewMemberDialog(Context context){
        SharedPreferences sp = context.getSharedPreferences("MyUserPrefs", Context.MODE_PRIVATE);
        dialogbuilder = new AlertDialog.Builder(this);
        final View memberPopupView = getLayoutInflater().inflate(R.layout.popup, null);
        newMemberName = (EditText) memberPopupView.findViewById(R.id.et_newMemberPopup_Name);
        newMemberSaveButton = (Button) memberPopupView.findViewById(R.id.btn_saveButton_newMember);
        newMemberCancelButton = (Button) memberPopupView.findViewById(R.id.btn_cancelButton_newMember);


        dialogbuilder.setView(memberPopupView);
        dialog = dialogbuilder.create();
        dialog.show();

        newMemberSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                try {
                    ac.insertMember(MembersActivity.this, sp.getString("email", ""), sp.getString("token", ""), newMemberName.getText().toString(), result -> {
                        Toast.makeText(MembersActivity.this, "Success", Toast.LENGTH_LONG).show();
                        dialog.dismiss();
                        getMembersFromApi();
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        newMemberCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                //define cancel button
                dialog.dismiss();
            }
        });
    }



    public void getMembersFromApi(){
        SharedPreferences sp = getApplicationContext().getSharedPreferences("MyUserPrefs", Context.MODE_PRIVATE);
        String email = sp.getString("email", "");
        String token = sp.getString("token", "");
        try {
            ac.getMembers(getApplicationContext(), email, token, result -> {
                parseMemberNamesAndImages(result);
                openMembersActivity(toStringArray(accountNames));
            });
        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(MembersActivity.this, "Failed to get Members", Toast.LENGTH_LONG).show();
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