package com.example.designernote.storageDB;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.designernote.storageDB.converters.Converters;
import com.example.designernote.storageDB.interfaces.CustomerDao;
import com.example.designernote.storageDB.interfaces.ProjectsDao;

@Database(entities = {Customers.class, Projects.class}, version = 1, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class ProjectDatabase extends RoomDatabase {

    private static ProjectDatabase instance;

    public static synchronized ProjectDatabase getInstance(Context context)
    {
        if (instance == null) {
            synchronized (ProjectDatabase.class) {
                if (instance == null) {
                    instance = Room.databaseBuilder(context.getApplicationContext(),
                                    ProjectDatabase.class,
                                    "project_database").build();
                }
            }
        }
        return instance;
    }
    public abstract CustomerDao customerDao();
    public abstract ProjectsDao projectsDao();
}