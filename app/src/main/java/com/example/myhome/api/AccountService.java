package com.example.myhome.api;


import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.myhome.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AccountService {

    public static final Map<String, String> header = new HashMap<>();

    public interface apiObjectSuccessHandler {
        void handle(JSONObject result) throws JSONException;
    }

    public interface apiStringSuccessHandler {
        void handle(String result) throws JSONException;
    }

    public interface apiArraySuccessHandler {
        void handle(JSONArray result) throws JSONException;
    }


    public void getLoginToken(Context context,String email, String password, apiObjectSuccessHandler callback) throws JSONException {
        RequestQueue queue = Volley.newRequestQueue(context);
        JSONObject data = new JSONObject();

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                "http://192.168.8.91:8080/account/login",
                data,
                response -> {
                    Log.d(Constants.TAG, response.toString());
                    try {
                        callback.handle(response);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> {
                    Toast.makeText(context, "Invalid Credentials" + error, Toast.LENGTH_LONG).show();
                }) {

            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<String, String>();
                params.put(Constants.EMAIL, email);
                params.put(Constants.PASSWORD, password);
                params.put(Constants.TOKEN, "MAHANSH MUTEM blyat suk my dik");
                return params;
            }
        };
        queue.add(request);
    }

    public void register(Context context,String email, String password, String defaultMemberName, apiObjectSuccessHandler callback) throws JSONException {
        RequestQueue queue = Volley.newRequestQueue(context);
        JSONObject data = new JSONObject();

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                "http://192.168.8.91:8080/account/register",
                data,
                response -> {
                    Log.d(Constants.TAG,response.toString());
                    try {
                        callback.handle(response);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> {
                    Toast.makeText(context, "Invalid Credentials", Toast.LENGTH_LONG).show();
                }) {

            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<String, String>();
                params.put(Constants.EMAIL, email);
                params.put(Constants.PASSWORD, password);
                params.put("newHouseName", "Home");
                params.put("defaultMemberName", defaultMemberName);
                params.put(Constants.TOKEN, "ma-ta este super dracu, blyat");
                return params;
            }
        };
        queue.add(request);
    }

    public void getMembers(Context context, String email, String token, apiArraySuccessHandler callback) throws JSONException {
        RequestQueue queue = Volley.newRequestQueue(context);
        JSONArray data = new JSONArray();

        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.GET,
                "http://192.168.8.91:8080/member/get-member",
                data,
                response -> {
                    try {
                        callback.handle(response);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> {
                    Log.d(Constants.TAG, error.toString());
                }) {

            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<String, String>();
                Log.d(Constants.TAG, "using email: " + email + " and  token : " + token);
                params.put(Constants.EMAIL, email);
                params.put(Constants.TOKEN, token);

                return params;
            }
        };
        queue.add(request);
    }


    public void removeMember(Context context, String email, String token, String memberid, apiObjectSuccessHandler callback) throws JSONException {
        RequestQueue queue = Volley.newRequestQueue(context);
        JSONObject data = new JSONObject();

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                "http://192.168.8.91:8080/member/delete-member",
                data,
                response -> {
                    try {
                        callback.handle(response);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> {
                    Log.d(Constants.TAG, error.toString());
                    Toast.makeText(context, "Failed to delete Member", Toast.LENGTH_LONG).show();
                }) {

            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<String, String>();
                params.put(Constants.EMAIL, email);
                params.put(Constants.TOKEN, token);
                params.put(Constants.MEMBERID, memberid);
                return params;
            }
        };
        queue.add(request);
    }

    public void insertMember(Context context, String email, String token, String name, apiObjectSuccessHandler callback) throws JSONException {
        RequestQueue queue = Volley.newRequestQueue(context);
        JSONObject data = new JSONObject();

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                "http://192.168.8.91:8080/member/insert-member",
                data,
                response -> {
                    try {
                        callback.handle(response);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> {
                    Log.d(Constants.TAG, error.toString());
                    Toast.makeText(context, "Failed to insert Member", Toast.LENGTH_LONG).show();
                }) {

            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<String, String>();
                params.put(Constants.EMAIL, email);
                params.put(Constants.TOKEN, token);
                params.put(Constants.ICON, "3");
                params.put(Constants.NAME, name);
                return params;
            }
        };
        queue.add(request);
    }

    public void updateMember(Context context, String email, String token, String member_id, String replacement_icon, String replacement_name, apiObjectSuccessHandler callback) throws JSONException {
        RequestQueue queue = Volley.newRequestQueue(context);
        JSONObject data = new JSONObject();

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                "http://192.168.8.91:8080/member/update-member",
                data,
                response -> {
                    try {
                        callback.handle(response);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> {
                    Log.d(Constants.TAG, error.toString());
                    Toast.makeText(context, "Failed to update Member", Toast.LENGTH_LONG).show();
                }) {

            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<String, String>();
                params.put(Constants.EMAIL, email);
                params.put(Constants.TOKEN, token);
                params.put(Constants.MEMBERID, member_id);
                params.put(Constants.REPLACEMENTICON, replacement_icon);
                params.put(Constants.REPLACEMENTNAME, replacement_name);
                return params;
            }
        };
        queue.add(request);
    }

    public void resetPassword(){

    }



}
