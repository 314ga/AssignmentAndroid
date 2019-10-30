package com.example.designernote.storageDB.repositories;

import android.app.Application;
import android.os.AsyncTask;
import com.example.designernote.storageDB.Price;
import com.example.designernote.storageDB.ProjectDatabase;
import com.example.designernote.storageDB.interfaces.PriceDao;

public class PriceRepo
{
    private PriceDao priceDao;
    private static PriceRepo instance;
    ///creating variables needed later

    private PriceRepo(Application application) {
        ProjectDatabase database = ProjectDatabase.getInstance(application);
        priceDao = database.priceDao();
    }

    public static synchronized PriceRepo getInstance(Application application)
    {
        if(instance == null)
            instance = new PriceRepo(application);

        return instance;
    }


    public void insert(Price item) {
        new PriceRepo.InsertNoteAsync(priceDao).execute(item);
    }


    public void deleteItem(){
        new PriceRepo.DeleteItemAsyncTask(priceDao).execute();
    }

    private static class InsertNoteAsync extends AsyncTask<Price,Void,Void> {
        private PriceDao itemDao;
        private InsertNoteAsync(PriceDao itemDao) {
            this.itemDao = itemDao;
        }

        @Override
        protected Void doInBackground(Price... listItem) {
            itemDao.insertPrice(listItem[0]);
            return null;
        }
    }


    private static class DeleteItemAsyncTask extends AsyncTask<Price, Void, Void> {

        private PriceDao itemDao;
        private DeleteItemAsyncTask(PriceDao itemDao) {
            this.itemDao = itemDao;
        }


        @Override
        protected Void doInBackground(Price... listItem) {
            itemDao.deletePrice(listItem[0]);
            return null;
        }
    }
}
