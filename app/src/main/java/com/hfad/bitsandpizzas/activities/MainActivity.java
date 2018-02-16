package com.hfad.bitsandpizzas.activities;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.hfad.bitsandpizzas.R;
import com.hfad.bitsandpizzas.fragments.AllRecipesMaterialFragment;
import com.hfad.bitsandpizzas.fragments.RecipeMaterialFragment;
import com.hfad.bitsandpizzas.fragments.WebrecipeMaterialFragment;


public class MainActivity extends Activity {

    private String[] titles;
    private ListView drawerList;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private int currentPosition = 0;

    //private class handling clicks to drawer
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //populating drawer with the array titles from string.xml
        titles = getResources().getStringArray(R.array.titles);

        drawerList = (ListView)findViewById(R.id.drawer);
        drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        drawerList.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_activated_1, titles));

        //setting click listener on drawer list
        drawerList.setOnItemClickListener(new DrawerItemClickListener());

        //create the actionbarDrawertoggle which opens and closes drawer
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout,
                R.string.open_drawer, R.string.close_drawer) {
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                invalidateOptionsMenu();
            }
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu();
            }
        };
        //setting listener on drawer to check if it's opened or closed
        drawerLayout.setDrawerListener(drawerToggle);

        //enabling the actionbar and the "go back" button
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);


        getFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            //makes sure app tilte always is gonna be correct, because it remembers which fragment was the last visible by updating currentposition
            public void onBackStackChanged() {
                FragmentManager fragMan = getFragmentManager();
                Fragment fragment = fragMan.findFragmentByTag("visible_fragment");
                if (fragment instanceof AllRecipesMaterialFragment) {
                    currentPosition = 0;
                }
                if (fragment instanceof RecipeMaterialFragment) {
                    currentPosition = 1;
                }
                if (fragment instanceof WebrecipeMaterialFragment) {
                    currentPosition = 2;
                }
                //set actionbar title
                setActionBarTitle(currentPosition);
                //highlight currently selected item in drawer
                drawerList.setItemChecked(currentPosition, true);
            }
        });

        //making sure that title does not get out of sync
        if (savedInstanceState != null) {
            currentPosition = savedInstanceState.getInt("position");
            setActionBarTitle(currentPosition);
        } else {
            //if mainactivity is newly created use the selectitem method to display allrecipes fragment
            selectItem(0);
        }
    }



    private void selectItem(int position) {
        //update the main content by replacing fragments
        currentPosition = position;
        Fragment fragment;
        switch (position) {
            case 1:
                fragment = new RecipeMaterialFragment();
                break;
            case 2:
                fragment = new WebrecipeMaterialFragment();
                break;
            default:
                fragment = new AllRecipesMaterialFragment();
        }

        //using fragment transaction to replace the fragment  thats displayed
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.content_frame, fragment, "visible_fragment");
        ft.addToBackStack(null);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.commit();

        //setting the title
        setActionBarTitle(position);

        //close the drawer after user selects new fragment to be shown
        drawerLayout.closeDrawer(drawerList);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu){
        boolean drawerOpen = drawerLayout.isDrawerOpen(drawerList);
        return super.onPrepareOptionsMenu(menu);
    }



    //setting the title of the actitvity to the selected fragment
    private void setActionBarTitle(int position) {
        String title;
        if (position == 0) {
            title = getResources().getString(R.string.app_name);
        } else {
            title = titles[position];
        }
        getActionBar().setTitle(title);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu - this adds items to the actionbar
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }


    //used when an item in the actionbar is clicked
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        switch (item.getItemId()) {
        case R.id.action_create_recipe:
            //Code to run when the Create recipe item is clicked
            Intent intent1 = new Intent(this, CreateRecipeActivity.class);
            startActivity(intent1);
            return true;
        case R.id.action_create_webrecipe:
            //Code to run when the settings item is clicked
            Intent intent2 = new Intent(this, CreateWebrecipeActivity.class);
            startActivity(intent2);
            return true;
        case R.id.action_search_recipe:
            Intent intent3 = new Intent(this, SearchActivity.class);
            startActivity(intent3);
            return true;
        default:
            return super.onOptionsItemSelected(item);
        }
    }

    //syncing the state of the drawertoggle with the state of the drawer
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    //pass any configurationchanges to the drawertoggle
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    //set the state of currentposition if activity is going to be destroyed
    @Override
    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        outState.putInt("position", currentPosition);
    }
}