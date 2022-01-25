package com.example.myhome.models;
import com.google.firebase.database.PropertyName;
import lombok.Data;


/**
 * @author Rad14nt
 * Class used to create a model for House, and add all the necessary methods to it * */

@Data
public class House {

    @PropertyName("id")
    private int id;
    @PropertyName("fk_accountId")
    private int fk_accountId;

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
    public int getFk_accountId() {
        return fk_accountId;
    }
    public void setFk_accountId(int fk_accountId) {
        this.fk_accountId = fk_accountId;
    }
}
