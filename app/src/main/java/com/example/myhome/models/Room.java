package com.example.myhome.models;
import com.google.firebase.database.PropertyName;
import lombok.Data;

/**
 * @author Rad14nt
 * Class used to create a model for Room, and add all the necessary methods to it
 * */

@Data
public class Room {

    @PropertyName("id")
    private int id;
    @PropertyName("name")
    private String name;
    @PropertyName("icon")
    private int icon;
    @PropertyName("house_id")
    private int house_id;

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
    public int getIcon() {
        return icon;
    }
    public void setIcon(int icon) {
        this.icon = icon;
    }
    public int getHouse_id() {
        return house_id;
    }
    public void setHouse_id(int house_id) {
        this.house_id = house_id;
    }
}
