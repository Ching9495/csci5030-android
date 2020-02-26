package edu.slu.yumdev;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class Recipe {

    public String recipeId;
    public String title;
    public String ingredients;
    public String steps;

    public Recipe() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Recipe(String recipeId, String title, String ingridients, String steps) {
        this.recipeId = recipeId;
        this.title = title;
        this.ingredients = ingridients;
        this.steps = steps;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("title", title);
        result.put("ingredients", ingredients);
        result.put("steps", title);

        return result;
    }
}