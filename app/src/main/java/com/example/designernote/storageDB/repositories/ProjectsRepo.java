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
        Projects project = new Projects(0,0,"","",path,false,false,false,false,false,
                false,false,false,false,false,0,0.0);
        project.setProject_id(id);
        new ProjectsRepo.UpdateImagePath(projectsDao).execute(project);
    }

    public void updateProject(int id, double hours, double price,double amountPerHour, boolean projectDone, boolean paidProject,boolean storedOnline)
    {
        Projects project = new Projects(0,price,"","",null,false,false,false,false,false,
                false,false,storedOnline,paidProject,projectDone,hours,amountPerHour);
        project.setProject_id(id);
        new ProjectsRepo.UpdateProject(projectsDao).execute(project);
    }

    public void updateTasks(ArrayList<Boolean> tasks, int id)
    {
        Projects project = new Projects(0,0.0,"","",null,tasks.get(0),
                tasks.get(1),tasks.get(2),tasks.get(3),tasks.get(4),
                tasks.get(5),tasks.get(6),false,false,false,0.0,0.0);
        project.setProject_id(id);
        new ProjectsRepo.UpdateProjectTasks(projectsDao).execute(project);
    }

    public void updateTimePrice(int id, double spentHours, double price)
    {
        Projects project = new Projects(0,price,"","",null,false,
               false,false,false,false,
                false,false,false,false,false,spentHours,0.0);
        project.setProject_id(id);
        new ProjectsRepo.UpdateProjectTimePrice(projectsDao).execute(project);
    }
    public void deleteProjectById(int id)
    {
        Projects project = new Projects(0,0,"","",null,false,
                false,false,false,false,
                false,false,false,false,false,0,0.0);
        project.setProject_id(id);
        new ProjectsRepo.DeleteProjectById(projectsDao).execute(project);
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

    private static class UpdateProject extends AsyncTask<Projects,Void,Void> {
        private ProjectsDao itemDao;
        private UpdateProject(ProjectsDao itemDao) {
            this.itemDao = itemDao;
        }

        @Override
        protected Void doInBackground(Projects... listItem) {
            itemDao.updateProject(listItem[0].getProject_id(), listItem[0].getSpent_hours(),
                    listItem[0].getPrice(),listItem[0].getAmountPerHour(), listItem[0].isDone(),listItem[0].isPaid());
            return null;
        }
    }

    private static class UpdateProjectTasks extends AsyncTask<Projects,Void,Void> {
        private ProjectsDao itemDao;
        private UpdateProjectTasks(ProjectsDao itemDao) {
            this.itemDao = itemDao;
        }

        @Override
        protected Void doInBackground(Projects... listItem) {
            itemDao.updateProjectTasks(listItem[0].getProject_id(), listItem[0].isLogo(),
                    listItem[0].isPoster(),listItem[0].isWebpage(), listItem[0].isPhotoedit(),
                    listItem[0].isMenu_design(),listItem[0].isBussinness_card(),listItem[0].isDiffeerent_task(),listItem[0].isStored_online());
            return null;
        }
    }
    private static class DeleteProjectById extends AsyncTask<Projects,Void,Void> {
        private ProjectsDao itemDao;
        private DeleteProjectById(ProjectsDao itemDao) {
            this.itemDao = itemDao;
        }

        @Override
        protected Void doInBackground(Projects... listItem) {
            itemDao.deleteByProjectId(listItem[0].getProject_id());
            return null;
        }
    }

    private static class UpdateProjectTimePrice extends AsyncTask<Projects,Void,Void> {
        private ProjectsDao itemDao;
        private UpdateProjectTimePrice(ProjectsDao itemDao) {
            this.itemDao = itemDao;
        }

        @Override
        protected Void doInBackground(Projects... listItem) {
            itemDao.updateProjectTimeAndPrice(listItem[0].getProject_id(), listItem[0].getSpent_hours(),
                    listItem[0].getPrice());
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
