package com.example.cinemaapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdapterCinema extends RecyclerView.Adapter<AdapterCinema.MyHolder>{
    private List<Movie> movieList;
    Context context;


    public AdapterCinema(List<Movie> movieList, Context context) {
        this.movieList = movieList;
        this.context = context;
    }

    @NonNull
    @Override
    public AdapterCinema.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.row,parent,false);



        return new AdapterCinema.MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterCinema.MyHolder holder, int position) {
        final int userImage=movieList.get(position).getImage();
        final String mName=movieList.get(position).getMovieName();
        final String mDes=movieList.get(position).getDes();


        holder.tx1.setText(mName);
        holder.tx2.setText(mDes);
        holder.image.setImageResource(userImage);

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context,MovieDetails.class);
                intent.putExtra("mName",mName);
                intent.putExtra("mDes",mDes);
                intent.putExtra("mPoster",userImage);
                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {

        ImageView image;
        TextView tx1,tx2;
        LinearLayout layout;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            image=itemView.findViewById(R.id.image);
            tx1=itemView.findViewById(R.id.text1);
            tx2=itemView.findViewById(R.id.text2);
            layout=itemView.findViewById(R.id.layout);
        }
    }
}
