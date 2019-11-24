package com.example.designernote.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.viewpager.widget.PagerAdapter;

import com.example.designernote.R;
import com.example.designernote.modules.ImageToISModule;

import java.util.ArrayList;

public class ProjectPagerAdapter extends PagerAdapter {

    private Context context;
    private ArrayList<String> imagePaths;
    private boolean[] tasks;
    private boolean hasSomeImages;
    private int taskTrueCount;

    private ImageToISModule imageToISModule;
    public ProjectPagerAdapter(Context context, ArrayList<String> imagePaths, boolean[] tasks) {
        this.context = context;
        this.imagePaths = imagePaths;
        this.tasks = tasks;
        hasSomeImages = checkIfSomeImage();
        taskTrueCount = setTaskTrueCount();
        imageToISModule = new ImageToISModule();
    }

    /*
    This callback is responsible for creating a page. We inflate the layout and set the drawable
    to the ImageView based on the position. In the end we add the inflated layout to the parent
    container .This method returns an object key to identify the page view, but in this example page view
    itself acts as the object key
    */
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.pager_item, null);
        ImageView imageView = view.findViewById(R.id.image);
        imageView.setImageBitmap(getImageAt(position));
        container.addView(view);
        return view;
    }
    /*
    This callback is responsible for destroying a page. Since we are using view only as the
    object key we just directly remove the view from parent container
    */
    @Override
    public void destroyItem(ViewGroup container, int position, Object view) {
        container.removeView((View) view);
    }
   /* @Override
    public float getPageWidth(int position) {
        return 0.4f;
    }*/
    /*
    Returns the count of the total pages
    */
    @Override
    public int getCount() {
        return taskTrueCount;
    }
    /*
    Used to determine whether the page view is associated with object key returned by instantiateItem.
    Since here view only is the key we return view==object
    */
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return object == view;
    }
    private Bitmap getImageAt(int position)
    {
        //TODO:IMAGE NAMING
        /////image naming: {ProjectID}_{logo/poster..}_{numberOfImage}.jpg
        Bitmap image = BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher);
        if(position<imagePaths.size() && hasSomeImages)
        {
            image = getImageFromPath(imagePaths.get(position));
            setImageLoadedFor(getImageTypeFromPath(imagePaths.get(position)));
        }
        else
        {
            for (int i = 0; i < tasks.length ; i++)
            {
                if(tasks[i])
                {
                    image = getDefaultImage(i);
                    tasks[i] = false;
                    break;
                }
            }
        }
        return image;

    }

    private int setTaskTrueCount()
    {
         int countTaskTrueCount = 0;
        for (int i = 0; i < tasks.length ; i++) {
            if(tasks[i])
                countTaskTrueCount++;
        }
        if(hasSomeImages)
        countTaskTrueCount = countTaskTrueCount - getCountOfDifferentTaskImg() + imagePaths.size();

        return  countTaskTrueCount;
    }

    private String getImageTypeFromPath(String path)
    {
        path = path.substring(path.indexOf("_") + 1);
        path = path.substring(0, path.indexOf("_"));
        return path;
    }

    private Bitmap getImageFromPath(String path)
    {
       return imageToISModule.loadImageFromStorage(path,context);
    }

    private Bitmap getDefaultImage(int imageNumber)
    {
        Bitmap imageDefault;
        switch(imageNumber)
        {
            case 0:
                imageDefault = BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_logo_no_image_foreground);

                break;
            case 1:
                imageDefault = BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_poster_foreground);

                break;
            case 2:
                imageDefault = BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_photo_edit_foreground);

                break;
            case 3:
                imageDefault = BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_business_card_foreground);

                break;
            case 4:
                imageDefault = BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_menu_foreground);

                break;
            case 5:
                imageDefault = BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_different_task_foreground);

                break;
            case 6:
                imageDefault = BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_web_design_foreground);

                break;
            default:
                imageDefault = BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher);
        }
        return imageDefault;
    }

    private void setImageLoadedFor(String stringTask)
    {
        switch(stringTask)
        {

            case "logo":
                tasks[0] = false;
                break;
            case "poster":
                tasks[1] = false;
                break;
            case "photoEdit":
                tasks[2] = false;
                break;
            case "businessCard":
                tasks[3] = false;
                break;
            case "menu":
                tasks[4] = false;
                break;
            case "difTask":
                tasks[5] = false;
                break;
            case "web":
                tasks[6] = false;
                break;
            default:
                break;
        }
    }

    private int getCountOfDifferentTaskImg()
    {
        String tempValue = "";
        int differentImages = 0;
        if(!(imagePaths.get(0).equals("noImage")))
        {
            for (int i = 0; i < imagePaths.size(); i++)
            {
                String imageType = getImageTypeFromPath(imagePaths.get(i));
                if(!(imageType.equals(tempValue)))
                {
                   differentImages++;
                   tempValue = imageType;
                }
            }
        }
            return differentImages;
    }

    private boolean checkIfSomeImage()
    {
        if(imagePaths.get(0).equals("noImage"))
            return false;
        else
            return true;
    }

}