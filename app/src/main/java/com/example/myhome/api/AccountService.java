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

/**
 * @author Rad14nt
 * Class used to hold the services that call to the account endpoint of the API
 * */

public class AccountService {

    private static final Map<String, String> header = new HashMap<>();
    /**
     * handler for sucessful response of type object
     */
    public interface apiObjectSuccessHandler {
        void handle(JSONObject result) throws JSONException;
    }

    /**
     * handler for sucessful response of type array
     */
    public interface apiArraySuccessHandler {
        void handle(JSONArray result) throws JSONException;
    }

    /**
     *
     * @param context context of current app
     * @param email email of the logged in user
     * @param password password of the logged in user
     * @param callback successhandler
     * @throws JSONException
     */
    public void getLoginToken(Context context,String email, String password, apiObjectSuccessHandler callback) throws JSONException {
        RequestQueue queue = Volley.newRequestQueue(context);
        JSONObject data = new JSONObject();

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                Constants.BASE_URL+ "account/login",
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
                    Toast.makeText(context, Constants.INVALIDCREDENTIALSERROR + error, Toast.LENGTH_LONG).show();
                }) {

            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<String, String>();
                params.put(Constants.EMAIL, email);
                params.put(Constants.PASSWORD, password);
                params.put(Constants.TOKEN, Constants.REGISTERTOKEN);
                return params;
            }
        };
        queue.add(request);
    }

    /**
     *
     * @param context context of current app
     * @param email email of the logged in user
     * @param password password of the logged in user
     * @param defaultMemberName a default member name that is the username
     * @param callback successhandler
     * @throws JSONException
     */
    public void register(Context context,String email, String password, String defaultMemberName, apiObjectSuccessHandler callback) throws JSONException {
        RequestQueue queue = Volley.newRequestQueue(context);
        JSONObject data = new JSONObject();

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                Constants.BASE_URL+ "account/register",
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
                    Toast.makeText(context, Constants.REGISTERERROR, Toast.LENGTH_LONG).show();
                }) {

            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<String, String>();
                params.put(Constants.EMAIL, email);
                params.put(Constants.PASSWORD, password);
                params.put(Constants.NEWHOUSENAME, Constants.DEFAULTHOMENAME);
                params.put(Constants.DEFAULTMEMBERNAME, defaultMemberName);
                params.put(Constants.TOKEN, Constants.LOGINTOKEN);
                return params;
            }
        };
        queue.add(request);
    }

    /**
     *
     * @param context context of current app
     * @param email email of the logged in user
     * @param token token of the logged in user
     * @param callback successhandler
     * @throws JSONException
     */
    public void getMembers(Context context, String email, String token, apiArraySuccessHandler callback) throws JSONException {
        RequestQueue queue = Volley.newRequestQueue(context);
        JSONArray data = new JSONArray();

        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.GET,
                Constants.BASE_URL+ "member/get-member",
                data,
                response -> {
                    try {
                        callback.handle(response);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> {
                    Toast.makeText(context, Constants.MEMBERFAILEDERROR, Toast.LENGTH_LONG).show();
                }) {

            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<String, String>();
                params.put(Constants.EMAIL, email);
                params.put(Constants.TOKEN, token);

                return params;
            }
        };
        queue.add(request);
    }

    /**
     *
     * @param context context of current app
     * @param email email of the logged in user
     * @param token token of the logged in user
     * @param callback successhandler
     * @param memberid id of the chosen member
     * @throws JSONException
     */
    public void removeMember(Context context, String email, String token, String memberid, apiObjectSuccessHandler callback) throws JSONException {
        RequestQueue queue = Volley.newRequestQueue(context);
        JSONObject data = new JSONObject();

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                Constants.BASE_URL+ "member/delete-member",
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
                    Toast.makeText(context, Constants.FAILEDMEMBEDELETION, Toast.LENGTH_LONG).show();
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

    /**
     *
     * @param context context of current app
     * @param email email of the logged in user
     * @param token token of the logged in user
     * @param callback successhandler
     * @param name name of the member to be inserter
     * @throws JSONException
     */
    public void insertMember(Context context, String email, String token, String name, apiObjectSuccessHandler callback) throws JSONException {
        RequestQueue queue = Volley.newRequestQueue(context);
        JSONObject data = new JSONObject();

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                Constants.BASE_URL+ "member/insert-member",
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
                    Toast.makeText(context, Constants.FAILEDMEMBERCREATION, Toast.LENGTH_LONG).show();
                }) {

            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<String, String>();
                params.put(Constants.EMAIL, email);
                params.put(Constants.TOKEN, token);
                params.put(Constants.ICON, Constants.STANDARDICON);
                params.put(Constants.NAME, name);
                return params;
            }
        };
        queue.add(request);
    }

    /**
     *
     * @param context context of current app
     * @param email email of the logged in user
     * @param token token of the logged in user
     * @param callback successhandler
     * @param member_id id of the member
     * @param replacement_icon the new icon (currently not available to user)
     * @param replacement_name the new name
     * @throws JSONException
     */
    public void updateMember(Context context, String email, String token, String member_id, String replacement_icon, String replacement_name, apiObjectSuccessHandler callback) throws JSONException {
        RequestQueue queue = Volley.newRequestQueue(context);
        JSONObject data = new JSONObject();

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                Constants.BASE_URL+ "member/update-member",
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
                    Toast.makeText(context, Constants.FAILEDMEMBERUPDATE, Toast.LENGTH_LONG).show();
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

    /**
     * resetpassword
     */
    public void resetPassword(){

    }



}
