package com.example.myhome.models;
import com.google.firebase.database.PropertyName;
import lombok.Data;


@Data
public class Item {

    @PropertyName("id")
    private int id;
    @PropertyName("name")
    private String name;


    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}
