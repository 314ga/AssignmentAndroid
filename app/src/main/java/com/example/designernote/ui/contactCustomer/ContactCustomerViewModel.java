package com.example.designernote.ui.contactCustomer;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ContactCustomerViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public ContactCustomerViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is tools fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}