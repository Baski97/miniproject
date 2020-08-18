package com.example.cinemaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import static android.content.Intent.ACTION_VIEW;

public class Location extends AppCompatActivity {

    private ImageView c1,c2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        c1=findViewById(R.id.c1);
        c2=findViewById(R.id.c2);

        c1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    Intent intent = new Intent(ACTION_VIEW, Uri.parse("geo:<3.7352434>,<103.1163852>?q=(One Utama)"));
                    startActivity(intent);





            }
        });

        c2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(ACTION_VIEW, Uri.parse("geo:<3.142806>,<101.6048972>?q=(Sunway+Velocity)"));
                startActivity(intent);





            }
        });
    }
}