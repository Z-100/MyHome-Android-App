package com.example.myhome.models;
import com.google.firebase.database.PropertyName;
import lombok.Data;

//ToDo

@Data
public class House {

    @PropertyName("id")
    private int id;
    @PropertyName("fk_accountId")
    private int fk_accountId;


    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getFk_accountId() {
        return fk_accountId;
    }
    public void setFk_accountId(int fk_accountId) {
        this.fk_accountId = fk_accountId;
    }
}
