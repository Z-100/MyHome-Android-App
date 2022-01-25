package com.example.myhome.models;

import com.google.firebase.database.PropertyName;
import lombok.Data;

/**
 * @author Rad14nt
 * Class used to create a model for Account, and add all the necessary methods to it * */

@Data
public class Account {

    @PropertyName("id")
    private Long id;
    @PropertyName("name")
    private String name;
    @PropertyName("pw")
    private String pw;

    /**
     * getters and setters
     * @return
     */
    public Long getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getPw() {
        return pw;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setPw(String pw) {
        this.pw = pw;
    }
}
