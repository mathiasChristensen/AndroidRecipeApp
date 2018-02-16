package com.hfad.bitsandpizzas.activities;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Toast;

import com.hfad.bitsandpizzas.R;
import com.hfad.bitsandpizzas.database.RecipeDatabaseHelper;
import com.hfad.bitsandpizzas.model.Recipe;


public class CreateRecipeActivity extends Activity {

    private Recipe recipe;
    private RecipeDatabaseHelper db;
    private EditText editTextName, editTextDate, editTextPeople;
    private MultiAutoCompleteTextView multiDescription, multiIngredients, multiApproach;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_recipe);

        //getting reference to database
        db = new RecipeDatabaseHelper(this);
        //taking the data from intent and assigning at to recipe variable
        recipe = getIntent().getParcelableExtra("recipe");

        //reference to items in view
        editTextName = (EditText)findViewById(R.id.editTextName);
        editTextDate = (EditText)findViewById(R.id.editTextDate);
        editTextPeople = (EditText)findViewById(R.id.editTextPeople);
        multiDescription = (MultiAutoCompleteTextView)findViewById(R.id.editTextDescription);
        multiIngredients = (MultiAutoCompleteTextView)findViewById(R.id.editTextIngredients);
        multiApproach = (MultiAutoCompleteTextView)findViewById(R.id.editTextApproach);

        //checking whether we received any data by the intent -> if we did, then sets data into the views text entries
        if (recipe != null) {
            editTextName.setText(recipe.getName());
            editTextDate.setText(recipe.getDate());
            editTextPeople.setText(recipe.getPeople());
            multiDescription.setText(recipe.getDescription());
            multiIngredients.setText(recipe.getIngredient());
            multiApproach.setText(recipe.getApproach());
        }
        //enabling the actionbar and the "go back" button
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu - this adds items to the actionbar
        getMenuInflater().inflate(R.menu.menu_create_recipe, menu);
        return super.onCreateOptionsMenu(menu);
    }


    //used when an item in the actionbar is clicked
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_save_recipe:
                //Code to run when the Create recipe is clicked
                save();
                Intent intent1 = new Intent(this, MainActivity.class);
                startActivity(intent1);
                return true;
            case R.id.action_delete_recipe:
                //only possible to delete if there is an object to delete
                //otherwise it just reacts to the click but does'nt do anything
                if (recipe != null) {
                    //Code to run when the Delete recipe is clicked
                    delete();
                    Intent intent2 = new Intent(this, MainActivity.class);
                    startActivity(intent2);
                } else {
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.no_delete_recipe_toast), Toast.LENGTH_LONG).show();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void save(){
        //save method takes all data in fields and saves them as a Recipe object
        Recipe newRecipe = new Recipe(
        editTextName.getText().toString(),
        multiDescription.getText().toString(),
        editTextDate.getText().toString(),
        editTextPeople.getText().toString(),
        multiIngredients.getText().toString(),
        multiApproach.getText().toString());

        //if we did'nt get any data from the intent when this activity was initialized then add the recipe object as a new object
        if (recipe == null) {
            db.addRecipe(newRecipe);
        //otherwise update the object with the new data and use the old name and description as reference to which row in the table to update
        } else {
            db.updateRecipe(newRecipe, recipe.getName(), recipe.getDescription());
        }
    }

    //delete method just calls method from RecipeDatabaseHelper and takes the recipe from the intent as parameter
    public void delete(){
        db.deleteRecipe(recipe);
    }



}
