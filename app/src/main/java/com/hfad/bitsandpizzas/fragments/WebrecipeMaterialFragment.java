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

import com.hfad.bitsandpizzas.activities.CreateWebrecipeActivity;
import com.hfad.bitsandpizzas.R;
import com.hfad.bitsandpizzas.adapters.CardNameDateAdapter;
import com.hfad.bitsandpizzas.database.RecipeDatabaseHelper;
import com.hfad.bitsandpizzas.model.Webrecipe;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class WebrecipeMaterialFragment extends Fragment {

    public ArrayList<Webrecipe> webrecipesArrayList = new ArrayList<Webrecipe>();



    public WebrecipeMaterialFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //run of method populateWebrecipeList to get the recipes to show
        populateWebRecipeList();

        RecyclerView webrecipeRecycler = (RecyclerView)inflater.inflate(
                R.layout.fragment_webrecipe_material, container, false);
        //setting fixed size to make performance better
        webrecipeRecycler.setHasFixedSize(true);

        //populating arrays with data that will appear in layout
        String[] webrecipeNames = new String[webrecipesArrayList.size()];
        for (int i = 0; i < webrecipeNames.length; i++) {
            webrecipeNames[i] = webrecipesArrayList.get(i).getName();
        }
        String[] webrecipeDates = new String[webrecipesArrayList.size()];
        for (int i = 0; i < webrecipeDates.length; i++) {
            webrecipeDates[i] = webrecipesArrayList.get(i).getDate();
        }

        //setting the custom adapter
        CardNameDateAdapter adapter = new CardNameDateAdapter(webrecipeNames, webrecipeDates);
        webrecipeRecycler.setAdapter(adapter);

        //chosing how to display the recycler -> here it's linearlayout
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        webrecipeRecycler.setLayoutManager(layoutManager);

        //setting listener on recipeRecyclers adapter, so any clicks by user will get registered
        adapter.setListener(new CardNameDateAdapter.Listener() {
            public void onClick(int position) {
                Intent intent = new Intent(getActivity(), CreateWebrecipeActivity.class);
                intent.putExtra("webrecipe", webrecipesArrayList.get(position));
                getActivity().startActivity(intent);
            }
        });
        // Inflate the layout for this fragment
        return webrecipeRecycler;
    }



    //--------------------------------------methods for arraylist initialization----------------------------------------



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
                    String name = cursor.getString(0);
                    String comment = cursor.getString(1);
                    String date = cursor.getString(2);
                    String url = cursor.getString(3);

                    //creating Webrecipe objects for every possible entry
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
}
