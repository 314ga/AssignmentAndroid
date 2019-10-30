package com.example.designernote.storageDB.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import com.example.designernote.storageDB.Price;
import com.example.designernote.storageDB.repositories.PriceRepo;

public class PriceViewModel extends AndroidViewModel {

    private PriceRepo priceRepo;

    public PriceViewModel(@NonNull Application application) {
        super(application);
        priceRepo = PriceRepo.getInstance(application);
    }

    public void insert(final Price price) {
        priceRepo.insert(price);
    }
}
