package com.example.myhome;

import com.google.firebase.database.PropertyName;

import lombok.Data;

@Data
public class Account {

    @PropertyName("id")
    private Long id;
    @PropertyName("name")
    private String name;
    @PropertyName("pw")
    private String pw;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPw() {
        return pw;
    }
}
