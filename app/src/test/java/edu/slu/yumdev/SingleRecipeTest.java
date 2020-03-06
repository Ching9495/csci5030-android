package edu.slu.yumdev;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import org.junit.Test;
import org.mockito.Mockito;

import java.util.UUID;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class SingleRecipeTest {
    @Test
    public void readRecipe_listensToDatabase() {
        String recipeId = randomString();

        // Establish a mock database
        DatabaseReference database = mock(DatabaseReference.class);
        DatabaseReference table = mock(DatabaseReference.class);
        DatabaseReference row = mock(DatabaseReference.class);

        when(database.child("recipe")).thenReturn(table);
        when(table.child(recipeId)).thenReturn(row);

        // Create a SingleRecipe instance and invoke readRecipe()
        SingleRecipe testObject = new SingleRecipe(database);
        testObject.readRecipe(recipeId);

        // Verify we setup a listener
        verify(row).addListenerForSingleValueEvent((ValueEventListener) Mockito.any());
    }

    private String randomString() {
        return UUID.randomUUID().toString();
    }
}
