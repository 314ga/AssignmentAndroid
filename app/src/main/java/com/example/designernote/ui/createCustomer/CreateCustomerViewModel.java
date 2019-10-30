package com.example.designernote.ui.createCustomer;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CreateCustomerViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public CreateCustomerViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is slideshow fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}