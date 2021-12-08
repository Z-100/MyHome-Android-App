package com.example.myhome;

import android.os.AsyncTask;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

/**
 * @author marvin
 * <p>
 * Multithreading for web tasks
 * EXAMPLE CLASS, DO NOT USE
 * </p>
 */
public class AccountFromRESTApiService extends AsyncTask<String, Void, ResponseEntity<Account>> {

    @Override
    protected ResponseEntity<Account> doInBackground(String... strings) {

        final String url = strings[0];

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters()
                .add(new MappingJackson2HttpMessageConverter());

        HttpHeaders headers = new HttpHeaders();

        headers.set("token", "s"); //TODO: Change to something secure or something like that

        HttpEntity<String> httpEntity = new HttpEntity<>(headers);

        return restTemplate.exchange(url, HttpMethod.GET, httpEntity, Account.class);
    }

    @Override
    protected void onPostExecute(ResponseEntity<Account> accountResponseEntity) {
        HttpStatus statusCode = accountResponseEntity.getStatusCode();
        Account account = accountResponseEntity.getBody();
    }
}
