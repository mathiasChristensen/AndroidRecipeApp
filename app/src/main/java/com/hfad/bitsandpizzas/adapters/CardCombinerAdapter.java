package com.hfad.bitsandpizzas.adapters;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hfad.bitsandpizzas.R;
import com.hfad.bitsandpizzas.model.Recipe;
import com.hfad.bitsandpizzas.model.Webrecipe;

import java.util.ArrayList;

public class CardCombinerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<Object> recipes;

    //final parameters used to check what kind of object in getItemViewType
    private final int RECIPE = 0, WEBRECIPE = 1;

    public CardCombinerAdapter(ArrayList<Object> recipes) {
        this.recipes = recipes;
    }

    @Override
    public int getItemCount() {
        return this.recipes.size();
    }

    //tells recycler view which type of view to inflate based on position
    @Override
    public int getItemViewType(int position){
        if (recipes.get(position) instanceof Recipe) {
            return RECIPE;
        } else if (recipes.get(position) instanceof Webrecipe) {
            return WEBRECIPE;
        }
        return -1;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());


        //tells recyclerview.adapter which viewholder object to create based on the viewtype returned
        switch (viewType) {
            case RECIPE:
                View v1 = inflater.inflate(R.layout.card_image, parent, false);
                viewHolder = new ViewHolder1(v1);
                break;
            case WEBRECIPE:
                View v2 = inflater.inflate(R.layout.card_image2, parent, false);
                viewHolder = new ViewHolder2(v2);
                break;
            default:
                View v = inflater.inflate(R.layout.card_image, parent, false);
                viewHolder = new ViewHolder1(v);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        //holder = the type of recyclerview.viewholder to populate
        //position = item position in the viewgroup

        //configure the viewholder with actual data that needs to be displayed
        //distinguishing between the two layouts and loading them with specified data

        switch (holder.getItemViewType()) {
            case RECIPE:
                ViewHolder1 vh1 = (ViewHolder1) holder;
                configureViewHolder1(vh1, position);
                break;
            case WEBRECIPE:
                ViewHolder2 vh2 = (ViewHolder2) holder;
                configureViewHolder2(vh2, position);
                break;
            default:
                ViewHolder1 vh = (ViewHolder1) holder;
                configureViewHolder1(vh, position);
        }

    }

    //configuring the individual recyclerview.viewholder objects

    private void configureViewHolder1(ViewHolder1 vh1, int position){
        Recipe recipe = (Recipe) recipes.get(position);
        if (recipe != null) {
            vh1.getLabel1().setText(recipe.getName());
            vh1.getLabel2().setText(recipe.getPeople());
        }
    }

    //configuring the individual recyclerview.viewholder objects

    private void configureViewHolder2(ViewHolder2 vh2, int position){
        Webrecipe webrecipe = (Webrecipe) recipes.get(position);
        if (webrecipe != null) {
            vh2.getLabel1().setText(webrecipe.getName());
            vh2.getLabel2().setText(webrecipe.getDate());
        }
    }

    //------------------------------------------------------------------

    private static class ViewHolder1 extends RecyclerView.ViewHolder {

        private TextView label1, label2;



        private ViewHolder1(View v) {
            //defining the type of data to be shown by viewholder
            super(v);
            label1 = (TextView)v.findViewById(R.id.info_text1);
            label2 = (TextView)v.findViewById(R.id.info_text2);
        }

        private TextView getLabel1() {
            return label1;
        }

        private TextView getLabel2() {
            return label2;
        }

        public void setLabel1(TextView label1) {
            this.label1 = label1;
        }

        public void setLabel2(TextView label2) {
            this.label2 = label2;
        }
    }

    //------------------------------------------------------------------


    private static class ViewHolder2 extends RecyclerView.ViewHolder {

        private TextView label1, label2;


        private ViewHolder2(View v) {
            //defining the type of data to be shown by viewholder
            super(v);
            label1 = (TextView)v.findViewById(R.id.info_text1);
            label2 = (TextView)v.findViewById(R.id.info_text2);
        }

        private TextView getLabel1() {
            return label1;
        }

        private TextView getLabel2() {
            return label2;
        }

        public void setLabel1(TextView label1) {
            this.label1 = label1;
        }

        public void setLabel2(TextView label2) {
            this.label2 = label2;
        }
    }



}
