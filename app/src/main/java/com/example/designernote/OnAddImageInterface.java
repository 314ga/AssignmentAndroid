package com.example.designernote;

import android.graphics.Bitmap;
import android.view.View;

import java.util.ArrayList;

public interface OnAddImageInterface {

    public void onImageSet(String imageName, ArrayList<String> imagePaths, int id);
}
