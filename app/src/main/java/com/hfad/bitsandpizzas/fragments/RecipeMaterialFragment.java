package com.hfad.bitsandpizzas.fragments;


import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hfad.bitsandpizzas.activities.CreateRecipeActivity;
import com.hfad.bitsandpizzas.R;
import com.hfad.bitsandpizzas.adapters.CardTextTextAdapter;
import com.hfad.bitsandpizzas.database.RecipeDatabaseHelper;
import com.hfad.bitsandpizzas.model.Recipe;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecipeMaterialFragment extends Fragment {

    public ArrayList<Recipe> localRecipesArrayList = new ArrayList<Recipe>();



    public RecipeMaterialFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //run of method populateRecipeList to get the recipes to show
        populateRecipeList();

        RecyclerView recipeRecycler = (RecyclerView)inflater.inflate(
                R.layout.fragment_recipe_material, container, false);
        //setting fixed size to make performance better
        recipeRecycler.setHasFixedSize(true);

        //populating arrays with data that will appear in layout
        String[] recipeNames = new String[localRecipesArrayList.size()];
        for (int i = 0; i < recipeNames.length; i++) {
            recipeNames[i] = localRecipesArrayList.get(i).getName();
        }
        String[] recipePeople = new String[localRecipesArrayList.size()];
        for (int i = 0; i < recipePeople.length; i++) {
            recipePeople[i] = localRecipesArrayList.get(i).getPeople();
        }

        //setting the custom adapter
        CardTextTextAdapter adapter = new CardTextTextAdapter(recipeNames, recipePeople);
        recipeRecycler.setAdapter(adapter);

        //chosing how to display the recycler -> here it's linearlayout
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recipeRecycler.setLayoutManager(layoutManager);

        //setting listener on recipeRecyclers adapter, so any clicks by user will get registered
        adapter.setListener(new CardTextTextAdapter.Listener() {
            public void onClick(int position){
                Intent intent = new Intent(getActivity(), CreateRecipeActivity.class);
                intent.putExtra("recipe", localRecipesArrayList.get(position));
                getActivity().startActivity(intent);
            }
        });
        // Inflate the layout for this fragment
        return recipeRecycler;
    }


//-----------------------------methods for arraylist initialization-------------------------------------------------



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
        localRecipesArrayList.add(recipe);
    }
}
