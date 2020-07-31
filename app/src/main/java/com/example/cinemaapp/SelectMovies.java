package com.example.cinemaapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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

public class SelectMovies extends AppCompatActivity {
    ListView lv;

    String mMovies[] = {"Avenger: Endgame", "Crawl", "Frozen 2", "Spiderman: Far From Home", "Train to Busan : Peninsula", "Trolls World Tour"};
    String mDescription[] = {"Action, Adventure, Superhero", "Horror, Disaster, Action", "Musical, Fantasy, Comedy", "Action, Fantasy, Superhero", "Thriller, Action, Horror", "Children's Film, Comedy, Animation"};
    int mImages[] = {R.drawable.avenger, R.drawable.crawl, R.drawable.frozen2, R.drawable.spidermanfarfromhome, R.drawable.traintobusan, R.drawable.trollworldtour};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_movies);

        lv = (ListView) findViewById(R.id.lv);

        MyAdapter adapter = new MyAdapter(SelectMovies.this, mMovies, mDescription, mImages);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                if (position == 0 ) {
                    Intent intent = new Intent(SelectMovies.this, MovieDetails.class);
                    intent.putExtra("MovieName", lv.getItemAtPosition(position).toString());
                    intent.putExtra("MovieDescription",lv.getItemAtPosition(position).toString());
                    intent.putExtra("Images",lv.getItemAtPosition(position).toString());
                    startActivity(intent);
                }
            }
        });
    }

    class MyAdapter extends ArrayAdapter<String>{

        Context context;
        String rMovies[];
        String rDes[];
        int rImages[];

        MyAdapter(Context c, String movies[], String description[], int imgs[]) {
            super(c, R.layout.row, R.id.text1, movies);
            this.context = c;
            this.rMovies = movies;
            this.rDes = description;
            this.rImages = imgs;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = layoutInflater.inflate(R.layout.row, parent, false);
            ImageView images = row.findViewById(R.id.image);
            TextView myMovies = row.findViewById(R.id.text1);
            TextView myDescription = row.findViewById(R.id.text2);


            images.setImageResource(rImages[position]);
            myMovies.setText(rMovies[position]);
            myDescription.setText(rDes[position]);

            return row;
        }
    }
}