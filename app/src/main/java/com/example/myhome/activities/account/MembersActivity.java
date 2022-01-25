package com.example.myhome.activities.account;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.myhome.Constants;
import com.example.myhome.GridAdapter;
import com.example.myhome.InfoActivity;
import com.example.myhome.OverviewActivity;
import com.example.myhome.R;
import com.example.myhome.api.AccountService;
import com.example.myhome.api.RoomService;
import com.example.myhome.databinding.ActivityMainBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

public class MembersActivity extends AppCompatActivity {


    private AlertDialog dialog;
    private EditText newMemberName;
    private JSONArray accountNames = new JSONArray();
    private JSONArray roomIconsAsJson = new JSONArray();
    private JSONArray roomNamesAsJson = new JSONArray();
    private AccountService ac = new AccountService();
    private int[] accountImages = {R.drawable.avataaar1, R.drawable.avataaar2, R.drawable.avataaar3, R.drawable.avataaar4, R.drawable.avataaar5, R.drawable.avataaar6, R.drawable.avataaar7, R.drawable.avataaar8, R.drawable.avataaar9 ,R.drawable.avataaar10};
    private int currentId;
    private String[] accountName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        com.example.myhome.databinding.ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        try {
            parseRoomsToStringArray();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            getIconsFromApi();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Button btn_logout = findViewById(R.id.btn_logout_members);
        Button btn_newmemeber = findViewById(R.id.btn_newmember_members);
        ImageButton btn_infoButton = findViewById(R.id.btn_infoButton_members);
        accountName = getIntent().getStringArrayExtra(Constants.MEMBER);
        SharedPreferences sp = getApplicationContext().getSharedPreferences(Constants.SHAREDPREFNAME, Context.MODE_PRIVATE);

        GridAdapter gridAdapter = new GridAdapter(MembersActivity.this, accountName, accountImages);
        binding.gridView.setAdapter(gridAdapter);


        btn_logout.setOnClickListener(view -> {
            SharedPreferences.Editor editor = sp.edit();
            editor.putString(Constants.EMAIL, Constants.EMPTYSTRING);
            editor.putString(Constants.TOKEN, Constants.EMPTYSTRING);
            editor.commit();
            openLoginActivity();
        });

        btn_infoButton.setOnClickListener(view -> {
            openInfoActivity();
        });


        btn_newmemeber.setOnClickListener(view -> {
            createNewMemberDialog(MembersActivity.this);
        });

        binding.gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Context context = MembersActivity.this;
                SharedPreferences sp = context.getSharedPreferences(Constants.SHAREDPREFNAME, Context.MODE_PRIVATE);
                try {
                    ac.getMembers(context, sp.getString(Constants.EMAIL, Constants.EMPTYSTRING), sp.getString(Constants.TOKEN, Constants.EMPTYSTRING), result -> {
                        for (int i = 0; i < result.length(); i++) {
                            if (result.getJSONObject(i).getString(Constants.NAME).equals(accountName[position])){
                                currentId = result.getJSONObject(i).getInt(Constants.ID);
                                openOverviewActivity(getApplicationContext(),accountName[position], toStringArray(roomNamesAsJson), toIntArray(roomIconsAsJson), currentId);
                            }
                        }

                    } );
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.d(Constants.TAG, Constants.IDFAILEDERROR);
                }

            }
        });
    }
    public void openLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    public void openOverviewActivity(Context context, String accountName,String[] roomnames, int[]roomicons, int id) {
        SharedPreferences sp = context.getSharedPreferences(Constants.SHAREDPREFNAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(Constants.LOGGEDACCOUNT, accountName);
        editor.commit();
        Intent intent = new Intent(this, OverviewActivity.class);
        intent.putExtra(Constants.CURRENTMEMBERID, id);
        intent.putExtra(Constants.ROOMNAMES, roomnames);
        intent.putExtra(Constants.ROOMICONS, roomicons);
        startActivity(intent);
    }

    public void createNewMemberDialog(Context context){
        SharedPreferences sp = context.getSharedPreferences(Constants.SHAREDPREFNAME, Context.MODE_PRIVATE);
        AlertDialog.Builder dialogbuilder = new AlertDialog.Builder(this);
        final View memberPopupView = getLayoutInflater().inflate(R.layout.popup, null);
        newMemberName = (EditText) memberPopupView.findViewById(R.id.et_newMemberPopup_Name);
        Button newMemberSaveButton = (Button) memberPopupView.findViewById(R.id.btn_saveButton_newMember);
        Button newMemberCancelButton = (Button) memberPopupView.findViewById(R.id.btn_cancelButton_newMember);


        dialogbuilder.setView(memberPopupView);
        dialog = dialogbuilder.create();
        dialog.show();

        newMemberSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                try {
                    ac.insertMember(MembersActivity.this, sp.getString(Constants.EMAIL, Constants.EMPTYSTRING), sp.getString(Constants.TOKEN, Constants.EMPTYSTRING), newMemberName.getText().toString(), result -> {
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
                dialog.dismiss();
            }
        });
    }


    public void getMembersFromApi(){
        SharedPreferences sp = getApplicationContext().getSharedPreferences(Constants.SHAREDPREFNAME, Context.MODE_PRIVATE);
        String email = sp.getString(Constants.EMAIL, Constants.EMPTYSTRING);
        String token = sp.getString(Constants.TOKEN, Constants.EMPTYSTRING);
        try {
            ac.getMembers(getApplicationContext(), email, token, result -> {
                parseMemberNamesAndImages(result);
                openMembersActivity(toStringArray(accountNames));
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void parseMemberNamesAndImages(JSONArray members) throws JSONException {
        for (int i = 0; i < members.length(); i++) {
            JSONObject member = members.getJSONObject(i);
            this.accountNames.put(member.getString(Constants.NAME));
        }
    }


    public void openMembersActivity(String[] members) {
        Intent intent = new Intent(this, MembersActivity.class);
        intent.putExtra(Constants.MEMBER, members);
        startActivity(intent);
    }
    public void openInfoActivity() {
        Intent intent = new Intent(this, InfoActivity.class);
        intent.putExtra(Constants.MEMBER,accountName);
        startActivity(intent);
    }

    public void parseRoomsToStringArray() throws JSONException {
        RoomService roomService = new RoomService();

        SharedPreferences sp = getApplicationContext().getSharedPreferences(Constants.SHAREDPREFNAME, Context.MODE_PRIVATE);
        roomService.getRooms(MembersActivity.this,
                sp.getString(Constants.EMAIL, Constants.EMPTYSTRING), sp.getString(Constants.TOKEN, Constants.EMPTYSTRING), result -> {
            for (int i = 0; i < result.length(); i++){
                this.roomNamesAsJson.put(result.getJSONObject(i).getString(Constants.NAME));
            }
        });
    }

    public int[] getIconsFromApi() throws JSONException {
        RoomService roomService = new RoomService();
        SharedPreferences sp = getApplicationContext().getSharedPreferences(Constants.SHAREDPREFNAME, Context.MODE_PRIVATE);
        roomService.getRooms(MembersActivity.this ,sp.getString(Constants.EMAIL, Constants.EMPTYSTRING), sp.getString(Constants.TOKEN, Constants.EMPTYSTRING), result -> {

            for (int i = 0; i < result.length(); i++)
            {
                this.roomIconsAsJson.put(result.getJSONObject(i).getInt(Constants.ICON));
            }
        });
        Log.d(Constants.TAG, roomIconsAsJson.toString());
        return toIntArray(roomIconsAsJson);
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

    public static int[] toIntArray(JSONArray array) {
        if(array==null)
            return null;

        int[] arr=new int[array.length()];
        for(int i=0; i<arr.length; i++) {
            arr[i]=array.optInt(i);
        }
        return arr;
    }


}