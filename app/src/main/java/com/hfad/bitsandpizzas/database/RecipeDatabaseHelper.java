package com.hfad.bitsandpizzas.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.hfad.bitsandpizzas.model.Recipe;
import com.hfad.bitsandpizzas.model.Webrecipe;


public class RecipeDatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "recipesDatabase";
    private static final int DB_VERSION = 1;

    private SQLiteDatabase db;

    public RecipeDatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

//creates database by calling updatemydatabase()
    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;
        updateMyDatabase(db, 0, DB_VERSION);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        updateMyDatabase(db, oldVersion, newVersion);
    }

    //method used to setup database based on the current version of the db
    private void updateMyDatabase(SQLiteDatabase db, int oldversion, int newVersion) {
        if (oldversion < 1) {
            db.execSQL("CREATE TABLE RECIPES ( " +
                    "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "NAME TEXT, " +
                    "DESCRIPTION TEXT, " +
                    "DATE TEXT, " +
                    "PEOPLE TEXT, " +
                    "INGREDIENTS TEXT, " +
                    "APPROACH TEXT);");
            db.execSQL("CREATE TABLE WEBRECIPES ( " +
                    "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "NAME TEXT, " +
                    "COMMENT TEXT, " +
                    "DATE TEXT, " +
                    "URL TEXT);");

            //initial test data for recipeApp
            insertRecipe("name a", "Description-Vivamus sagittis lacus vel augue laoreet rutrum faucibus dolor auctor.", "02/02/2012", "4", "ingrdients-Consectetur Cursus Vestibulum Vulputate Dolor", "approach-Maecenas faucibus mollis interdum. Nullam quis risus eget urna mollis ornare vel eu leo. Vivamus sagittis lacus vel augue laoreet rutrum faucibus dolor auctor.");
            insertRecipe("name b", "Description-Aenean eu leo quam. Pellentesque ornare sem lacinia quam venenatis vestibulum.", "30/02/2015", "2", "ingrdients-Tellus Ligula Sem Ullamcorper Risus", "approach-Vivamus sagittis lacus vel augue laoreet rutrum faucibus dolor auctor. Nulla vitae elit libero, a pharetra augue. Curabitur blandit tempus porttitor.");
            insertRecipe("name c", "Description-Integer posuere erat a ante venenatis dapibus posuere velit aliquet.", "02/12/2015", "4", "ingrdients-Dapibus Ipsum Quam Cras Venenatis", "approach-Donec sed odio dui. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Nulla vitae elit libero, a pharetra augue.");
            insertRecipe("name d", "Description-Cras mattis consectetur purus sit amet fermentum.", "22/09/2012", "5", "ingrdients-Vehicula Ultricies Inceptos Tortor Sit", "approach-Nulla vitae elit libero, a pharetra augue. Cras justo odio, dapibus ac facilisis in, egestas eget quam. Sed posuere consectetur est at lobortis.");
            insertRecipe("name e", "Description-Donec id elit non mi porta gravida at eget metus.", "19/05/2016", "1", "ingrdients-Purus Vulputate Fringilla Adipiscing Ultricies", "approach-Lorem ipsum dolor sit amet, consectetur adipiscing elit. Maecenas faucibus mollis interdum. Sed posuere consectetur est at lobortis.");
            insertRecipe("name f", "Description-Cras justo odio, dapibus ac facilisis in, egestas eget quam.", "23/04/2011", "3", "ingrdients-Risus Sollicitudin Sit Nullam Tortor", "approach-Maecenas sed diam eget risus varius blandit sit amet non magna. Cras mattis consectetur purus sit amet fermentum. Maecenas faucibus mollis interdum.");
            insertRecipe("name g", "Description-Curabitur blandit tempus porttitor.", "03/04/2009", "4", "ingrdients-Etiam Adipiscing Condimentum Inceptos Vehicula", "approach-Donec id elit non mi porta gravida at eget metus. Cras mattis consectetur purus sit amet fermentum. Aenean lacinia bibendum nulla sed consectetur.");
            insertRecipe("name h", "Description-Cras justo odio, dapibus ac facilisis in, egestas eget quam.", "01/01/2015", "4", "ingrdients-Amet Quam Lorem Sollicitudin Magna", "approach-Donec sed odio dui. Maecenas sed diam eget risus varius blandit sit amet non magna. Lorem ipsum dolor sit amet, consectetur adipiscing elit.");
            insertRecipe("name i", "Description-Morbi leo risus, porta ac consectetur ac, vestibulum at eros.", "14/06/2013", "2", "ingrdients-Aenean Cras Lorem Purus Inceptos", "approach-Vivamus sagittis lacus vel augue laoreet rutrum faucibus dolor auctor. Nullam id dolor id nibh ultricies vehicula ut id elit. Maecenas faucibus mollis interdum.");
            insertRecipe("name j", "Description-Donec ullamcorper nulla non metus auctor fringilla.", "13/02/2011", "2", "ingrdients-Quam Amet Consectetur Tristique Sem", "approach-Sed posuere consectetur est at lobortis. Maecenas sed diam eget risus varius blandit sit amet non magna. Donec id elit non mi porta gravida at eget metus.");
            insertRecipe("name k", "Description-Praesent commodo cursus magna, vel scelerisque nisl consectetur et.", "25/07/2014", "4", "ingrdients-Pellentesque Ornare Mollis Ligula Vestibulum", "approach-Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Aenean eu leo quam. Pellentesque ornare sem lacinia quam venenatis vestibulum. Cras justo odio, dapibus ac facilisis in, egestas eget quam.");
            insertRecipe("name l", "Description-Etiam porta sem malesuada magna mollis euismod.", "08/09/2010", "3", "ingrdients-Sit Dolor Ridiculus Vehicula Bibendum", "approach-Praesent commodo cursus magna, vel scelerisque nisl consectetur et. Cras justo odio, dapibus ac facilisis in, egestas eget quam. Curabitur blandit tempus porttitor.");
            insertRecipe("name m", "Description-Morbi leo risus, porta ac consectetur ac, vestibulum at eros.", "05/06/2007", "3", "ingrdients-Pellentesque Fusce Nullam Nibh Mattis", "approach-Fusce dapibus, tellus ac cursus commodo, tortor mauris condimentum nibh, ut fermentum massa justo sit amet risus. Vestibulum id ligula porta felis euismod semper. Cras mattis consectetur purus sit amet fermentum.");
            insertRecipe("name n", "Description-Duis mollis, est non commodo luctus, nisi erat porttitor ligula, eget lacinia odio sem nec elit.", "07/06/2016", "4", "ingrdients-Commodo Dapibus Mattis Sollicitudin Elit", "approach-Fusce dapibus, tellus ac cursus commodo, tortor mauris condimentum nibh, ut fermentum massa justo sit amet risus. Donec ullamcorper nulla non metus auctor fringilla. Curabitur blandit tempus porttitor.");
            insertWebrecipe("name wa", "Comment-", "23/04/2011", "http://www.familycircle.com/recipe/chicken/quick-chicken-nuggets/");
            insertWebrecipe("name wb", "Comment-", "22/09/2012", "http://www.fitnessmagazine.com/recipe/chicken/mediterranean-chicken-and-pasta/");
            insertWebrecipe("name wc", "Comment-", "13/02/2011", "http://www.familycircle.com/recipe/chicken/indian-spiced-chicken-thighs/");
            insertWebrecipe("name wd", "Comment-", "08/09/2010", "http://www.bhg.com/recipe/chicken/maryland-fried-chicken-with-creamy-gravy/");
            insertWebrecipe("name we", "Comment-", "23/04/2011", "http://www.bhg.com/recipe/chicken/chicken-with-artichokes/");
            insertWebrecipe("name wf", "Comment-", "05/06/2007", "http://www.bhg.com/recipe/chicken/buffalo-chicken-drumsticks-with-blue-cheese-dip/");
            insertWebrecipe("name wg", "Comment-", "02/02/2012", "https://secure.parents.com/common/profile/regStep1.jsp?recipeId=RU219092&regSource=7168&returnURL=http%3A//www.parents.com/recipe/cranberry-apple-pork-roast/");
            insertWebrecipe("name wh", "Comment-", "30/02/2015", "http://www.bhg.com/recipe/chicken/chicken-with-sourdough-mushroom-stuffing/");
            insertWebrecipe("name wi", "Comment-", "25/07/2014", "http://www.bhg.com/recipe/beef/italian-chili/");
            insertWebrecipe("name wj", "Comment-", "03/04/2009", "http://www.midwestliving.com/recipe/cider-baked-stuffed-apples-with-salty-caramel-sauce/");
            insertWebrecipe("name wk", "Comment-", "22/09/2012", "http://www.bhg.com/recipe/poultry/chicken-with-apricots-and-dried-plums/");
            insertWebrecipe("name wl", "Comment-", "07/06/2016", "http://www.diabeticlivingonline.com/recipe/chicken/chicken-with-caribbean-salsa/");

        }
        if (oldversion < 2) {
            //put future upgrade db code here
             }
    }

    //---------------------------------------------------------------------------------------

    //CRUD RECIPE

    private static final String RECIPE_TABLE_NAME = "RECIPES";
    private static final String RECIPE_COLUMN_NAME = "NAME";
    private static final String RECIPE_COLUMN_DESCRIPTION = "DESCRIPTION";
    private static final String RECIPE_COLUMN_DATE = "DATE";
    private static final String RECIPE_COLUMN_PEOPLE = "PEOPLE";
    private static final String RECIPE_COLUMN_INGREDIENTS = "INGREDIENTS";
    private static final String RECIPE_COLUMN_APPROACH = "APPROACH";


    //as database gets closed after run of oncreate -> two different methods for insert/add have been created to accommodate this
    //one method where db is available
    private void insertRecipe (String name, String description, String date, String people, String ingredients, String approach) {
        ContentValues recipeValues = new ContentValues();
        recipeValues.put(RECIPE_COLUMN_NAME, name);
        recipeValues.put(RECIPE_COLUMN_DESCRIPTION, description);
        recipeValues.put(RECIPE_COLUMN_DATE, date);
        recipeValues.put(RECIPE_COLUMN_PEOPLE, people);
        recipeValues.put(RECIPE_COLUMN_INGREDIENTS, ingredients);
        recipeValues.put(RECIPE_COLUMN_APPROACH, approach);
        db.insert(RECIPE_TABLE_NAME, null, recipeValues);

    }

    //and one method where we get a new reference to the db
    public void addRecipe (Recipe recipe) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues recipeValues = new ContentValues();
        recipeValues.put(RECIPE_COLUMN_NAME, recipe.getName());
        recipeValues.put(RECIPE_COLUMN_DESCRIPTION, recipe.getDescription());
        recipeValues.put(RECIPE_COLUMN_DATE, recipe.getDate());
        recipeValues.put(RECIPE_COLUMN_PEOPLE, recipe.getPeople());
        recipeValues.put(RECIPE_COLUMN_INGREDIENTS, recipe.getIngredient());
        recipeValues.put(RECIPE_COLUMN_APPROACH, recipe.getApproach());
        db.insert(RECIPE_TABLE_NAME, null, recipeValues);
        db.close();
    }

    //updating method for Recipe
    //name and description used as where clause for the update
    public void updateRecipe(Recipe recipe, String oldName, String oldDescription) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues recipeValues = new ContentValues();
        recipeValues.put(RECIPE_COLUMN_NAME, recipe.getName());
        recipeValues.put(RECIPE_COLUMN_DESCRIPTION, recipe.getDescription());
        recipeValues.put(RECIPE_COLUMN_DATE, recipe.getDate());
        recipeValues.put(RECIPE_COLUMN_PEOPLE, recipe.getPeople());
        recipeValues.put(RECIPE_COLUMN_INGREDIENTS, recipe.getIngredient());
        recipeValues.put(RECIPE_COLUMN_APPROACH, recipe.getApproach());
        db.update(RECIPE_TABLE_NAME, recipeValues,
                RECIPE_COLUMN_NAME + " = ? AND " + RECIPE_COLUMN_DESCRIPTION + " = ?",
                new String[]{oldName, oldDescription});
        db.close();
    }

    //delete method for Recipe
    //name and description used as where clause for the deletion
    public void deleteRecipe(Recipe recipe){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(RECIPE_TABLE_NAME,
                RECIPE_COLUMN_NAME + " = ? AND " + RECIPE_COLUMN_DESCRIPTION + " = ?",
                new String[]{recipe.getName(), recipe.getDescription()});
    }

    //----------------------------------------------------------------------------------------

    //CRUD WEBRECIPE

    private static final String WEBRECIPE_TABLE_NAME = "WEBRECIPES";
    private static final String WEBRECIPE_COLUMN_NAME = "NAME";
    private static final String WEBRECIPE_COLUMN_COMMENT = "COMMENT";
    private static final String WEBRECIPE_COLUMN_DATE = "DATE";
    private static final String WEBRECIPE_COLUMN_URL = "URL";

    //as database gets closed after run of oncreate -> two different methods for insert/add have been created to accommodate this
    //one method where db is available
    private void insertWebrecipe(String name, String comment, String date, String url) {
        ContentValues webrecipeValues = new ContentValues();
        webrecipeValues.put(WEBRECIPE_COLUMN_NAME, name);
        webrecipeValues.put(WEBRECIPE_COLUMN_COMMENT, comment);
        webrecipeValues.put(WEBRECIPE_COLUMN_DATE, date);
        webrecipeValues.put(WEBRECIPE_COLUMN_URL, url);
        db.insert(WEBRECIPE_TABLE_NAME, null, webrecipeValues);
    }

    //and one method where we get a new reference to the db
    public void addWebrecipe(Webrecipe webrecipe) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues webrecipeValues = new ContentValues();
        webrecipeValues.put(WEBRECIPE_COLUMN_NAME, webrecipe.getName());
        webrecipeValues.put(WEBRECIPE_COLUMN_COMMENT, webrecipe.getComment());
        webrecipeValues.put(WEBRECIPE_COLUMN_DATE, webrecipe.getDate());
        webrecipeValues.put(WEBRECIPE_COLUMN_URL, webrecipe.getUrl());
        db.insert(WEBRECIPE_TABLE_NAME, null, webrecipeValues);
        db.close();
    }
    //updating method for Webrecipe
    //name and url used as where clause for the update
    public void updateWebrecipe(Webrecipe webrecipe, String oldName, String oldUrl) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues webrecipeValues = new ContentValues();
        webrecipeValues.put(WEBRECIPE_COLUMN_NAME, webrecipe.getName());
        webrecipeValues.put(WEBRECIPE_COLUMN_COMMENT, webrecipe.getComment());
        webrecipeValues.put(WEBRECIPE_COLUMN_DATE, webrecipe.getDate());
        webrecipeValues.put(WEBRECIPE_COLUMN_URL, webrecipe.getUrl());
        db.update(WEBRECIPE_TABLE_NAME, webrecipeValues,
                WEBRECIPE_COLUMN_NAME + " = ? AND " + WEBRECIPE_COLUMN_URL + " = ?",
                new String[]{oldName, oldUrl});
        db.close();
    }

    //delete method for Recipe
    //name and url used as where clause for the deletion
    public void deleteWebrecipe(Webrecipe webrecipe){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(WEBRECIPE_TABLE_NAME,
                WEBRECIPE_COLUMN_NAME + " = ? AND " + WEBRECIPE_COLUMN_URL + " = ?",
                new String[]{webrecipe.getName(), webrecipe.getUrl()});
    }

}
