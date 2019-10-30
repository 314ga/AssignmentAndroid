package com.example.designernote.storageDB.repositories;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;


import com.example.designernote.storageDB.ProjectDatabase;
import com.example.designernote.storageDB.Projects;
import com.example.designernote.storageDB.interfaces.ProjectsDao;

import java.util.List;

public class ProjectsRepo
{

    private ProjectsDao projectsDao;
    private static ProjectsRepo instance;
    private LiveData<List<Projects>> allProjects;
    ///creating variables needed later

    private ProjectsRepo(Application application) {
        ProjectDatabase database = ProjectDatabase.getInstance(application);
        projectsDao = database.projectsDao();
        allProjects = projectsDao.getAllProjects();
    }

    public static synchronized ProjectsRepo getInstance(Application application)
    {
        if(instance == null)
            instance = new ProjectsRepo(application);

        return instance;
    }

    public LiveData<List<Projects>> getAllItems(){
        return allProjects;
    }

    public void insert(Projects item) {
        new ProjectsRepo.InsertNoteAsync(projectsDao).execute(item);
    }


    public void deleteItem(){
        new ProjectsRepo.DeleteItemAsyncTask(projectsDao).execute();
    }

    private static class InsertNoteAsync extends AsyncTask<Projects,Void,Void> {
        private ProjectsDao itemDao;
        private InsertNoteAsync(ProjectsDao itemDao) {
            this.itemDao = itemDao;
        }

        @Override
        protected Void doInBackground(Projects... listItem) {
            itemDao.insertProject(listItem[0]);
            return null;
        }
    }


    private static class DeleteItemAsyncTask extends AsyncTask<Projects, Void, Void> {

        private ProjectsDao itemDao;
        private DeleteItemAsyncTask(ProjectsDao itemDao) {
            this.itemDao = itemDao;
        }


        @Override
        protected Void doInBackground(Projects... listItem) {
            itemDao.deleteProjects(listItem[0]);
            return null;
        }
    }
}
