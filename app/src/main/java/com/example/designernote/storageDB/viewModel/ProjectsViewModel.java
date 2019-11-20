package com.example.designernote.storageDB.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.designernote.storageDB.Projects;
import com.example.designernote.storageDB.repositories.ProjectsRepo;

import java.util.ArrayList;
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

    public void updateImagePath(ArrayList<String> path, int id)
    {
        projectsRepo.changeImagePath(path,id);
    }

    public void updateProject(int id, double hours, double price,double amountPerHour, boolean projectDone, boolean paidProject, boolean storedOnline)
    {
        projectsRepo.updateProject(id, hours, price,amountPerHour,projectDone,paidProject, storedOnline);
    }

     public void updateTasks(int id,ArrayList<Boolean> tasks)
     {
         projectsRepo.updateTasks(tasks,id);
     }
}

