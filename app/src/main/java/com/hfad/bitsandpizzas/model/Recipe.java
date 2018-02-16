package com.hfad.bitsandpizzas.model;


import android.os.Parcel;
import android.os.Parcelable;

public class Recipe implements Parcelable {

    //Class implements parcelable to make it possible to pass object with intent

    private String name;
    private String description;
    private String date;
    private String people;
    private String ingredient;
    private String approach;


    public Recipe(String name, String description, String date, String people, String ingredient, String approach) {
        this.name = name;
        this.description = description;
        this.date = date;
        this.people = people;
        this.ingredient = ingredient;
        this.approach = approach;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getDate() {
        return date;
    }

    public String getPeople() {
        return people;
    }

    public String getIngredient() {
        return ingredient;
    }

    public String getApproach() {
        return approach;
    }

    public void setName(String name) {
        this.name = name;
    }


    //---------------------------Parcelable code---------------------------------------

    @Override
    public int describeContents() {
        return 0;
    }

    //taking every parameter from object and making it into a parcelable object
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(description);
        dest.writeString(date);
        dest.writeString(people);
        dest.writeString(ingredient);
        dest.writeString(approach);
    }

    public static final Parcelable.Creator<Recipe> CREATOR = new Parcelable.Creator<Recipe>() {

        //creating Recipe object from the parcelable data
        public Recipe createFromParcel(Parcel in) {
            Recipe recipe = new Recipe(
                    in.readString(),
                    in.readString(),
                    in.readString(),
                    in.readString(),
                    in.readString(),
                    in.readString());
            return recipe;
        }

        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }

    };
}
