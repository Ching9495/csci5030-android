package edu.slu.yumdev;

import com.google.firebase.database.DatabaseReference;

import org.junit.Test;

import java.util.Map;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RecipeTest {
    @Test
    public void recipe_maptest(){

        String recipeId = randomString();
        String title = randomString();
        String ingredients = randomString();
        String steps = randomString();

        Recipe recipe = new Recipe(recipeId, title, ingredients, steps);

        Map<String, Object> result = recipe.toMap();

        assertEquals(result.get("title"),title);
        assertEquals(result.get("steps"),steps);
        assertEquals(result.get("ingredients"),ingredients);
    }
    private String randomString() {
        return UUID.randomUUID().toString();
    }
}
