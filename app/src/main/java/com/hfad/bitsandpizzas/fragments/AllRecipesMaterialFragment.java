package com.hfad.bitsandpizzas.fragments;


import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hfad.bitsandpizzas.R;
import com.hfad.bitsandpizzas.adapters.CardCombinerAdapter;
import com.hfad.bitsandpizzas.database.RecipeDatabaseHelper;
import com.hfad.bitsandpizzas.model.Recipe;
import com.hfad.bitsandpizzas.model.Webrecipe;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class AllRecipesMaterialFragment extends Fragment {

    public ArrayList<Object> allRecipesArrayList = new ArrayList<Object>();
    public ArrayList<Object> recipesArrayList = new ArrayList<Object>();
    public ArrayList<Object> webrecipesArrayList = new ArrayList<Object>();


    public AllRecipesMaterialFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //run of method populateRecipeList and populateWebRecipeList to get the recipes to show
        populateRecipeList();
        populateWebRecipeList();
        //method populating allrecipesarraylist with all data from the above mentioned arraylists
        populateAllrecipesList(recipesArrayList, webrecipesArrayList);


        RecyclerView allRecipeRecycler = (RecyclerView)inflater.inflate(
                R.layout.fragment_all_recipes_material, container, false);
        //setting fixed size to make performance better
        allRecipeRecycler.setHasFixedSize(true);

        //setting the custom adapter
        //smarter adapter is used in this situation compared to RecipeMaterialFragment and WebrecipeMaterialFragment, since this adapter just takes the arraylist as is
        CardCombinerAdapter adapter = new CardCombinerAdapter(allRecipesArrayList);
        allRecipeRecycler.setAdapter(adapter);

        //chosing how to display the recycler -> here it's gridlayout
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        allRecipeRecycler.setLayoutManager(gridLayoutManager);


        // Inflate the layout for this fragment
        return allRecipeRecycler;
    }


    //------------------------------methods for recipearraylist initialization------------------------------------------------



    public void populateRecipeList(){

        try {
            //getting reference to the db
            SQLiteOpenHelper recipeDatabaseHelper = new RecipeDatabaseHelper(getActivity().getApplicationContext());
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
            SQLiteOpenHelper webrecipeDatabaseHelper = new RecipeDatabaseHelper(getActivity().getApplicationContext());
            SQLiteDatabase db = webrecipeDatabaseHelper.getReadableDatabase();
            //creating cursor that will iterate through Webrecipes table to get all data
            Cursor cursor = db.query("WEBRECIPES",
                    new String[] {"NAME", "COMMENT", "DATE", "URL"},
                    null, null, null, null,
                    //ordering table by name
                    "NAME ASC");

            if (cursor.moveToFirst()) {
                do {
                    String name = "WEB : " + cursor.getString(0);
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



}
