package edu.slu.yumdev;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SingleRecipe extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private DatabaseReference mPostReference;
    private ValueEventListener mPostListener;
    private String mPostKey;
    private String recipeID;
    private String recipeTitle, recipeIngredients, recipeSteps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_single_recipe);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // For testing purposes, get the 1 recipe we have stored already.
        recipeID = "d5adfe16-5df2-4e2e-a8b4-3d8d187c534a";

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mPostReference = FirebaseDatabase.getInstance().getReference()
                .child("recipe").child(recipeID);

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

        TextView titleField = (TextView)findViewById(R.id.recipeTitle);
        titleField.setText(recipeTitle);

        TextView ingredientsField = (TextView)findViewById(R.id.recipeIngredients);
        ingredientsField.setText(recipeIngredients);

        TextView stepsField = (TextView)findViewById(R.id.recipeSteps);
        stepsField.setText(recipeSteps);

    }

    @IgnoreExtraProperties
    public class Post {

        public String uid;
        public String author;
        public String title;
        public String body;
        public int starCount = 0;
        public Map<String, Boolean> stars = new HashMap<>();

        public Post() {
            // Default constructor required for calls to DataSnapshot.getValue(Post.class)
        }

        public Post(String uid, String author, String title, String body) {
            this.uid = uid;
            this.author = author;
            this.title = title;
            this.body = body;
        }

        @Exclude
        public Map<String, Object> toMap() {
            HashMap<String, Object> result = new HashMap<>();
            result.put("uid", uid);
            result.put("author", author);
            result.put("title", title);
            result.put("body", body);
            result.put("starCount", starCount);
            result.put("stars", stars);

            return result;
        }

    }

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
    }

}
