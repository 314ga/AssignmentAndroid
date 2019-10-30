package com.example.designernote.storageDB.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.designernote.storageDB.Projects;
import com.example.designernote.storageDB.repositories.ProjectsRepo;

import java.util.List;

public class ProjectsViewModel extends AndroidViewModel {

    private ProjectsRepo projectsRepo;

    public ProjectsViewModel(@NonNull Application application) {
        super(application);
        projectsRepo =  ProjectsRepo.getInstance(application);
    }

    public LiveData<List<Projects>> getAllProjects()
    {
        return projectsRepo.getAllItems();
    }

    public void insert(final Projects projects)
    {
        projectsRepo.insert(projects);
    }
}

