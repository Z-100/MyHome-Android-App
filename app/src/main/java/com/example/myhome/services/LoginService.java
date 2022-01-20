package com.example.myhome.services;

import android.os.AsyncTask;

import com.example.myhome.models.Account;
import com.example.myhome.models.Token;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

public class LoginService extends AsyncTask<String, Void, ResponseEntity<Token>>{

        @Override
        protected ResponseEntity<Token> doInBackground(String... strings) {

            final String url = strings[0];

            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters()
                    .add(new MappingJackson2HttpMessageConverter());

            HttpHeaders headers = new HttpHeaders();

            headers.set("email", "email");
            headers.set("password", "pw");
            headers.set("token", "69"); //TODO: Change to something secure or something like that

            HttpEntity<String> httpEntity = new HttpEntity<>(headers);

            return restTemplate.exchange(url, HttpMethod.GET, httpEntity, Token.class);
    }

    @Override
    protected void onPostExecute(ResponseEntity<Token> accountResponseEntity) {
        HttpStatus statusCode = accountResponseEntity.getStatusCode();
        Token token = accountResponseEntity.getBody();
    }

}
