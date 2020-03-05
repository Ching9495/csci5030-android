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

        // Create a SingleRecipe instance and invoke readRecipe()
        SingleRecipe testObject = new SingleRecipe(database);
        testObject.readRecipe(recipeId);

        // Capture the recipe that was supposed to be stored in the mock database
        ArgumentCaptor<Recipe> argument = ArgumentCaptor.forClass(Recipe.class);
        verify(row).setValue(argument.capture());
        Recipe recipeArgument = argument.getValue();

        // Please pass.....
        assertEquals(testObject.getRecipeTitle(), recipeArgument.title);
        assertEquals(testObject.getRecipeIngredients(), recipeArgument.ingredients);
        assertEquals(testObject.getRecipeSteps(), recipeArgument.steps);

    }

    private String randomString() {
        return UUID.randomUUID().toString();
    }
}
