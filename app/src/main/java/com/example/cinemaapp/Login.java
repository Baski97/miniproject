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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {
    EditText email,password;
    Button login;
    FirebaseAuth fb;
    TextView signup;
    private FirebaseAuth.AuthStateListener Auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        fb = FirebaseAuth.getInstance();

        email= findViewById(R.id.editText);
        password= findViewById(R.id.editText2);
        login = findViewById(R.id.button2);
        signup = findViewById(R.id.textView);

        Auth = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser mFirebaseUser = fb.getCurrentUser();
                if(mFirebaseUser!=null){
                    Toast.makeText(Login.this, "You are logged in",Toast.LENGTH_SHORT).show();
                    Intent intentsignin=new Intent(Login.this,Home.class);
                    startActivity(intentsignin);
                }
                else{
                    Toast.makeText(Login.this, "Please login",Toast.LENGTH_SHORT).show();
                }
            }
        };


        login.setOnClickListener(new View.OnClickListener() {
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

                    Toast.makeText(Login.this, "Enter Your Email and Password!",Toast.LENGTH_SHORT).show();
                }
                else if (!(useremail.isEmpty() && userpassword.isEmpty())){

                    fb.signInWithEmailAndPassword(useremail,userpassword).addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful()){
                                Toast.makeText(Login.this, "Unable to SignIn",Toast.LENGTH_SHORT).show();
                            }
                            else{
                                Intent intenthome= new Intent(Login.this,Home.class);
                                startActivity(intenthome);
                            }
                        }
                    });
                }
                else {
                    Toast.makeText(Login.this, "Something went Wrong!",Toast.LENGTH_SHORT).show();
                }
            }
            });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentsignup= new Intent(Login.this, MainActivity.class);
                startActivity(intentsignup);
            }
        });
        }

    @Override
    protected void onStart() {
        super.onStart();
        fb.addAuthStateListener(Auth);
    }
}

