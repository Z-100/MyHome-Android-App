package com.example.myhome.models;


import lombok.Data;

/**
 * @author Rad14nt
 * Class used to create a model for Token, and add all the necessary methods to it
 * */

@Data
public class Token {

    private String token;

    /**
     * getters and setters
     * @return
     */
    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }
}
