package com.example.myhome.models;
import com.google.firebase.database.PropertyName;
import lombok.Data;

/**
 * @author Rad14nt
 * Class used to create a model for Recipe, and add all the necessary methods to it
 * */

@Data
public class Recipe {

    @PropertyName("id")
    private int id;
    @PropertyName("name")
    private String name;
    @PropertyName("for_amount_of_people")
    private String for_amount_of_people;

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
    public String getFor_amount_of_people() {
        return for_amount_of_people;
    }
    public void setFor_amount_of_people(String for_amount_of_people) {
        this.for_amount_of_people = for_amount_of_people;
    }
}
