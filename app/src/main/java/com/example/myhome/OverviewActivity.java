package com.example.myhome;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myhome.activities.account.MembersActivity;
import com.example.myhome.activities.items.ItemsActivity;
import com.example.myhome.activities.kitchen.KitchenActivity;
import com.example.myhome.activities.rooms.RoomActivity;
import com.example.myhome.api.AccountService;
import com.example.myhome.api.RoomService;
import com.example.myhome.databinding.ActivityMainBinding;
import com.example.myhome.models.Room;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class OverviewActivity extends AppCompatActivity {
    private ImageButton btn_openMembers, btn_openItems, btn_deleteMember, btn_editMember;
    private AccountService accountService = new AccountService();
    private JSONArray accountNames = new JSONArray();
    private AlertDialog dialog;
    private int currentMemberId;
    private EditText newMemberName;
    private ActivityMainBinding binding;
    private Button editedMemberSaveButton, editedMemberCancelButton, btn_openKitchen;
    private AlertDialog.Builder dialogbuilder;
    private ListView listView;
    private String[] roomNames;
    private int[] roomImages = {};
    private int roomImage;

    public OverviewActivity() throws JSONException {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        roomNames = getIntent().getStringArrayExtra("roomNames");
        roomImages = getIntent().getIntArrayExtra("roomIcons");
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setContentView(R.layout.activity_overview);
        currentMemberId =  getIntent().getIntExtra("currentMemberId", 0);
        btn_openMembers     =    findViewById(R.id.btn_openMembers_overview);
        btn_openItems       =    findViewById(R.id.btn_openItems_overview);
        btn_openKitchen     =    findViewById(R.id.btn_openKitchenActivity_overview);
        btn_deleteMember    =    findViewById(R.id.btn_deleteAccount_overview);
        btn_editMember      =    findViewById(R.id.btn_editMember_overview);
        listView            =    findViewById(R.id.rooms_listview);

        CustomAdapter customAdapter = new CustomAdapter();
        listView.setAdapter(customAdapter);
        TextView mTextView = (TextView) findViewById(R.id.txt_name_overview);
        SharedPreferences sp = getApplicationContext().getSharedPreferences(Constants.SHAREDPREFNAME, Context.MODE_PRIVATE);
        mTextView.setText(sp.getString("loggedAccount", ""));


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), RoomActivity.class);
                intent.putExtra("roomname", roomNames[i]);
                intent.putExtra("roomimage", roomImage);
                startActivity(intent);
            }
        });
        btn_openMembers.setOnClickListener(view -> {
            openMembersActivityAndGetMembers();
        });
        btn_openItems.setOnClickListener(view -> {
            openItemsActivity();
        });

        btn_openKitchen.setOnClickListener(view -> {
            openKitchenActivity();
        });
        btn_deleteMember.setOnClickListener(view -> {
            try {
                deleteAccount();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });
        btn_editMember.setOnClickListener(view -> {
            createNewMemberDialog(OverviewActivity.this);
        });
    }


    public void openMembersActivityAndGetMembers(){
        SharedPreferences sp = getApplicationContext().getSharedPreferences(Constants.SHAREDPREFNAME, Context.MODE_PRIVATE);
        String email = sp.getString(Constants.EMAIL, "");
        String token = sp.getString(Constants.TOKEN, "");
        try {
            accountService.getMembers(getApplicationContext(), email, token, result -> {
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

    public void openItemsActivity(){
        Intent intent = new Intent(this, ItemsActivity.class);
        startActivity(intent);
    }

    public void openKitchenActivity(){
        Intent intent = new Intent(this, KitchenActivity.class);
        startActivity(intent);
    }

    public void deleteAccount() throws JSONException {
        SharedPreferences sp = getApplicationContext().getSharedPreferences(Constants.SHAREDPREFNAME, Context.MODE_PRIVATE);
        accountService.removeMember(OverviewActivity.this, sp.getString(Constants.EMAIL, ""), sp.getString(Constants.TOKEN, ""), String.valueOf(currentMemberId), result -> {
            openMembersActivityAndGetMembers();
        });
    }



    public void createNewMemberDialog(Context context){
        SharedPreferences sp = context.getSharedPreferences(Constants.SHAREDPREFNAME, Context.MODE_PRIVATE);
        dialogbuilder = new AlertDialog.Builder(this);
        final View editMemberView = getLayoutInflater().inflate(R.layout.edit_member, null);
        newMemberName = (EditText) editMemberView.findViewById(R.id.et_newMemberPopup_Name);
        editedMemberSaveButton = (Button) editMemberView.findViewById(R.id.btn_saveButton_editMember);
        editedMemberCancelButton = (Button) editMemberView.findViewById(R.id.btn_cancelButton_editMember);


        dialogbuilder.setView(editMemberView);
        dialog = dialogbuilder.create();
        dialog.show();

        editedMemberSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                try {
                    accountService.updateMember(OverviewActivity.this, sp.getString("email", ""), sp.getString("token", ""), String.valueOf(currentMemberId), "3", newMemberName.getText().toString(), result -> {
                        Toast.makeText(OverviewActivity.this, "Successfully updated", Toast.LENGTH_LONG).show();
                        dialog.dismiss();
                        resetActivity(OverviewActivity.this, newMemberName.getText().toString());
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        editedMemberCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                dialog.dismiss();
            }
        });
    }
    public void resetActivity(Context context, String name){
        SharedPreferences sp = context.getSharedPreferences("MyUserPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("loggedAccount", name);
        editor.commit();
        Intent intent = new Intent(this, OverviewActivity.class);
        intent.putExtra("currentMemberId", currentMemberId);
        startActivity(intent);
    }

    private class CustomAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return roomNames.length;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            View view1 = getLayoutInflater().inflate(R.layout.room_data, null);
            TextView name = view1.findViewById(R.id.roomname);
            ImageView imageView = view1.findViewById(R.id.roomimages);

            name.setText(roomNames[i]);
            if (roomImages[i] == 0 ){
                imageView.setImageResource(R.drawable.kitchen);
            } else if (roomImages[i] == 1) {
                imageView.setImageResource(R.drawable.bedroom);
            }else if (roomImages[i] == 2) {
                imageView.setImageResource(R.drawable.living);
            }else if (roomImages[i] == 3) {
                imageView.setImageResource(R.drawable.bathroom);
            }
            else if (roomImages[i] == 5) {
                imageView.setImageResource(R.drawable.garage);
            }
            return view1;
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