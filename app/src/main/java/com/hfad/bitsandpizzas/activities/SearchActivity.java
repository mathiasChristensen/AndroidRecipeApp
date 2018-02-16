package com.hfad.bitsandpizzas.activities;

import android.app.ActionBar;
import android.app.Activity;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

import com.hfad.bitsandpizzas.R;
import com.hfad.bitsandpizzas.adapters.CardCombinerAdapter;
import com.hfad.bitsandpizzas.database.RecipeDatabaseHelper;
import com.hfad.bitsandpizzas.model.Recipe;
import com.hfad.bitsandpizzas.model.Webrecipe;

import java.util.ArrayList;

public class SearchActivity extends Activity {


    public RecyclerView searchRecycler;
    public EditText search;
    public ArrayList<Object> allRecipesArrayList = new ArrayList<Object>();
    public ArrayList<Object> recipesArrayList = new ArrayList<Object>();
    public ArrayList<Object> webrecipesArrayList = new ArrayList<Object>();
    public CardCombinerAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //setting the layout for the view
        setContentView(R.layout.activity_search);

        //reference to items in view
        search = (EditText)findViewById(R.id.editTextSearch);
        searchRecycler = (RecyclerView) findViewById(R.id.search_recycler);
        //setting fixed size to make performance better
        searchRecycler.setHasFixedSize(true);
        //setting layoutmanager -> linearlayoutmanager
        searchRecycler.setLayoutManager(new LinearLayoutManager(this));

        //run of method populateRecipeList and populateWebRecipeList to get all the recipes
        populateRecipeList();
        populateWebRecipeList();
        //method populating allrecipesarraylist with all data from the above mentioned arraylists
        populateAllrecipesList(recipesArrayList, webrecipesArrayList);

        //enabling the actionbar and the "go back" button
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        //setting the custom adapter
        //smarter adapter is used in this situation compared to RecipeMaterialFragment and WebrecipeMaterialFragment, since this adapter just takes the arraylist as is
        adapter = new CardCombinerAdapter(allRecipesArrayList);
        searchRecycler.setAdapter(adapter);

        //method for listening for new inputs in searchfield
        addTextListener();


    }

    //------------------------------methods for recipearraylist initialization------------------------------------------------


    public void populateRecipeList(){

        try {
            //getting reference to the db
            SQLiteOpenHelper recipeDatabaseHelper = new RecipeDatabaseHelper(this);
            SQLiteDatabase db = recipeDatabaseHelper.getReadableDatabase();
            //creating cursor that will iterate through Recipes table to get all data
            Cursor cursor = db.query("RECIPES",
                    new String[] {"NAME", "DESCRIPTION", "DATE", "PEOPLE", "INGREDIENTS", "APPROACH"},
                    null, null, null, null,
                    //ordering table by name
                    "NAME ASC");

            if (cursor.moveToFirst()) {
                do {
                    String name = cursor.getString(0);
                    String description = cursor.getString(1);
                    String date = cursor.getString(2);
                    String people = cursor.getString(3);
                    String ingredients = cursor.getString(4);
                    String approach = cursor.getString(5);

                    //creating Recipe objects for every possible entry
                    Recipe recipe = new Recipe(name, description, date, people, ingredients, approach);
                    addLocalRecipe(recipe);
                }
                while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
        } catch (SQLException e) {
            Log.d("Exception", e.toString());
        }
    }

    public void addLocalRecipe(Recipe recipe){
        recipesArrayList.add(recipe);
    }

//---------------------------------methods for webrecipearraylist initialization---------------------------------------------

    public void populateWebRecipeList(){

        try {
            //getting reference to the db
            SQLiteOpenHelper webrecipeDatabaseHelper = new RecipeDatabaseHelper(this);
            SQLiteDatabase db = webrecipeDatabaseHelper.getReadableDatabase();
            //creating cursor that will iterate through Webrecipes table to get all data
            Cursor cursor = db.query("WEBRECIPES",
                    new String[] {"NAME", "COMMENT", "DATE", "URL"},
                    null, null, null, null,
                    //ordering table by name
                    "NAME ASC");

            if (cursor.moveToFirst()) {
                do {
                    String name = cursor.getString(0);
                    String comment = cursor.getString(1);
                    String date = cursor.getString(2);
                    String url = cursor.getString(3);

                    //creating Recipe objects for every possible entry
                    Webrecipe webrecipe = new Webrecipe(name, comment, date, url);
                    addWebrecipe(webrecipe);
                }
                while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
        } catch (SQLException e) {
            Log.d("Exception", e.toString());
        }
    }

    public void addWebrecipe(Webrecipe webrecipe){
        webrecipesArrayList.add(webrecipe);
    }


    //------------------------------------------------------------------------------

    //method combining two arraylist into one
    public void populateAllrecipesList(ArrayList<Object> arraylist1, ArrayList<Object> arrayList2) {

        arraylist1.addAll(arrayList2);
        allRecipesArrayList = arraylist1;
    }

    //------------------------------------------------------------------------------

    public void addTextListener(){

        //adding TextWatcher to searchfield to check for updates and entries
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            //search method that filters the list based on what have been typed in the searchfield
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                s = s.toString().toLowerCase();
                final ArrayList<Object> filteredList = new ArrayList<Object>();
                for (int i = 0; i < allRecipesArrayList.size(); i++){
                    //have to check what object we are at to get the name from object
                    if (allRecipesArrayList.get(i) instanceof Recipe){
                        //making the search string and name lowercase to let the search be case insensitive
                        if ((((Recipe) allRecipesArrayList.get(i)).getName().toLowerCase().contains(s))){
                            filteredList.add(allRecipesArrayList.get(i));
                        }
                    }
                    if (allRecipesArrayList.get(i) instanceof Webrecipe) {
                        if ((((Webrecipe) allRecipesArrayList.get(i)).getName().toLowerCase().contains(s))){
                            filteredList.add(allRecipesArrayList.get(i));
                        }
                    }
                }
                searchRecycler.setLayoutManager(new LinearLayoutManager(SearchActivity.this));
                adapter = new CardCombinerAdapter(filteredList);
                searchRecycler.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        });
    }








}
