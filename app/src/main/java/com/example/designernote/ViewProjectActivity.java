package com.example.designernote;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.designernote.adapters.ProjectCardAdapter;
import com.example.designernote.adapters.ProjectPagerAdapter;
import com.example.designernote.storageDB.Projects;
import com.example.designernote.storageDB.viewModel.ProjectsViewModel;

import java.util.ArrayList;
import java.util.List;

public class ViewProjectActivity extends AppCompatActivity {

    private ViewPager projectViewPager;
    Projects project;
    ProjectPagerAdapter projectPagerAdapter;
    private TextView projectName, customerName, tasksText, priceText, hoursText;
    private ImageView savedOnline, paid, finsihed;
    private ImageButton backButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_project);
        Intent intent = getIntent();
        project = (Projects)intent.getSerializableExtra("Projects");

        boolean[] tasks = {project.isLogo(),project.isPoster(),project.isPhotoedit(),project.isBussinness_card(),
                project.isMenu_design(),project.isDiffeerent_task(),project.isWebpage()};

        projectPagerAdapter = new ProjectPagerAdapter(this,project.getImage_path(),tasks);
        projectViewPager = findViewById(R.id.viewpager);
        projectViewPager.setPadding(0, 0, 650, 0);
        projectViewPager.setAdapter(projectPagerAdapter);
        projectName = findViewById(R.id.projectNameTextView);
        customerName = findViewById(R.id.customerNameTextView);
        tasksText = findViewById(R.id.projectIncludesItems);
        priceText = findViewById(R.id.priceValueText);
        hoursText = findViewById(R.id.hoursValueText);
        savedOnline = findViewById(R.id.imageStorageState);
        paid = findViewById(R.id.paidValueImage);
        finsihed = findViewById(R.id.doneValueImage);
        backButton = findViewById(R.id.backButton);
        projectName.setText(project.getP_name());
        //customerName.setText(project.getCustomer_id());
        tasksText.setText(getTaskString(tasks));
        priceText.setText(String.valueOf(project.getPrice()));
        hoursText.setText(String.valueOf(project.getSpent_hours()));

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    private String getTaskString(boolean[] tasks)
    {
        String taskString = "";

        for (int i = 0; i < tasks.length; i++)
        {
            if(tasks[i])
            {
                switch(i) {
                    case 0:
                        taskString += "Logo";
                        break;
                    case 1:
                        taskString += "Poster";
                        break;
                    case 2:
                        taskString += "Photo edit";

                        break;
                    case 3:
                        taskString += "Business card";

                        break;
                    case 4:
                        taskString += "Menu design";

                        break;
                    case 5:
                        taskString += project.getText_diff_task();

                        break;
                    case 6:
                        taskString += "Web page";

                        break;
                    default:
                        break;
                }
                taskString += " | ";
            }
        }
        taskString = taskString.substring(0, taskString.length() - 3);
        return taskString;
    }
}
