package edu.slu.yumdev;

import com.google.firebase.database.DatabaseReference;

import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class SingleRecipeTest {
    @Test
    public void singleRecipe_obtainRecipeFromDatabaseCorrectly() {

        String recipeId = randomString();
        String title = randomString();
        String ingredients = randomString();
        String steps = randomString();

        // Establish a mock database

        DatabaseReference database = mock(DatabaseReference.class);
        DatabaseReference table = mock(DatabaseReference.class);
        DatabaseReference row = mock(DatabaseReference.class);

        when(database.child("recipe")).thenReturn(table);
        when(table.child(recipeId)).thenReturn(row);

        // Write 1 recipe to the mock database
        Recipe recipe = new Recipe();
        recipe.writeRecipe(recipeId, title, ingredients, steps,database);

        // Then the recipe gets sent to the database, in the correct table, on the correct row

        // NOTE: You have to capture the argument.  If you just compare the recipe that gets
        // stored, java will just compare the pointers.
        ArgumentCaptor<Recipe> argument = ArgumentCaptor.forClass(Recipe.class);
        verify(row).setValue(argument.capture());

        // Compare all the recipe properties to what was passed in.
        Recipe recipeArgument = argument.getValue();
        assertEquals(recipeArgument.recipeId, recipeId);
        assertEquals(recipeArgument.title, title);
        assertEquals(recipeArgument.ingredients, ingredients);
        assertEquals(recipeArgument.steps, steps);
    }

    private String randomString() {
        return UUID.randomUUID().toString();
    }
}
