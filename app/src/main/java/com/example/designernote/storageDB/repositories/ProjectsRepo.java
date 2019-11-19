package com.example.designernote.storageDB.repositories;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;


import com.example.designernote.models.ParameterPass;
import com.example.designernote.storageDB.ProjectDatabase;
import com.example.designernote.storageDB.Projects;
import com.example.designernote.storageDB.interfaces.ProjectsDao;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class ProjectsRepo
{

    private ProjectsDao projectsDao;
    private static ProjectsRepo instance;
    private ParameterPass taskNames;
    private LiveData<List<Projects>> allProjects;
    ///creating variables needed later

    private ProjectsRepo(Application application) {
        ProjectDatabase database = ProjectDatabase.getInstance(application);
        taskNames = new ParameterPass();
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

    public void changeImagePath(ArrayList<String> path, int id)
    {
        Log.v("FuckingFucker", id + "asfffffffffffffffffff");
        Projects project = new Projects(0,0,"","",path,false,false,false,false,false,
                false,false,false,false,false,0);
        project.setProject_id(id);
        new ProjectsRepo.UpdateImagePath(projectsDao).execute(project);
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

    private static class UpdateImagePath extends AsyncTask<Projects,Void,Void> {
        private ProjectsDao itemDao;
        private UpdateImagePath(ProjectsDao itemDao) {
            this.itemDao = itemDao;
        }

        @Override
        protected Void doInBackground(Projects... listItem) {
            itemDao.updateImage(listItem[0].getImage_path(),listItem[0].getProject_id());
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
