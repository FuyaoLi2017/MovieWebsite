package com.fablix.androidapp;

import java.util.ArrayList;

public class SingleMovie {



    private String title;

    private String year;

    private String director;

    private ArrayList<String> genres;

    private ArrayList<String> stars;

        public SingleMovie(String title, String year,String director,ArrayList<String> genres,ArrayList<String> stars) {
            this.title = title;
            this.year = year;
            this.director=director;
            this.genres=genres;
            this.stars=stars;
        }

        public String getTitle() {
            return title;
        }

        public String getYear() {
            return year;
        }

        public String getDirector() {
        return director;
        }

         public ArrayList<String> getGenres() {
        return genres;
    }

         public ArrayList<String> getStars() {
        return stars;
    }

}
