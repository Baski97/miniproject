package com.example.cinemaapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    EditText email,password;
    Button register;
    FirebaseAuth fb;
    TextView signin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fb = FirebaseAuth.getInstance();

        email= findViewById(R.id.editText);
        password= findViewById(R.id.editText2);
        register = findViewById(R.id.button2);
        signin = findViewById(R.id.textView);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String useremail=email.getText().toString();
                String userpassword=email.getText().toString();
                if (useremail.isEmpty()){
                    email.setError("Please enter your Email");
                    email.requestFocus();
                }
                else if(userpassword.isEmpty()){
                    password.setError("Please enter your Password");
                    password.requestFocus();

                }
                else if (useremail.isEmpty() && userpassword.isEmpty()){

                    Toast.makeText(MainActivity.this, "Enter Your Email and Password!",Toast.LENGTH_SHORT).show();
                }
                else if (!(useremail.isEmpty() && userpassword.isEmpty())){

                    fb.createUserWithEmailAndPassword(useremail,userpassword).addOnCompleteListener(MainActivity.this, new OnCompleteListener<com.google.firebase.auth.AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<com.google.firebase.auth.AuthResult> task) {
                            if(!task.isSuccessful()){
                                Toast.makeText(MainActivity.this, "Unable to SignUp",Toast.LENGTH_SHORT).show();
                            }
                            else{
                                startActivity(new Intent(MainActivity.this,Home.class));
                            }

                        }
                    });
                }
                else {
                    Toast.makeText(MainActivity.this, "Something went Wrong!",Toast.LENGTH_SHORT).show();
                }
            }
        });


        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intenthome= new Intent(MainActivity.this,CheckMovies.class);
                startActivity(intenthome);
            }
        });
    }




    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser current= fb.getCurrentUser();
        if(current!=null){
            //user logged in
            Intent intenthome= new Intent(MainActivity.this,Home.class);
            startActivity(intenthome);
        }
        else {
            Intent intentsignin=new Intent(MainActivity.this,Login.class);
            startActivity(intentsignin);
            finish();

        }
    }
}
