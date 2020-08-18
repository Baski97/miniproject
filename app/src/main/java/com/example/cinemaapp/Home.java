package com.example.cinemaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Objects;

import javax.annotation.Nullable;

public class Home extends AppCompatActivity {

    Button logout, home, location;
    FirebaseAuth fb;
    TextView viewTicket;
    private FirebaseAuth.AuthStateListener Auth;
    //firebase
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth auth= FirebaseAuth.getInstance();
    String user= Objects.requireNonNull(auth.getCurrentUser()).getUid();
    CollectionReference cReff=db.collection(""+user);
    DocumentReference dReff=db.document(user+"/Ticket");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        logout= findViewById(R.id.logout);
        home= findViewById(R.id.home);
        viewTicket= findViewById(R.id.viewTicket);
        location=findViewById(R.id.location);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intentmain= new Intent(Home.this, MainActivity.class);
                startActivity(intentmain);
            }
        });

        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intentmain= new Intent(Home.this, Location.class);
                startActivity(intentmain);
            }
        });


        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Home.this,CheckMovies.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        cReff.addSnapshotListener(this, new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if(e!=null){
                    return;
                }
                String data="";
                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots){
                    Ticket ticket = documentSnapshot.toObject(Ticket.class);

                    String movie= ticket.getMovieInfo();
                    String seat= ticket.getSeats();
                    String price=ticket.getPrice();

                    data+=""+movie+"\nSeats:"+ seat+ "\nTotal Payment: RM"+price+"\n\n";
                }
                viewTicket.setText(data);
            }
        });

    }
}
