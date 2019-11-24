package com.example.designernote.storageDB.interfaces;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.designernote.storageDB.Projects;

import java.util.ArrayList;
import java.util.List;
@Dao
public interface ProjectsDao
{
    @Insert
    void insertProject(Projects note);


    @Query("SELECT * FROM Projects ORDER BY project_id desc")
    LiveData<List<Projects>> getAllProjects();

    @Query("UPDATE Projects SET image_path = :imagePaths WHERE project_id = :id")
    int updateImage(ArrayList<String> imagePaths, int id);

    @Query("UPDATE Projects SET spent_hours = :hours, price = :price, amountPerHour = :amountPerHour, done = :projectDone, paid = :paidProject WHERE project_id = :id")
    int updateProject(int id, double hours, double price,double amountPerHour, boolean projectDone, boolean paidProject);

    @Query("UPDATE Projects SET logo = :logo, poster = :poster, webpage = :webPage, photoedit = :photoedit, menu_design = :menu, bussinness_card = :bussinnessCard, diffeerent_task = :diffeerentTask,stored_online = :storedOnline  WHERE project_id = :id")
    int updateProjectTasks(int id, boolean logo, boolean poster,boolean webPage, boolean photoedit, boolean menu, boolean bussinnessCard, boolean diffeerentTask, boolean storedOnline);

    @Query("UPDATE Projects SET spent_hours = :spentHours, price = :price WHERE project_id = :id")
    int updateProjectTimeAndPrice(int id, double spentHours, double price);

    @Query("DELETE FROM Projects WHERE project_id = :id")
    void deleteByProjectId(int id);

    @Update
    void updateProjects(Projects note);


    @Delete
    void deleteProjects(Projects note);
}
