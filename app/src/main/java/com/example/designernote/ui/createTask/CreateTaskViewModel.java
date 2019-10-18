package com.example.designernote.ui.createTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CreateTaskViewModel extends ViewModel {

    private MutableLiveData<CreateTask> createTask;
    public CreateTaskViewModel() {
        createTask = new MutableLiveData<>();
        CreateTask createTaskObj = new CreateTask("Choose customer:","Project name","Save online","Type of the work:");
        createTask.setValue(createTaskObj);
    }

    public LiveData<CreateTask> getText() {
        return createTask;
    }
}