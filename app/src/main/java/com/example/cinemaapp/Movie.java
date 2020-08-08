package com.example.cinemaapp;

import android.net.Uri;

public class Movie {
    private String movieName,des;
    private int image;

    public Movie(String movieName, String des, int image) {
        this.movieName = movieName;
        this.des = des;
        this.image = image;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
