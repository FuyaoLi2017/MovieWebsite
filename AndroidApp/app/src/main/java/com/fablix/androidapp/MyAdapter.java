//Source: Inspiration from developer.android.com

package com.fablix.androidapp;

import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.LinkedList;
import java.util.Map;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private LinkedList<Map<String, Object>> list;
    private String id;
    private  ClickListener clicklistener = null;

    public MyAdapter(LinkedList<Map<String, Object>> list) {


        this.list=list;

    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = View.inflate(viewGroup.getContext(), R.layout.itemlist, null);

        return new ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        final public TextView title;
        public TextView year;
        public TextView director;
        public LinkedList<TextView> genres = new LinkedList<>();
        public LinkedList<TextView> stars = new LinkedList<>();
        private CardView main;

        public ViewHolder(final View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.title);
            year = itemView.findViewById(R.id.year);
            director = itemView.findViewById(R.id.director);
            genres.add((TextView)itemView.findViewById(R.id.genre1));
            genres.add((TextView)itemView.findViewById(R.id.genre2));
            genres.add((TextView)itemView.findViewById(R.id.genre3));
            stars.add((TextView)itemView.findViewById(R.id.star1));
            stars.add((TextView)itemView.findViewById(R.id.star2));
            stars.add((TextView)itemView.findViewById(R.id.star3));
            stars.add((TextView)itemView.findViewById(R.id.star4));
            main = (CardView) itemView.findViewById(R.id.main);
            main.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(itemView.getContext(), "Position:" + Integer.toString(getPosition()), Toast.LENGTH_SHORT).show();
                    if(clicklistener !=null){
                        clicklistener.itemClicked(v,getPosition());
                    }
                }
            });


        }
    }




    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        id=list.get(i).get("id").toString();
        viewHolder.title.setText(list.get(i).get("title").toString());
        viewHolder.title.setVisibility(View.VISIBLE);
        viewHolder.year.setText(list.get(i).get("year").toString());
        viewHolder.year.setVisibility(View.VISIBLE);
        viewHolder.director.setText(list.get(i).get("director").toString());
        viewHolder.director.setVisibility(View.VISIBLE);

        LinkedList<String> genreList = (LinkedList)list.get(i).get("genres");
        LinkedList<String> starList = (LinkedList)list.get(i).get("stars");
        for (int j = 0; j < genreList.size(); ++ j) {
            viewHolder.genres.get(j).setText(genreList.get(j));
            viewHolder.genres.get(j).setVisibility(View.VISIBLE);
        }
        for (int j = 0; j < starList.size(); ++ j) {
            viewHolder.stars.get(j).setText(starList.get(j));
            viewHolder.stars.get(j).setVisibility(View.VISIBLE);
        }




    }


    @Override
    public int getItemCount() {
        return list.size();
    }



    public void setClickListener(ClickListener clickListener){
        this.clicklistener = clickListener;
    }






}