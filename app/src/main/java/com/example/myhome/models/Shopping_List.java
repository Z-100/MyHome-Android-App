package com.example.myhome.models;
import com.google.firebase.database.PropertyName;

import java.sql.Date;

import lombok.Data;

@Data
public class Shopping_List {

    @PropertyName("id")
    private int id;
    @PropertyName("date")
    private Date date;
    @PropertyName("house_id")
    private int house_id;


    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }
    public int getHouse_id() {
        return house_id;
    }
    public void setHouse_id(int house_id) {
        this.house_id = house_id;
    }
}
