package com.example.designernote.storageDB.interfaces;

import androidx.lifecycle.LiveData;
import androidx.room.Delete;
import androidx.room.Query;
import androidx.room.Update;

import com.example.designernote.storageDB.Price;

public interface PriceDao {

    void insertPrice(Price price);

    @Query("SELECT * FROM Price WHERE price_id =:priceId")
    LiveData<Price> getPrice(int priceId);


    @Update
    void updatePrice(Price note);


    @Delete
    void deletePrice(Price note);
}
