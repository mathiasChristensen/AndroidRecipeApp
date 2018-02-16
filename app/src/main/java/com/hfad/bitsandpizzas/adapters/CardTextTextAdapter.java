package com.hfad.bitsandpizzas.adapters;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;
import android.view.View;

import com.hfad.bitsandpizzas.R;


public class CardTextTextAdapter extends RecyclerView.Adapter<CardTextTextAdapter.ViewHolder>{


    public static class ViewHolder extends RecyclerView.ViewHolder {
        //define a viewHolder
        private CardView cardView;

        //defining the type of data to be shown by viewholder -> Cardview
        public ViewHolder(CardView v) {
            //super call includes metadata and item's position in recyclerview
            super(v);
            cardView = v;
        }
    }

    private String[] textArray1;
    private String[] textArray2;
    private Listener listener;

    //listener interface for recyclerview click
    public static interface Listener {
        public void onClick(int position);
    }

    public CardTextTextAdapter(String[] textArray1, String[] textArray2){
        this.textArray1 = textArray1;
        this.textArray2 = textArray2;
    }

    //fragments will use this method
    public void setListener(Listener listener) {
        this.listener = listener;
    }

    @Override
    public CardTextTextAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //viewgroup is the recycler itself

        //creating the views
        CardView cv = (CardView) LayoutInflater.from(parent.getContext())
                //specifying the layout to use -> card_image
                .inflate(R.layout.card_image, parent, false);

        return new ViewHolder(cv);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        //holder = the type of recyclerview.viewholder to populate
        //position = item position in the viewgroup

        //setting values inside the view

        //populating the cardviews two textviews
        CardView cardView = holder.cardView;
        TextView textView1 = (TextView)cardView.findViewById(R.id.info_text1);
        TextView textView2 = (TextView)cardView.findViewById(R.id.info_text2);
        textView1.setText(textArray1[position]);
        textView2.setText(textArray2[position]);

        //when cardview is clicked call method onClick
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                if (listener != null){
                    listener.onClick(position);
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        //returns number of items in data set
        return textArray1.length;
    }
}
