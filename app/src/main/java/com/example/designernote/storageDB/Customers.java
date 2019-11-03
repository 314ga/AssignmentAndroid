package com.example.designernote.storageDB;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(indices = {@Index("customer_id")})
public class Customers
{
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "customer_id")
    private int customer_id;
    private String f_name;
    private String l_name;
    private String email;
    private String phone_number;

    public Customers(String f_name, String l_name, String email, String phone_number) {
        this.customer_id = customer_id;
        this.f_name = f_name;
        this.l_name = l_name;
        this.email = email;
        this.phone_number = phone_number;
    }

    public String getF_name() {
        return f_name;
    }

    public String getL_name() {
        return l_name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setF_name(String f_name) {
        this.f_name = f_name;
    }

    public void setL_name(String l_name) {
        this.l_name = l_name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public int getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }
}
