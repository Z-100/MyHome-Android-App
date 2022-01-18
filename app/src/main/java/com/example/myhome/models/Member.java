package com.example.myhome.models;
import com.google.firebase.database.PropertyName;
import lombok.Data;

@Data
public class Member {

    @PropertyName("id")
    private int id;
    @PropertyName("name")
    private String name;
    @PropertyName("icon")
    private int icon;
    @PropertyName("fk_accountId")
    private int fk_accountId;


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
    public int getFk_accountId() {
        return fk_accountId;
    }
    public void setFk_accountId(int fk_accountId) {
        this.fk_accountId = fk_accountId;
    }
}
