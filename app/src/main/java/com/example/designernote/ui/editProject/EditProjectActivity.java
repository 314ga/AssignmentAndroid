package com.example.designernote.ui.editProject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.designernote.interfaces.OnAddImageInterface;
import com.example.designernote.R;
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
    Switch projectDone, projectPaid, storedOnline;
    EditText hoursValueText,ePriceValueText,amountPerHourValue;
    Button saveChanges;
    TextView eCustomerName,eProjectName;
    private ImageButton backButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_project);
        Intent intent = getIntent();
        project = (Projects)intent.getSerializableExtra("Projects");
        imageToISModule = new ImageToISModule();
        projectsViewModel = ViewModelProviders.of(this).get(ProjectsViewModel.class);
        mRecyclerView = (RecyclerView) findViewById(R.id.idEditRecyclerView);
        saveChanges = findViewById(R.id.saveChangesBtn);
        amountPerHourValue = findViewById(R.id.amountPerHour);
        ePriceValueText = findViewById(R.id.ePriceValueText);
        hoursValueText = findViewById(R.id.hoursValueText);
        projectDone = findViewById(R.id.doneProjectSwitch);
        projectPaid = findViewById(R.id.paidProjectSwitch);
        storedOnline = findViewById(R.id.SwitchStorageState);
        eCustomerName = findViewById(R.id.eCustomerName);
        eProjectName = findViewById(R.id.eProjectName);
        backButton = findViewById(R.id.backButtonEdit);
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
        mAdapter.setOnTaskListener(this);
        mRecyclerView.setAdapter(mAdapter);
        setItemsByData();
        saveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                   projectsViewModel.updateProject(project.getProject_id(),Double.parseDouble(hoursValueText.getText().toString()),
                            Double.parseDouble(ePriceValueText.getText().toString()),Double.parseDouble(amountPerHourValue.getText().toString()),
                            projectDone.isChecked(),projectPaid.isChecked(),storedOnline.isChecked());
                }
            });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
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

    @Override
    public void onTaskChange(ArrayList<Boolean> tasks)
    {
        projectsViewModel.updateTasks(project.getProject_id(),tasks);
        //TODO:Delete Images from storage and update string path in database
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
        super.onActivityResult(requestCode, resultCode, data);
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
                    tasks = new ArrayList<>();

                    addTaskToTasks("logo",project.isLogo(), "Logo");
                    addTaskToTasks("poster",project.isPoster(),"Poster");
                    addTaskToTasks("web",project.isWebpage(), "Web page");
                    addTaskToTasks("photoEdit",project.isPhotoedit(),"Photo edit");
                    addTaskToTasks("menu",project.isMenu_design(), "Menu");
                    addTaskToTasks("difTask",project.isDiffeerent_task(),"Different task: "+ project.getText_diff_task());
                    addTaskToTasks("businessCard",project.isBussinness_card(), "Business card");
                    mAdapter.setTasks(tasks);
                    mAdapter.notifyDataSetChanged();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }

    private void setItemsByData()
    {
        eProjectName.setText(project.getP_name());
        eCustomerName.setText("customer");
        amountPerHourValue.setText(project.getAmountPerHour() + "");
        ePriceValueText.setText(project.getPrice() + "");
        hoursValueText.setText(project.getSpent_hours() + "");
        if(project.isDone())
         projectDone.setChecked(true);
        if(project.isPaid())
         projectPaid.setChecked(true);
        if(project.isStored_online())
            storedOnline.setChecked(true);
    }
}
