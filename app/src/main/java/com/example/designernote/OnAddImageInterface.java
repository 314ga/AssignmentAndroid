package com.example.designernote;

import android.graphics.Bitmap;
import android.view.View;

import java.util.ArrayList;

public interface OnAddImageInterface {

    void onImageSet(String imageName, ArrayList<String> imagePaths, int id);
    void onTaskChange(ArrayList<Boolean> tasks);
}
