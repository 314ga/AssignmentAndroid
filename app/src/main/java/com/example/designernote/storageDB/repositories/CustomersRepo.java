package com.example.designernote.storageDB.repositories;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.designernote.storageDB.Customers;
import com.example.designernote.storageDB.ProjectDatabase;
import com.example.designernote.storageDB.interfaces.CustomerDao;

import java.util.List;

public class CustomersRepo {

    private CustomerDao customerDao;
    private static CustomersRepo instance;
    private LiveData<List<Customers>> allCustomers;
    ///creating variables needed later

    private CustomersRepo(Application application) {
        ProjectDatabase database = ProjectDatabase.getInstance(application);
        customerDao = database.customerDao();
        allCustomers = customerDao.getAllCustomers();
    }

    public static synchronized CustomersRepo getInstance(Application application)
    {
        if(instance == null)
            instance = new CustomersRepo(application);

        return instance;
    }

    public LiveData<List<Customers>> getAllItems(){
        return allCustomers;
    }

    public void insert(Customers item) {
        new InsertNoteAsync(customerDao).execute(item);
    }


    public void deleteItem(){
        new DeleteItemAsyncTask(customerDao).execute();
    }

    private static class InsertNoteAsync extends AsyncTask<Customers,Void,Void> {
        private CustomerDao itemDao;
        private InsertNoteAsync(CustomerDao itemDao) {
            this.itemDao = itemDao;
        }

        @Override
        protected Void doInBackground(Customers... listItem) {
            itemDao.insertCustomer(listItem[0]);
            return null;
        }
    }


    private static class DeleteItemAsyncTask extends AsyncTask<Customers, Void, Void> {

        private CustomerDao itemDao;
        private DeleteItemAsyncTask(CustomerDao itemDao) {
            this.itemDao = itemDao;
        }


        @Override
        protected Void doInBackground(Customers... listItem) {
            itemDao.deleteCustomers(listItem[0]);
            return null;
        }
    }

}
