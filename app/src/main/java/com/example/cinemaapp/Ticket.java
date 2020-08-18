package com.example.cinemaapp;

public class Ticket {

    private String movieInfo;
    private String seats;
    private String price;

    public Ticket(){}

    public Ticket(String movieInfo,String seats, String price){
        this.movieInfo=movieInfo;
        this.seats=seats;
        this.price=price;
    }

    public String getMovieInfo() {
        return movieInfo;
    }

    public void setMovieInfo(String movieInfo) {
        this.movieInfo = movieInfo;
    }

    public String getSeats() {
        return seats;
    }

    public void setSeats(String seats) {
        this.seats = seats;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
