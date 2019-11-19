package com.example.designernote;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Toast;

import com.example.designernote.adapters.EditProjectCardAdapter;
import com.example.designernote.models.Tasks;
import com.example.designernote.modules.ImageToISModule;
import com.example.designernote.storageDB.Projects;
import com.example.designernote.storageDB.viewModel.ProjectsViewModel;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EditProjectActivity extends AppCompatActivity implements OnAddImageInterface {

    private EditProjectCardAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private Projects project;
    private ImageToISModule imageToISModule;
    private ProjectsViewModel projectsViewModel;
    private String imageName;
    private ArrayList<String> imgPaths;
    private int id;
    private List<Tasks> tasks;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_project);
        Intent intent = getIntent();
        project = (Projects)intent.getSerializableExtra("Projects");
        imageToISModule = new ImageToISModule();
        projectsViewModel = ViewModelProviders.of(this).get(ProjectsViewModel.class);
        mRecyclerView = (RecyclerView) findViewById(R.id.idEditRecyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        tasks = new ArrayList<>();

                addTaskToTasks("logo",project.isLogo(), "Logo");
                addTaskToTasks("poster",project.isPoster(),"Poster");
                addTaskToTasks("web",project.isWebpage(), "Web page");
                addTaskToTasks("photoEdit",project.isPhotoedit(),"Photo edit");
                addTaskToTasks("menu",project.isMenu_design(), "Menu");
                addTaskToTasks("difTask",project.isDiffeerent_task(),"Different task: "+ project.getText_diff_task());
                addTaskToTasks("businessCard",project.isBussinness_card(), "Business card");

        mAdapter = new EditProjectCardAdapter(tasks,getApplicationContext(),project);
        mAdapter.setOnDeleteListener(this);
        mRecyclerView.setAdapter(mAdapter);
    }

    private ArrayList<Bitmap> getImagesByType(String img)
    {
        ArrayList<Bitmap> images = new ArrayList<>();
        ArrayList<String> paths = project.getImage_path();
            for (int i = 0; i < paths.size(); i++)
            {
                if(getImageTypeFromPath(paths.get(i)).equals(img))
                {
                    images.add(getImageFromPath(paths.get(i)));
                }
            }
        return images;
    }
    private void addTaskToTasks(String taskName, boolean taskValue, String taskNameText)
    {
        Tasks task;
        if(taskValue)
        {
            if(project.getImage_path().get(0).equals("noImage"))
                task = new Tasks(taskName,true, null,taskNameText);
            else
                task = new Tasks(taskName,true, getImagesByType(taskName),taskNameText);
        }
        else
            task = new Tasks(taskName,false, null,taskNameText);

        tasks.add(task);

    }
    private String getImageTypeFromPath(String path)
    {
        path = path.substring(path.indexOf("_") + 1);
        path = path.substring(0, path.indexOf("_"));
        return path;
    }
    private Bitmap getImageFromPath(String path)
    {
        return imageToISModule.loadImageFromStorage(path,this);
    }


    @Override
    public void onImageSet(String imageName, ArrayList<String> imagePaths, int id) {
        this.imageName = imageName;
        imgPaths = imagePaths;
        this.id = id;
        openFileChooser();
    }


    private void openFileChooser()
    {
        // start picker to get image for cropping and then use the image in cropping activity
        CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .setAspectRatio(1,1)
                .setRequestedSize(320,320)
                .start(this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE)
        {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK)
            {
                Toast.makeText(this,imageName,Toast.LENGTH_LONG).show();
                /////image naming: {ProjectID}_{logo/poster..}_{numberOfImage}.jpg
                Uri resultUri = result.getUri();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), resultUri);
                    imageToISModule.saveToInternalStorage(bitmap,this,imageName);
                    if(imgPaths.get(0).equals("noImage"))
                        imgPaths.remove(0);
                    imgPaths.add(imageName+".jpg");
                    projectsViewModel.updateImagePath(imgPaths,id);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }

}
