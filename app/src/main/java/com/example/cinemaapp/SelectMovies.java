package com.example.cinemaapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class SelectMovies extends AppCompatActivity {
    private RecyclerView lv;
    private List<Movie> movieList=new ArrayList<>();
    private AdapterCinema adapterCinema;
    private Movie movie;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_movies);

        lv = findViewById(R.id.lv);
        adapterCinema=new AdapterCinema(movieList,this);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        lv.setLayoutManager(mLayoutManager);
        lv.setItemAnimator(new DefaultItemAnimator());
        lv.setAdapter(adapterCinema);

        prepareMovieData();


    }

    private void prepareMovieData() {
        movie=new Movie("Avenger: Endgame","Action, Adventure, Superhero",R.drawable.avenger);
        movieList.add(movie);

        movie=new Movie("Crawl","Horror, Disaster, Action",R.drawable.crawl);
        movieList.add(movie);

        movie=new Movie("Frozen 2","Musical, Fantasy, Comedy", R.drawable.frozen2);
        movieList.add(movie);

        movie=new Movie("Spiderman: Far From Home","Action, Fantasy, Superhero",R.drawable.spidermanfarfromhome);
        movieList.add(movie);

        movie=new Movie("Train to Busan : Peninsula","Thriller, Action, Horror",R.drawable.traintobusan);
        movieList.add(movie);

        movie=new Movie("Trolls World Tour","Children's Film, Comedy, Animation",R.drawable.trollworldtour);
        movieList.add(movie);
    }


}
