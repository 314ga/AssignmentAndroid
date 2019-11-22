package com.example.designernote.ui.imageInspiration;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ImageInspirationViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public ImageInspirationViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is share fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}