package com.example.cinemaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;


import android.content.Intent;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.core.Tag;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.stripe.android.ApiResultCallback;
import com.stripe.android.PaymentIntentResult;
import com.stripe.android.Stripe;
import com.stripe.android.model.ConfirmPaymentIntentParams;
import com.stripe.android.model.PaymentIntent;
import com.stripe.android.model.PaymentMethodCreateParams;
import com.stripe.android.view.CardInputWidget;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Payment extends AppCompatActivity {

    //firebase
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth auth= FirebaseAuth.getInstance();
    String user= Objects.requireNonNull(auth.getCurrentUser()).getUid();
    CollectionReference cReff=db.collection(""+user);
    DocumentReference dReff=db.document(user+"/Ticket");

    //backend server
    private static final String BACKEND_URL = "https://payment-stripe-mobile-server.herokuapp.com/";

    private static final String KEY_INFO1="info1";
    private static final String KEY_INFO2="info2";
    private static final String KEY_INFO3="info3";

    private OkHttpClient httpClient = new OkHttpClient();
    private String paymentIntentClientSecret;
    private Stripe stripe;
    private TextView mAmount, summary, seats;
    private Button payButton, home;





    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        mAmount =findViewById(R.id.amount);
        payButton = findViewById(R.id.payButton);
        summary =findViewById(R.id.summary);
        seats =findViewById(R.id.seat);
        home=findViewById(R.id.home);

        Bundle extras = getIntent().getExtras();
        String amount;
        String seat;
        String ticket;

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Payment.this,Home.class);
                startActivity(intent);


            }


        });


                if (extras != null) {
            amount = extras.getString("TOTALCOST");
            seat = extras.getString("TOTALSEAT");
            ticket= extras.getString("ticket");
            // and get whatever type user account id is
            summary.setText(String.valueOf(ticket));
            seats.setText(String.valueOf(seat));
            mAmount.setText(String.valueOf(amount));
        }


        // Configure the SDK with your Stripe publishable key so it can make requests to Stripe
        stripe = new Stripe(
                getApplicationContext(),
                Objects.requireNonNull("pk_test_TrHwbVCXP855ZUJ2oQeDUscD00jk9TpqSd") //Your publishable key
        );
        //call check out
        startCheckout();
    }



    private void startCheckout() {
        //amount will calculate from .00 make sure multiply by 100
        double amount=Double.parseDouble(mAmount.getText().toString())*100;
        Map<String,Object> payMap=new HashMap<>();
        Map<String,Object> itemMap=new HashMap<>();
        List<Map<String,Object>> itemList =new ArrayList<>();
        payMap.put("currency","usd");
        itemMap.put("id","photo_subscription");
        itemMap.put("amount",amount);
        itemList.add(itemMap);
        payMap.put("items",itemList);
        String json = new Gson().toJson(payMap);
        Log.i("TAG", "startCheckout: "+json);

        // Create a PaymentIntent by calling the server's endpoint.
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");
        /*String json = "{"
                + "\"currency\":\"usd\","
                + "\"items\":["
                + "{\"id\":\"photo_subscription\",\"amount\":"+amount+"}"
                + "]"
                + "}";*/
        RequestBody body = RequestBody.create(json, mediaType);
        Request request = new Request.Builder()
                .url(BACKEND_URL + "create-payment-intent")
                .post(body)
                .build();
        //Connect with stripe and get paymentIntentClientSecret
        httpClient.newCall(request)
                .enqueue(new PayCallback(this));

        // Hook up the pay button to the card widget and stripe instance

        payButton.setOnClickListener((View view) -> {
            CardInputWidget cardInputWidget = findViewById(R.id.cardInputWidget);
            PaymentMethodCreateParams params = cardInputWidget.getPaymentMethodCreateParams();
            if (params != null) {
                ConfirmPaymentIntentParams confirmParams = ConfirmPaymentIntentParams
                        .createWithPaymentMethodCreateParams(params, paymentIntentClientSecret);
                stripe.confirmPayment(this, confirmParams);
            }
        });
    }

    private void displayAlert(@NonNull String title,
                              @Nullable String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message);

        builder.setPositiveButton("Ok", null);
        builder.create().show();
    }
    //Once payment is start, It will call onActivityResult
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Handle the result of stripe.confirmPayment
        stripe.onPaymentResult(requestCode, data, new PaymentResultCallback(this));
    }

    private void onPaymentSuccess(@NonNull final Response response) throws IOException {
        Gson gson = new Gson();

        Type type = new TypeToken<Map<String, String>>(){}.getType();
        Map<String, String> responseMap = gson.fromJson(
                Objects.requireNonNull(response.body()).string(),
                type
        );

        paymentIntentClientSecret = responseMap.get("clientSecret");
    }
    //Ok http call back
    private static final class PayCallback implements Callback {
        @NonNull private final WeakReference<Payment> activityRef;

        PayCallback(@NonNull Payment activity) {
            activityRef = new WeakReference<>(activity);
        }

        @Override
        public void onFailure(@NonNull Call call, @NonNull IOException e) {
            final Payment activity = activityRef.get();
            Log.e("TAG", "onFailure: "+e.getMessage());
            if (activity == null) {
                return;
            }

            activity.runOnUiThread(() -> Toast.makeText(activity, "Error: " + e.toString(), Toast.LENGTH_LONG).show());
        }

        @Override
        public void onResponse(@NonNull Call call, @NonNull final Response response)
                throws IOException {
            final Payment activity = activityRef.get();
            if (activity == null) {
                return;
            }

            if (!response.isSuccessful()) {
                activity.runOnUiThread(() ->
                        Toast.makeText(
                                activity, "Error: " + response.toString(), Toast.LENGTH_LONG
                        ).show()
                );
            } else {
                activity.onPaymentSuccess(response);
            }
        }
    }

    private final class PaymentResultCallback
            implements ApiResultCallback<PaymentIntentResult> {
        @NonNull private final WeakReference<Payment> activityRef;

        PaymentResultCallback(@NonNull Payment activity) {
            activityRef = new WeakReference<>(activity);
        }

        @Override
        public void onSuccess(@NonNull PaymentIntentResult result) {
            final Payment activity = activityRef.get();
            if (activity == null) {
                return;
            }

            PaymentIntent paymentIntent = result.getIntent();
            PaymentIntent.Status status = paymentIntent.getStatus();

            //FirebaseFirestore db = FirebaseFirestore.getInstance();
            //FirebaseAuth auth=FirebaseAuth.getInstance();


            if (status == PaymentIntent.Status.Succeeded) {
                // Payment completed successfully
               // Gson gson = new GsonBuilder().setPrettyPrinting().create();
                //If It is successful You will get detail in log and UI
                //Log.i("TAG", "onSuccess:Payment "+gson.toJson(paymentIntent));
                sendTicket();
                activity.displayAlert(
                        "Payment completed","Check your ticket in Home Menu");

                 //seeTicket();
                       // gson.toJson(paymentIntent)
                //);
                //Toast.makeText(Payment.this,"Payment Successfull", Toast.LENGTH_SHORT).show();
                //Toast.makeText(SelectSeat.this, "You Selected Seat Number :" + (finalI + 1), Toast.LENGTH_SHORT).show();
           //final uid=auth.getCurrentUser();

            } else if (status == PaymentIntent.Status.RequiresPaymentMethod) {
                // Payment failed – allow retrying using a different payment method
                activity.displayAlert(
                        "Payment failed",
                        Objects.requireNonNull(paymentIntent.getLastPaymentError()).getMessage()
                );
            }
        }



        @Override
        public void onError(@NonNull Exception e) {
            final Payment activity = activityRef.get();
            if (activity == null) {
                return;
            }

            // Payment request failed – allow retrying using the same payment method
            activity.displayAlert("Error", e.toString());
        }
    }



    //Upload ticket to firebase
    private void sendTicket() {
        //Toast.makeText(Payment.this, "Empty", Toast.LENGTH_LONG).show();
        String info =summary.getText().toString();
        String info2 =seats.getText().toString();
        String info3=mAmount.getText().toString();
      //  String user= Objects.requireNonNull(auth.getCurrentUser()).getUid();


        Ticket ticket= new Ticket(info,info2,info3);

         //dReff.add(ticket);
        cReff.add(ticket);

       home.setVisibility(View.VISIBLE);


       /* Map<String, Object> note=new HashMap<>();
        note.put(KEY_INFO1, info);
        note.put(KEY_INFO2, info2);
        note.put(KEY_INFO3, info3);*/

        /*db.collection(""+ user).document("Ticket").add(ticket)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(Payment.this,"Saved", Toast.LENGTH_SHORT).show();


                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Payment.this,"Error!", Toast.LENGTH_SHORT).show();
                Log.d("tag", e.toString());

            }
        });*/

    }



}
