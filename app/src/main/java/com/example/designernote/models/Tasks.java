package com.example.designernote.models;

import android.graphics.Bitmap;

import java.util.ArrayList;

public class Tasks
{
    private String taskName;
    private boolean taskValue;
    private ArrayList<Bitmap> taskImages;
    String taskNameText;
    public Tasks(String taskName, boolean taskValue, ArrayList<Bitmap> taskImages,String taskNameText) {
        this.taskName = taskName;
        this.taskValue = taskValue;
        this.taskImages = taskImages;
        this.taskNameText = taskNameText;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public boolean isTaskValue() {
        return taskValue;
    }

    public void setTaskValue(boolean taskValue) {
        this.taskValue = taskValue;
    }

    public ArrayList<Bitmap> getTaskImages() {
        return taskImages;
    }

    public void setTaskImages(ArrayList<Bitmap> taskImages) {
        this.taskImages = taskImages;
    }

    public String getTaskNameText() {
        return taskNameText;
    }

    public void setTaskNameText(String taskNameText) {
        this.taskNameText = taskNameText;
    }
}
