package com.example.designernote.storageDB.interfaces;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.designernote.storageDB.Customers;

import java.util.List;

@Dao
    public interface CustomerDao {

        @Insert
        void insertCustomer(Customers note);


        @Query("SELECT * FROM Customers ORDER BY f_name desc")
        LiveData<List<Customers>> getAllCustomers();


        @Query("SELECT * FROM Customers WHERE customer_id =:customerId")
        LiveData<Customers> getCustomer(int customerId);


        @Update
        void updateCustomers(Customers note);


        @Delete
        void deleteCustomers(Customers note);
    }
