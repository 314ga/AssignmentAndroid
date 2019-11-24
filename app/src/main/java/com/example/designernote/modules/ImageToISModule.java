package com.example.designernote.modules;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class ImageToISModule {

    public String saveToInternalStorage(Bitmap bitmapImage, Context context, String imageName){
        ContextWrapper cw = new ContextWrapper(context);

        // path to /data/data/yourapp/app_data/DesignerNote
        File directory = cw.getDir("DesignerNote", Context.MODE_PRIVATE);
        File mypath=new File(directory,imageName + ".jpg");
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 1, fos);
            return "Image successfully saved";
        } catch (Exception e) {
            e.printStackTrace();
            return "Problem with saving image";
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public Bitmap loadImageFromStorage(String path, Context context)
    {

        try {
            ContextWrapper cw = new ContextWrapper(context);
            File directory = cw.getDir("DesignerNote", Context.MODE_PRIVATE);
            File mypath=new File(directory ,path);
            Bitmap b = BitmapFactory.decodeStream(new FileInputStream(mypath));
            return b;
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
            return null;
        }

    }
    public boolean deleteImageFromStorage(String path, Context context)
    {
            ContextWrapper cw = new ContextWrapper(context);
            File directory = cw.getDir("DesignerNote", Context.MODE_PRIVATE);
            File mypath=new File(directory ,path);
            if(mypath.delete())
                return true;
            else
                return false;
    }
}
