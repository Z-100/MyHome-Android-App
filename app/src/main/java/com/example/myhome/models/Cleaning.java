package com.example.myhome.models;
import com.google.firebase.database.PropertyName;
import com.google.type.DateTime;

import java.sql.Date;

import lombok.Data;


/**
 * @author Rad14nt
 * Class used to create a model for Cleaning, and add all the necessary methods to it * */

@Data
public class Cleaning {

    @PropertyName("id")
    private int id;
    @PropertyName("cleaning_date")
    private Date cleaning_date;

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
    public Date getCleaning_date() {
        return cleaning_date;
    }
    public void setCleaning_date(Date cleaning_date) {
        this.cleaning_date = cleaning_date;
    }
}
