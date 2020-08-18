package com.example.cinemaapp;

public class PaymentDetail {

    private String totalSeats, totalPrice;

    public PaymentDetail(){};

    public PaymentDetail(String totalSeats, String totalPrice){
        this.totalSeats=totalSeats;
        this.totalPrice=totalPrice;
    }


    public String getTotalSeats() {
        return totalSeats;
    }

    public void setTotalSeats(String totalSeats) {
        this.totalSeats = totalSeats;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }
}
