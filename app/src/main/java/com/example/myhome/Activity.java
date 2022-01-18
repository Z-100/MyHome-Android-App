package com.example.myhome;

import androidx.appcompat.app.AppCompatActivity;
import com.example.myhome.models.Account;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import java.util.concurrent.ExecutionException;

public class Activity extends AppCompatActivity {

    private TextView mTextViewResult;
    private Button buttonParse;
    private RequestQueue mQueue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);

//        mTextViewResult = (TextView) findViewById(R.id.textView);
//        buttonParse = (Button) findViewById(R.id.button);

        mQueue = Volley.newRequestQueue(this);

        buttonParse.setOnClickListener(v -> jsonParse());
    }

    private void jsonParse() {
        final String uri = "http://10.10.21.139:8080/getAcc";

        try {
            Account responseAcc = new AccountFromRESTApiService().execute(uri).get().getBody();
            mTextViewResult.setText(responseAcc.getName() + " " + responseAcc.getPw());
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
