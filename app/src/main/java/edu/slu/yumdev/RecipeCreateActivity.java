package edu.slu.yumdev;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.UUID;

public class RecipeCreateActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        Button savebtn = findViewById(R.id.savebtn);

        savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String recipeId = UUID.randomUUID().toString();
                String title = ((TextView)findViewById(R.id.title)).getText().toString();
                String ingredients = ((TextView)findViewById(R.id.ingredients)).getText().toString();
                String steps = ((TextView)findViewById(R.id.steps)).getText().toString();

                writeRecipe(recipeId, title, ingredients, steps);
            }
        });
    }

    private void writeRecipe(String recipeId, String title, String ingredient, String steps) {
        Recipe recipe = new Recipe(recipeId, title, ingredient, steps);

        mDatabase.child("recipe").child(recipeId).setValue(recipe);
    }


}

