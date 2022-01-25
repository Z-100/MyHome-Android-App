package com.example.myhome.models;
import com.google.firebase.database.PropertyName;
import com.google.type.DateTime;

import java.sql.Date;

import lombok.Data;


@Data
public class Cleaning {

    @PropertyName("id")
    private int id;
    @PropertyName("cleaning_date")
    private Date cleaning_date;


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
