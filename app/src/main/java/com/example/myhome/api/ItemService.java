package com.example.myhome.api;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.myhome.Constants;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Rad14nt
 * Class used to hold the services that call to the item endpoint of the API
 * */

public class ItemService {
    /**
     * Method to get items from api
     * @param context context of current app
     * @param email email of the logged in user
     * @param token token of the logged in user
     * @param callback successhandler
     * @throws JSONException
     */
    public void getItems(Context context, String email, String token, AccountService.apiArraySuccessHandler callback) throws JSONException {
        RequestQueue queue = Volley.newRequestQueue(context);
        JSONArray data = new JSONArray();

        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.GET,
                Constants.BASE_URL+ "item/get-item",
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
                    Toast.makeText(context, Constants.FAILEDGETITEMS, Toast.LENGTH_LONG).show();
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
}
