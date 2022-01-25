package com.example.myhome.models;
import com.google.firebase.database.PropertyName;

import java.sql.Date;

import lombok.Data;

@Data
public class Meal {

    @PropertyName("id")
    private int id;
    @PropertyName("meal_date")
    private Date meal_date;
    @PropertyName("type")
    private String type;
    @PropertyName("fk_memberId")
    private int fk_memberId;


    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public Date getMeal_date() {
        return meal_date;
    }
    public void setMeal_date(Date meal_date) {
        this.meal_date = meal_date;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public int getFk_memberId() {
        return fk_memberId;
    }
    public void setFk_memberId(int fk_memberId) {
        this.fk_memberId = fk_memberId;
    }
}
