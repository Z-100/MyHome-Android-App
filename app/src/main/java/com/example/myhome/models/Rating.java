package com.example.myhome.models;
import com.google.firebase.database.PropertyName;
import lombok.Data;

@Data
public class Rating {

    @PropertyName("id")
    private int id;
    @PropertyName("rating")
    private int rating;
    @PropertyName("fk_memberId")
    private int fk_memberId;
    @PropertyName("recipe_idRecipe")
    private int recipe_idRecipe;


    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getRating() {
        return rating;
    }
    public void setRating(int rating) {
        this.rating = rating;
    }
    public int getFk_memberId() {
        return fk_memberId;
    }
    public void setFk_memberId(int fk_memberId) {
        this.fk_memberId = fk_memberId;
    }
    public int getRecipe_idRecipe() {
        return recipe_idRecipe;
    }
    public void setRecipe_idRecipe(int recipe_idRecipe) {
        this.recipe_idRecipe = recipe_idRecipe;
    }
}
