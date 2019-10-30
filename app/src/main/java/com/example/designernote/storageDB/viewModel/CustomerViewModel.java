package com.example.designernote.storageDB.viewModel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.designernote.storageDB.Customers;
import com.example.designernote.storageDB.repositories.CustomersRepo;

import java.util.List;

public class CustomerViewModel extends AndroidViewModel {

    private CustomersRepo customerRepo;

    public CustomerViewModel(@NonNull Application application) {
        super(application);
        customerRepo =  CustomersRepo.getInstance(application);
    }

    public LiveData<List<Customers>> getAllCustomers()
    {
        return customerRepo.getAllItems();
    }

    public void insert(final Customers customer)
    {
        customerRepo.insert(customer);
    }
}
