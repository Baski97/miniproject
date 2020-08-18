package com.example.cinemaapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.Calendar;

public class MovieDetails extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    ImageView imageV1;
    //Button oneU12, oneU4, oneU9, sv4;
    Button btndate;
    TextView textV1,date,place,time;
    DialogFragment datePicker;
    RadioButton utama,sunway,time1,time2,time3,stime1;
    RadioGroup rd,utamatime,sunwaytime;
    Button procced;
    private Button bt;
    //String place,time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);


        bt=findViewById(R.id.booking);



        imageV1 = (ImageView) findViewById(R.id.imageV1);
        textV1 = (TextView) findViewById(R.id.textV1);
        btndate = (Button)findViewById(R.id.btndate);
        procced=findViewById(R.id.procced);
        date=findViewById(R.id.dateview);
        utama=findViewById(R.id.utama);
        sunway=findViewById(R.id.sunway);
        rd=findViewById(R.id.cinemahall);

        time1=findViewById(R.id.time1);
        time2=findViewById(R.id.time2);
        time3=findViewById(R.id.time3);
        utamatime=findViewById(R.id.utamatime);


        stime1=findViewById(R.id.stime1);
        sunwaytime=findViewById(R.id.sunwaytime);

        place=findViewById(R.id.place);
        time=findViewById(R.id.time);



        if(getIntent().hasExtra("mName") && getIntent().hasExtra("mDes")){
            String name=getIntent().getStringExtra("mName");
            String des=getIntent().getStringExtra("mDes");
            int image=getIntent().getIntExtra("mPoster",0);


            imageV1.setImageResource(image);
            textV1.setText(name+"\n"+des);



        }else {
            Toast.makeText(MovieDetails.this, "Empty", Toast.LENGTH_LONG).show();
        }

        procced.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(utama.isChecked()){
                    Toast.makeText(MovieDetails.this, "Utama", Toast.LENGTH_LONG).show();
                    rd.setVisibility(View.GONE);
                    utamatime.setVisibility(View.VISIBLE);
                    //String place=utama.getText().toString();
                    place.setText(utama.getText().toString());
                    if(time1.isChecked()){
                        Toast.makeText(MovieDetails.this, "You have choose: Utama,Time: 12PM", Toast.LENGTH_LONG).show();
                        procced.setVisibility(View.GONE);
                        bt.setVisibility(View.VISIBLE);
                        //String time=time1.getText().toString();
                        time.setText(time1.getText().toString());


                    }else if(time2.isChecked()){
                        Toast.makeText(MovieDetails.this, "You have choose: Utama,Time: 4PM", Toast.LENGTH_LONG).show();
                        procced.setVisibility(View.GONE);
                        bt.setVisibility(View.VISIBLE);
                        //String time=time2.getText().toString();
                        time.setText(time2.getText().toString());


                    }else if(time3.isChecked()){
                        Toast.makeText(MovieDetails.this, "You have choose: Utama,Time: 9PM", Toast.LENGTH_LONG).show();
                        procced.setVisibility(View.GONE);
                        bt.setVisibility(View.VISIBLE);
                        //String time=time3.getText().toString();
                        time.setText(time3.getText().toString());


                    }


                }else {
                    Toast.makeText(MovieDetails.this, "Sunway", Toast.LENGTH_LONG).show();
                    rd.setVisibility(View.GONE);
                    sunwaytime.setVisibility(View.VISIBLE);
                    //String place=sunway.getText().toString();
                    place.setText(sunway.getText().toString());
                    if (stime1.isChecked()){
                        Toast.makeText(MovieDetails.this, "You have choose: Sunway,Time: 4PM", Toast.LENGTH_LONG).show();
                        procced.setVisibility(View.GONE);
                        bt.setVisibility(View.VISIBLE);
                        //String time=stime1.getText().toString();
                        time.setText(stime1.getText().toString());




                    }

                }

            }
        });

///confirmation button
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MovieDetails.this,SelectSeat.class);
                String info= textV1.getText().toString();
                String cinema= place.getText().toString();
                String timing=time.getText().toString();
                intent.putExtra("name",info);
               intent.putExtra("place", cinema);
               intent.putExtra("time", timing);
                startActivity(intent);
            }
        });


        btndate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });



          }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String currentDateString = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());

        date.setText(currentDateString);

    }
}
