package com.example.designernote.storageDB.repositories;

import android.app.Application;
import android.os.AsyncTask;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;

import com.example.designernote.models.ParameterPass;
import com.example.designernote.storageDB.Customers;
import com.example.designernote.storageDB.ProjectDatabase;
import com.example.designernote.storageDB.interfaces.CustomerDao;
import com.google.common.base.Functions;
import com.google.common.collect.Lists;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class CustomersRepo {

    private CustomerDao customerDao;
    private ParameterPass taskNames;
    private static CustomersRepo instance;
    private LiveData<List<Customers>> allCustomers;
    ///creating variables needed later

    private CustomersRepo(Application application) {
        ProjectDatabase database = ProjectDatabase.getInstance(application);
        taskNames = new ParameterPass();
        customerDao = database.customerDao();
        allCustomers = customerDao.getAllCustomers();
    }

    public static synchronized CustomersRepo getInstance(Application application)
    {
        if(instance == null)
            instance = new CustomersRepo(application);

        return instance;
    }

    public LiveData<List<Customers>> getAllItems()
    {
        return allCustomers;
    }


    public List<String> getCustomerIdByName(String firstName, String lastName)
    {
        try {
            taskNames.setGetCustomerId();
            String params[] = {firstName, lastName};
            taskNames.setParameters(params);
            return new GetDataFromDatabase(customerDao).execute(taskNames).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
            return null;
        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }
    public LiveData<List<String>> getAllCustomerNames() {
        try {
            taskNames.setGetCustomerNames();
            return new GetLiveDataFromDatabase(customerDao).execute(taskNames).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
            return null;
        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void insert(Customers item) {
        new InsertNoteAsync(customerDao).execute(item);
    }

    public void deleteItem(){
        new DeleteItemAsyncTask(customerDao).execute();
    }

    private static class GetLiveDataFromDatabase extends AsyncTask<ParameterPass,LiveData<List<String>>,LiveData<List<String>>> {
        private CustomerDao itemDao;
        private ParameterPass parameterPass;
        private GetLiveDataFromDatabase(CustomerDao itemDao) {
            this.itemDao = itemDao;
        }

        @Override
        protected LiveData<List<String>> doInBackground(ParameterPass... task)
        {
            parameterPass = task[0];
            switch(parameterPass.getTaskName())
            {
                case GETCUSTOMERNAMES:
                    return itemDao.getCustomerNames();
                default:
                    return null;
            }
        }

    }
    private static class GetDataFromDatabase extends AsyncTask<ParameterPass,List<String>,List<String>> {
        private CustomerDao itemDao;
        private ParameterPass parameterPass;
        private GetDataFromDatabase(CustomerDao itemDao) {
            this.itemDao = itemDao;
        }

        @Override
        protected List<String> doInBackground(ParameterPass... task)
        {
            parameterPass = task[0];
            switch(parameterPass.getTaskName())
            {
                case GETCUSTOMERID:
                {
                    List<Integer> integers = itemDao.getIdFromName(parameterPass.getParameters()[0],parameterPass.getParameters()[1]);
                    List<String> strings = Lists.transform(integers, Functions.toStringFunction());
                    return strings;
                }
                default:
                    return null;
            }
        }

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
