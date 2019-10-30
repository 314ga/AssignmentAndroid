package com.example.designernote.ui.createTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.designernote.models.CreateTask;

public class CreateTaskViewModel extends ViewModel {

    private MutableLiveData<CreateTask> createTask;
    public CreateTaskViewModel() {
        createTask = new MutableLiveData<>();
        String[] checkBoxes = {"Business card", "Logo", "Poster", "Web page", "Photo edit", "Menu design", "Different task"};
        CreateTask createTaskObj = new CreateTask("Choose customer:","Project name","Save online","Type of the work:", "Create task", checkBoxes);
        createTask.setValue(createTaskObj);
    }

    public LiveData<CreateTask> getText() {
        return createTask;
    }
}