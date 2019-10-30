package com.example.designernote.storageDB;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Price {

    @PrimaryKey(autoGenerate = true)
    private int price_id;
    private double price;

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
