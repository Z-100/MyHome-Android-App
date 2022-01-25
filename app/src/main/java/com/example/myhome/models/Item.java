package com.example.myhome.models;
import com.google.firebase.database.PropertyName;
import lombok.Data;

/**
 * @author Rad14nt
 * Class used to create a model for Item, and add all the necessary methods to it * */

@Data
public class Item {

    @PropertyName("id")
    private int id;
    @PropertyName("name")
    private String name;

    /**
     * getters and setters
     * @return
     */
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
