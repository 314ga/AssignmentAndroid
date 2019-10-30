package com.example.designernote.storageDB.interfaces;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.designernote.storageDB.Customers;
import com.example.designernote.storageDB.Projects;

import java.util.List;
@Dao
public interface ProjectsDao
{
    @Insert
    void insertProject(Projects note);


    @Query("SELECT * FROM Projects ORDER BY project_id desc")
    LiveData<List<Projects>> getAllProjects();


    @Query("SELECT * FROM Projects WHERE project_id =:projectId")
    LiveData<Projects> getProject(int projectId);


    @Update
    void updateProjects(Projects note);


    @Delete
    void deleteProjects(Projects note);
}
