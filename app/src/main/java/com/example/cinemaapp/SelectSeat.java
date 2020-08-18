package com.example.cinemaapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class SelectSeat extends AppCompatActivity {

    GridLayout mainGrid;
    Double seatPrice = 15.00;
    Double totalCost = 0.00;
    int totalSeat = 0;
    TextView totalPrice;
    TextView totalBookedSeat, detail;

    private Button btnProceed, btnCancel;

    //private FirebaseAuth firebaseAuth;
    //DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_seat);

        //getSupportActionBar().setTitle("Select Seats");
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mainGrid = (GridLayout) findViewById(R.id.mainGrid);
        totalBookedSeat = (TextView) findViewById(R.id.total_seats);
        totalPrice = (TextView) findViewById(R.id.total_cost);
        btnProceed = (Button) findViewById(R.id.btnProceed);
        btnCancel = (Button) findViewById(R.id.btnCancel);
        detail=findViewById(R.id.detail);
        //databaseReference = FirebaseDatabase.getInstance().getReference();

        setToggleEvent(mainGrid);

        Bundle extras = getIntent().getExtras();
        String name, cinema, time;


        if (extras != null) {
            name = extras.getString("name");
            cinema = extras.getString("place");
            time = extras.getString("time");
            detail.setText(name+"\n"+cinema+"\n"+time);
            // seat = extras.getString("TOTALSEAT");
            // and get whatever type user account id is
            //mAmount.setText(String.valueOf(amount));
        }

        btnProceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String totalPriceI=totalPrice.getText().toString().trim();
                String totalBookedSeatsI=totalBookedSeat.getText().toString().trim();
                String ticket=detail.getText().toString();
                Intent intent=new Intent(SelectSeat.this,Payment.class);
                intent.putExtra("TOTALCOST",totalPriceI);
                intent.putExtra("TOTALSEAT",totalBookedSeatsI);
                intent.putExtra("ticket", ticket);
               // intent.putExtra("TOTALSEAT",totalBookedSeatsI);
                startActivity(intent);
               /*PaymentDetail paymentDetail=new PaymentDetail(totalBookedSeatsI,totalPriceI);

                //FirebaseUser user=firebaseAuth.getCurrentUser();
                //databaseReference.child(user.getUid()).child("SeatDetails").setValue(paymentDetail);

                Intent intent=new Intent(SelectSeat.this,Payment.class);
                intent.putExtra("TOTALCOST",totalPriceI);
                intent.putExtra("TOTALSEAT",totalBookedSeatsI);
                startActivity(intent);*/

            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SelectSeat.this, MovieDetails.class);
                startActivity(intent);
            }
        });


    }

    private void setToggleEvent(GridLayout mainGrid) {
        for (int i = 0; i < mainGrid.getChildCount(); i++) {
            final CardView cardView = (CardView) mainGrid.getChildAt(i);
            final int finalI = i;
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (cardView.getCardBackgroundColor().getDefaultColor() == -1) {
                        //Change background color
                        cardView.setCardBackgroundColor(Color.parseColor("#00FF00"));
                        totalCost += seatPrice;
                        ++totalSeat;
                        Toast.makeText(SelectSeat.this, "You Selected Seat Number :" + (finalI + 1), Toast.LENGTH_SHORT).show();

                    } else {
                        //Change background color
                        cardView.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
                        totalCost -= seatPrice;
                        --totalSeat;
                        Toast.makeText(SelectSeat.this, "You Unselected Seat Number :" + (finalI + 1), Toast.LENGTH_SHORT).show();
                    }
                    totalPrice.setText("" + totalCost + "0");
                    totalBookedSeat.setText("" + totalSeat);
                }
            });
        }
    }
}