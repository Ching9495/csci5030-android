package edu.slu.yumdev;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SingleRecipe extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private DatabaseReference mPostReference;
    private String recipeID;
    private String recipeTitle, recipeIngredients, recipeSteps;

    public SingleRecipe() {
        this(FirebaseDatabase.getInstance().getReference());
    }

    // Pass in a specific database.  (The test instance will use this)
    public SingleRecipe(DatabaseReference mDatabase) {
        this.mDatabase = mDatabase;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_single_recipe);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // For testing purposes, get the 1 recipe we have stored already.
        recipeID = "6b3f26c5-9792-4f2f-8314-003856b79c51";

        readRecipe(recipeID);

        TextView titleField = (TextView)findViewById(R.id.recipeTitle);
        titleField.setText(recipeTitle);

        TextView ingredientsField = (TextView)findViewById(R.id.recipeIngredients);
        ingredientsField.setText(recipeIngredients);

        TextView stepsField = (TextView)findViewById(R.id.recipeSteps);
        stepsField.setText(recipeSteps);

    }

    // TODO: Test Here

    public void readRecipe(String recipeID){

        mPostReference = mDatabase.child("recipe").child(recipeID);

        ValueEventListener recipeListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                // Get Recipe object and use the values to update the UI
                Recipe recipeSnapshot = dataSnapshot.getValue(Recipe.class);

                // Set values from database for the specified recipeID
                recipeTitle = recipeSnapshot.title.toString();
                recipeIngredients = recipeSnapshot.ingredients.toString();
                recipeSteps = recipeSnapshot.steps.toString();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w("0", "loadPost:onCancelled", databaseError.toException());
                // ...
            }
        };

        mPostReference.addListenerForSingleValueEvent(recipeListener);

    }

}
