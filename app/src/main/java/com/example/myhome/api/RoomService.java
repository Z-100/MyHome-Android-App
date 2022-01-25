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

public class RoomService {

    public void getRooms(Context context, String email, String token, AccountService.apiArraySuccessHandler callback) throws JSONException {
        RequestQueue queue = Volley.newRequestQueue(context);
        JSONArray data = new JSONArray();


        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.GET,
                "http://192.168.8.91:8080/room/get-all-rooms",
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
                    Toast.makeText(context, "Failed to get Members", Toast.LENGTH_LONG).show();
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
