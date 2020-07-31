package com.example.cinemaapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class CheckMovies extends AppCompatActivity {

    private ViewPager2 viewPager2;
    private Handler slideHandler = new Handler();
    private Button btnselectmovies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_movies);

        btnselectmovies = findViewById(R.id.btnselectmovies);

        viewPager2 = findViewById(R.id.viewPagerImageSlider);

        List<SliderItem> sliderItems = new ArrayList<>();
        sliderItems.add(new SliderItem(R.drawable.avenger));
        sliderItems.add(new SliderItem(R.drawable.crawl));
        sliderItems.add(new SliderItem(R.drawable.frozen2));
        sliderItems.add(new SliderItem(R.drawable.spidermanfarfromhome));
        sliderItems.add(new SliderItem(R.drawable.traintobusan));
        sliderItems.add(new SliderItem(R.drawable.trollworldtour));

        viewPager2.setAdapter(new SliderAdapter(sliderItems, viewPager2));

        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(40));
        compositePageTransformer.addTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                float r = 1 - Math.abs(position);
                page.setScaleY(0.85f + r * 0.15f);
            }
        });

        viewPager2.setPageTransformer(compositePageTransformer);

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                slideHandler.removeCallbacks(slideRunnable);
                slideHandler.postDelayed(slideRunnable, 3000);
            }
        });

        btnselectmovies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMoviesDetail();
            }
        });
    }

    private  Runnable slideRunnable = new Runnable() {
        @Override
        public void run() {
            viewPager2.setCurrentItem(viewPager2.getCurrentItem() + 1);
        }
    };

    @Override
    protected void onPause() {
        super.onPause();
        slideHandler.removeCallbacks(slideRunnable);
    }

    @Override
    protected void onResume() {
        super.onResume();
        slideHandler.postDelayed(slideRunnable, 3000);
    }

    public void openMoviesDetail(){
        Intent intent = new Intent(this, SelectMovies.class);
        startActivity(intent);
    }
}