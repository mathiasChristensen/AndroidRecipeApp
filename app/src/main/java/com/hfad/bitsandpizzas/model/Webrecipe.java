package com.hfad.bitsandpizzas.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Webrecipe implements Parcelable {

    //Class implements parcelable to make it possible to pass object with intent


    private String name;
    private String comment;
    private String date;
    private String url;

    public Webrecipe(String name, String comment, String date, String url) {
        this.name = name;
        this.comment = comment;
        this.date = date;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public String getComment() {
        return comment;
    }

    public String getDate() {
        return date;
    }

    public String getUrl() {
        return url;
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
        dest.writeString(comment);
        dest.writeString(date);
        dest.writeString(url);
    }

    public static final Parcelable.Creator<Webrecipe> CREATOR = new Parcelable.Creator<Webrecipe>() {

        //creating Webrecipe object from the parcelable data
        public Webrecipe createFromParcel(Parcel in) {
            Webrecipe webrecipe = new Webrecipe(
                    in.readString(),
                    in.readString(),
                    in.readString(),
                    in.readString());
            return webrecipe;
        }

        public Webrecipe[] newArray(int size) {
            return new Webrecipe[size];
        }
    };
}
