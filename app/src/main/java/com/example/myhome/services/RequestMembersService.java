package com.example.myhome.services;

import android.os.AsyncTask;

import com.example.myhome.models.Member;
import com.example.myhome.models.Token;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.List;



public class RequestMembersService extends AsyncTask<String, Void, ResponseEntity<List<Member>>> {
    @Override
    protected ResponseEntity<List<Member>> doInBackground(String... strings) {

        final String url = strings[0];

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters()
                .add(new MappingJackson2HttpMessageConverter());

        HttpHeaders headers = new HttpHeaders();
        headers.set("email", strings[1]);
        headers.set("token", strings[2]);

        HttpEntity<String> httpEntity = new HttpEntity<>(headers);

        return restTemplate.exchange(url, HttpMethod.GET, httpEntity, new ParameterizedTypeReference<List<Member>>() {});
    }
}
