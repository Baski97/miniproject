package com.example.cinemaapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.Calendar;


public class MovieDetails extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ImageView imageV1;
        Button oneU12, oneU4, oneU9, sv4;
        Button btndate;
        TextView textV1;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        getSupportActionBar().setTitle("Movie Details");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        imageV1 = (ImageView) findViewById(R.id.imageV1);
        oneU12 = (Button) findViewById(R.id.oneU12);
        oneU4 = (Button) findViewById(R.id.oneU4);
        oneU9 = (Button) findViewById(R.id.oneU9);
        sv4 = (Button) findViewById(R.id.sv4);
        textV1 = (TextView) findViewById(R.id.textV1);
        btndate = (Button)findViewById(R.id.btndate);

        btndate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });

        Intent in = getIntent();
        Bundle b = in.getExtras();

        String img=getIntent().getStringExtra("Images");
        imageV1.setImageURI(Uri.parse(img));

        String textname = getIntent().getStringExtra("MovieName");
        textV1.setText(textname);

        oneU12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MovieDetails.this, SelectSeat.class);
                intent.putExtra("oneU12cinema", "ONE UTAMA");
                intent.putExtra("oneU12","12PM");
                startActivity(intent);

                Intent intent1 = new Intent(MovieDetails.this, MakePayment.class);
                intent1.putExtra("oneU12cinema", "ONE UTAMA");
                intent1.putExtra("oneU12","12PM");
                startActivity(intent1);
            }
        });

        oneU4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MovieDetails.this, SelectSeat.class);
                intent.putExtra("oneU4cinema", "ONE UTAMA");
                intent.putExtra("oneU4","4PM");
                startActivity(intent);

                Intent intent1 = new Intent(MovieDetails.this, MakePayment.class);
                intent1.putExtra("oneU4cinema", "ONE UTAMA");
                intent1.putExtra("oneU4","4PM");
                startActivity(intent1);
            }
        });

        oneU9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MovieDetails.this, SelectSeat.class);
                intent.putExtra("oneU9cinema", "ONE UTAMA");
                intent.putExtra("oneU9","9PM");
                startActivity(intent);

                Intent intent1 = new Intent(MovieDetails.this, SelectSeat.class);
                intent1.putExtra("oneU9cinema", "ONE UTAMA");
                intent1.putExtra("oneU9","9PM");
                startActivity(intent1);
            }
        });

        sv4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MovieDetails.this, SelectSeat.class);
                intent.putExtra("sv4cinema", "SUNWAY VELOCITY");
                intent.putExtra("sv4","4PM");
                startActivity(intent);

                Intent intent1 = new Intent(MovieDetails.this, SelectSeat.class);
                intent1.putExtra("sv4cinema", "SUNWAY VELOCITY");
                intent1.putExtra("sv4","4PM");
                startActivity(intent1);
            }
        });
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR,year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, day);

        String currentDateString = DateFormat.getDateInstance(DateFormat.MEDIUM).format(c.getTime());

        TextView dateview = (TextView)findViewById(R.id.dateview);
        dateview.setText(currentDateString);
    }
}