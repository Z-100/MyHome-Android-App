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

/**
 * @author Rad14nt
 * Class used to Display Overview Page and get/use the necessary data
 * */

public class OverviewActivity extends AppCompatActivity {
    private AccountService accountService = new AccountService();
    private JSONArray accountNames = new JSONArray();
    private AlertDialog dialog;
    private int currentMemberId;
    private EditText newMemberName;
    private String[] roomNames;
    private int[] roomImages = {};


    /**
     * Method used to create and start the view
     *
     * @param savedInstanceState Standard bundle to start creation of the view
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        roomNames = getIntent().getStringArrayExtra(Constants.ROOMNAMES);
        roomImages = getIntent().getIntArrayExtra(Constants.ROOMICONS);
        com.example.myhome.databinding.ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setContentView(R.layout.activity_overview);
        currentMemberId =  getIntent().getIntExtra(Constants.CURRENTMEMBERID, 0);
        ImageButton btn_openMembers = findViewById(R.id.btn_openMembers_overview);
        ImageButton btn_openItems = findViewById(R.id.btn_openItems_overview);
        Button btn_openKitchen = findViewById(R.id.btn_openKitchenActivity_overview);
        ImageButton btn_deleteMember = findViewById(R.id.btn_deleteAccount_overview);
        ImageButton btn_editMember = findViewById(R.id.btn_editMember_overview);
        ListView listView = findViewById(R.id.rooms_listview);

        CustomAdapter customAdapter = new CustomAdapter();
        listView.setAdapter(customAdapter);
        TextView mTextView = (TextView) findViewById(R.id.txt_name_overview);
        SharedPreferences sp = getApplicationContext().getSharedPreferences(Constants.SHAREDPREFNAME, Context.MODE_PRIVATE);
        mTextView.setText(sp.getString(Constants.LOGGEDACCOUNT, Constants.EMPTYSTRING));


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            /**
             * clickListener, enables clicking on a room to continue to a room page where more details are to be shown
             * @param adapterView adapterview needed to generate the onItemClick method
             * @param view the current view we are on
             * @param i  position of item that was clicked
             * @param l  position required by onItemClick
             */
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), RoomActivity.class);
                intent.putExtra(Constants.ROOMNAME, roomNames[i]);
                intent.putExtra(Constants.ROOMIMAGE, roomImages[i]);
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

    /**
     * Method used to open Members activity and get the members activity the data it requires
     */
    public void openMembersActivityAndGetMembers(){
        SharedPreferences sp = getApplicationContext().getSharedPreferences(Constants.SHAREDPREFNAME, Context.MODE_PRIVATE);
        String email = sp.getString(Constants.EMAIL, Constants.EMPTYSTRING);
        String token = sp.getString(Constants.TOKEN, Constants.EMPTYSTRING);
        try {
            accountService.getMembers(getApplicationContext(), email, token, result -> {
                parseMemberNamesAndImages(result);
                openMembersActivity(toStringArray(accountNames));
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Method used to parse Members from their original object
     * @param members
     * @throws JSONException
     */
    public void parseMemberNamesAndImages(JSONArray members) throws JSONException {
        for (int i = 0; i < members.length(); i++) {
            JSONObject member = members.getJSONObject(i);
            this.accountNames.put(member.getString(Constants.NAME));
        }
    }

    /**
     * Method used to open Members Activity
     * @param members
     */
    public void openMembersActivity(String[] members) {
        Intent intent = new Intent(this, MembersActivity.class);
        intent.putExtra(Constants.MEMBER, members);
        startActivity(intent);
    }

    /**
     * Method to open Items Activity
     */
    public void openItemsActivity(){
        Intent intent = new Intent(this, ItemsActivity.class);
        startActivity(intent);
    }

    /**
     * Method used to open kitchen Activity
     */
    public void openKitchenActivity(){
        Intent intent = new Intent(this, KitchenActivity.class);
        startActivity(intent);
    }

    /**
     * Method used to delete account an account, calls to open Member Activity after
     * @throws JSONException throws a JSON Exception
     */
    public void deleteAccount() throws JSONException {
        SharedPreferences sp = getApplicationContext().getSharedPreferences(Constants.SHAREDPREFNAME, Context.MODE_PRIVATE);
        accountService.removeMember(OverviewActivity.this, sp.getString(Constants.EMAIL, Constants.EMPTYSTRING), sp.getString(Constants.TOKEN, Constants.EMPTYSTRING), String.valueOf(currentMemberId), result -> {
            openMembersActivityAndGetMembers();
        });
    }

    /**
     * Method used to create a popup to create a new Member
     * @param context
     */
    public void createNewMemberDialog(Context context){
        SharedPreferences sp = context.getSharedPreferences(Constants.SHAREDPREFNAME, Context.MODE_PRIVATE);
        AlertDialog.Builder dialogbuilder = new AlertDialog.Builder(this);
        final View editMemberView = getLayoutInflater().inflate(R.layout.edit_member, null);
        newMemberName = (EditText) editMemberView.findViewById(R.id.et_newMemberPopup_Name);
        Button editedMemberSaveButton = (Button) editMemberView.findViewById(R.id.btn_saveButton_editMember);
        Button editedMemberCancelButton = (Button) editMemberView.findViewById(R.id.btn_cancelButton_editMember);


        dialogbuilder.setView(editMemberView);
        dialog = dialogbuilder.create();
        dialog.show();

        editedMemberSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            /** Method used to save the edit data on user edit
             * @param v
             */
            public void onClick(View v){
                try {
                    accountService.updateMember(OverviewActivity.this, sp.getString(Constants.EMAIL, Constants.EMPTYSTRING), sp.getString(Constants.TOKEN, Constants.EMPTYSTRING), String.valueOf(currentMemberId), Constants.STANDARDICON, newMemberName.getText().toString(), result -> {
                        Toast.makeText(OverviewActivity.this, Constants.SUCCESSFULMEMBERUPDATE, Toast.LENGTH_LONG).show();
                        dialog.dismiss();
                        openOverviewActivity(OverviewActivity.this, newMemberName.getText().toString(), roomNames, roomImages, currentMemberId);
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(OverviewActivity.this, Constants.FAILEDMEMBERUPDATE, Toast.LENGTH_LONG).show();
                }
            }
        });
        editedMemberCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            /** Method used to dismiss the popup
             * @param v
             */
            public void onClick(View v){
                dialog.dismiss();
            }
        });
    }

    /**
     * Method used to open OverviewActivity and give it all the data it needs to generate its items
     * @param context the current Activity Context
     * @param name the name of the user we clicked on
     * @param roomnames the names of the rooms that are connected to the account
     * @param roomicons the list of icons that belong to the rooms
     * @param id the id of the current member that was clicked
     */
    public void openOverviewActivity(Context context, String name,String[] roomnames, int[]roomicons, int id) {
        SharedPreferences sp = context.getSharedPreferences(Constants.SHAREDPREFNAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(Constants.LOGGEDACCOUNT, name);
        editor.commit();
        Intent intent = new Intent(this, OverviewActivity.class);
        intent.putExtra(Constants.CURRENTMEMBERID, id);
        intent.putExtra(Constants.ROOMNAMES, roomnames);
        intent.putExtra(Constants.ROOMICONS, roomicons);
        startActivity(intent);
    }

    /**
     * @author Rad14nt
     * Class with methods used to generate the Member buttons for the different members
     */
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