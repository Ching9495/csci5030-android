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
        // Given a database, with a recipe table...

        DatabaseReference database = mock(DatabaseReference.class);
        DatabaseReference table = mock(DatabaseReference.class);
        DatabaseReference row = mock(DatabaseReference.class);

        when(database.child("recipe")).thenReturn(table);
        when(table.child(recipeId)).thenReturn(row);

        Map<String, Object> result = recipe.toMap();

        assertEquals(recipe.toMap().get("title"),title);
        assertEquals(recipe.toMap().get("steps"),steps);
        assertEquals(recipe.toMap().get("ingredients"),ingredients);
    }
    private String randomString() {
        return UUID.randomUUID().toString();
    }
}
