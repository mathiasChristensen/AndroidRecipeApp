package com.hfad.bitsandpizzas.activities;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Toast;

import com.hfad.bitsandpizzas.R;
import com.hfad.bitsandpizzas.database.RecipeDatabaseHelper;
import com.hfad.bitsandpizzas.model.Webrecipe;

public class CreateWebrecipeActivity extends Activity {


    //----------------------------------------------------------------------------


    private class MyBrowser extends WebViewClient {
        //making it possible to pass url to webview
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }


    //----------------------------------------------------------------------------

    private Webrecipe webrecipe;
    private RecipeDatabaseHelper db;
    private MultiAutoCompleteTextView multiTextComment;
    private EditText editTextName, editTextDate;
    private WebView webView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_webrecipe);

        //getting reference to database
        db = new RecipeDatabaseHelper(this);
        //taking the data from intent and assigning at to recipe variable
        webrecipe = getIntent().getParcelableExtra("webrecipe");

        //reference to items in view
        multiTextComment = (MultiAutoCompleteTextView)findViewById(R.id.editTextComment);
        editTextName = (EditText)findViewById(R.id.editTextName);
        editTextDate = (EditText) findViewById(R.id.editTextDate);
        webView = (WebView)findViewById(R.id.webView);

        //setting up the webview to handle images and javascript
        webView.setWebViewClient(new MyBrowser());
        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.getSettings().setJavaScriptEnabled(true);
        //specifying the type of scrollbars
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);

        //checking whether we received any data by the intent -> if we did, then sets data into the views text entries and loads url in webview
        if (webrecipe != null) {
            editTextName.setText(webrecipe.getName());
            editTextDate.setText(webrecipe.getDate());
            multiTextComment.setText(webrecipe.getComment());
            webView.loadUrl(webrecipe.getUrl());
        } else {
            //if no data have been received by intent, then use google.com as start page for webview
            webView.loadUrl(getString(R.string.url));
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
                //Code to run when the Create recipe item is clicked
                save();
                Intent intent1 = new Intent(this, MainActivity.class);
                startActivity(intent1);
                return true;
            case R.id.action_delete_recipe:
                //only possible to delete if there is an object to delete
                //otherwise it just reacts to the click but does'nt do anything
                if (webrecipe != null) {
                    delete();
                    Intent intent2 = new Intent(this, MainActivity.class);
                    startActivity(intent2);
                } else {
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.no_delete_webrecipe_toast), Toast.LENGTH_LONG).show();

                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void save(){
        //save method takes all data in fields and saves them as a Webrecipe object
        Webrecipe newWebrecipe = new Webrecipe(
                editTextName.getText().toString(),
                multiTextComment.getText().toString(),
                editTextDate.getText().toString(),
                webView.getUrl());

        //if we did'nt get any data from the intent when this activity was initialized then add the webrecipe object as a new object
        if (webrecipe == null) {
            db.addWebrecipe(newWebrecipe);
            //otherwise update the object with the new data and use the old name and url as reference to which row in the table to update
        } else {
            db.updateWebrecipe(newWebrecipe, webrecipe.getName(), webrecipe.getUrl());
        }
    }

    //delete method just calls method from RecipeDatabaseHelper and takes the recipe from the intent as parameter
    public void delete(){
        db.deleteWebrecipe(webrecipe);
    }

    @Override
    public void onBackPressed(){
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }
}
