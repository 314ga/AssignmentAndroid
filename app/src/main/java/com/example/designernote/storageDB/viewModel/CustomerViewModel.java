package com.example.designernote.storageDB.viewModel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.designernote.storageDB.Customers;
import com.example.designernote.storageDB.repositories.CustomersRepo;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class CustomerViewModel extends AndroidViewModel {

    private CustomersRepo customerRepo;
    private LiveData<List<Customers>> allCustomers;
    public CustomerViewModel(@NonNull Application application) {
        super(application);
        customerRepo =  CustomersRepo.getInstance(application);
        allCustomers = customerRepo.getAllItems();
    }

    public LiveData<List<Customers>> getAllCustomers()
    {
        return allCustomers;
    }

    public void insert(final Customers customer)
    {
        customerRepo.insert(customer);
    }
    public LiveData<List<String>> getCustomerNames(){
        return customerRepo.getAllCustomerNames();
    }
    public List<String> getCustomerIdByName(String fName, String lName)
    {
        return customerRepo.getCustomerIdByName(fName,lName);
    }

}
