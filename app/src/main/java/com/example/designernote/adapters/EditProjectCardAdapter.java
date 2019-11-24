package com.example.designernote.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProviders;
import androidx.palette.graphics.Palette;
import androidx.recyclerview.widget.RecyclerView;

import com.example.designernote.OnAddImageInterface;
import com.example.designernote.R;
import com.example.designernote.ViewProjectActivity;
import com.example.designernote.models.Tasks;
import com.example.designernote.modules.ImageToISModule;
import com.example.designernote.storageDB.Projects;

import java.util.ArrayList;
import java.util.List;

public class EditProjectCardAdapter extends RecyclerView.Adapter<EditProjectCardAdapter.EditProjectViewHolder>{
    private List<Tasks> tasks;
    Context context;
    private Projects project;
    private OnAddImageInterface onAddImageInterface;
    public EditProjectCardAdapter(List<Tasks> tasks, Context context, Projects project) {
        this.tasks = tasks;
        this.context = context;
        this.project = project;
    }

    public void setOnTaskListener(OnAddImageInterface onAddImageInterface) {
        this.onAddImageInterface = onAddImageInterface;
    }
    public void setTasks(List<Tasks> tasks)
    {
        this.tasks = tasks;
    }
    @Override
    public EditProjectCardAdapter.EditProjectViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflate the layout file
        View projectView = LayoutInflater.from(parent.getContext()).inflate(R.layout.edit_project_card, parent, false);
        EditProjectCardAdapter.EditProjectViewHolder gvh = new EditProjectCardAdapter.EditProjectViewHolder(projectView);
        return gvh;
    }

    @Override
    public void onBindViewHolder(EditProjectCardAdapter.EditProjectViewHolder holder, final int position)
    {
        holder.taskNameTextView.setText(tasks.get(position).getTaskNameText());
        if(!(tasks.get(position).isTaskValue()))
        {
          disableTaskDesign(holder);
        }
        else
        {
            holder.isTaskChosen.setChecked(true);
            if(tasks.get(position).getTaskImages() != null)
                updateImages(holder, position);

        }
        holder.addImageToTask.setOnClickListener(v -> {
            if(onAddImageInterface != null)
            {
                int imagesIn;
                if(project.getImage_path().get(0).equals("noImage"))
                imagesIn = 1;
                else
                    imagesIn = project.getImage_path().size() + 1;

                /////image naming: {ProjectID}_{logo/poster..}_{numberOfImage}.jpg
                String imageName = project.getProject_id() + "_" + tasks.get(position).getTaskName() + "_" + imagesIn;
                onAddImageInterface.onImageSet(imageName,project.getImage_path(),project.getProject_id());
            }
        });

        holder.isTaskChosen.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
    {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
        {
            ArrayList<Boolean> taskList = new ArrayList<>();

            if (isChecked)
            {
                tasks.get(position).setTaskValue(true);
                for (int i = 0; i < tasks.size(); i++) {
                    taskList.add(tasks.get(i).isTaskValue());
                }
                enableTaskDesign(holder);
                if(onAddImageInterface != null)
                {
                    onAddImageInterface.onTaskChange(taskList);
                }
            }
            else
            {
                tasks.get(position).setTaskValue(false);
                for (int i = 0; i < tasks.size(); i++) {
                    taskList.add(tasks.get(i).isTaskValue());
                }
                disableTaskDesign(holder);
                if(onAddImageInterface != null)
                {
                    onAddImageInterface.onTaskChange(taskList);
                }
            }

        }
    });
    }

    private void updateImages(EditProjectCardAdapter.EditProjectViewHolder holder, int position)
    {
        int imageCount = tasks.get(position).getTaskImages().size();
        if(imageCount == 1)
            holder.firstTaskImage.setImageBitmap(tasks.get(position).getTaskImages().get(0));
        else if(imageCount == 2)
        {
            holder.firstTaskImage.setImageBitmap(tasks.get(position).getTaskImages().get(0));
            holder.secondTaskImage.setImageBitmap(tasks.get(position).getTaskImages().get(1));
        }
        else if(imageCount == 3)
        {
            holder.firstTaskImage.setImageBitmap(tasks.get(position).getTaskImages().get(0));
            holder.secondTaskImage.setImageBitmap(tasks.get(position).getTaskImages().get(1));
            holder.thirdTaskImage.setImageBitmap(tasks.get(position).getTaskImages().get(2));
        }
    }
    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public class EditProjectViewHolder extends RecyclerView.ViewHolder {
        CheckBox isTaskChosen;
        View eGradientLayout;
        LinearLayout layoutDisable, cardLayout, layoutHeight;
        TextView taskNameTextView;
        ImageView firstTaskImage,secondTaskImage,thirdTaskImage;
        ImageButton addImageToTask;
        public EditProjectViewHolder(View view) {
            super(view);
            isTaskChosen = view.findViewById(R.id.idTaskChosen);
            layoutDisable = view.findViewById(R.id.layoutDisable);
            layoutHeight = view.findViewById(R.id.layoutHeight);
            cardLayout = view.findViewById(R.id.cardBackground);
            eGradientLayout = view.findViewById(R.id.eGradientLayout);
            firstTaskImage = view.findViewById(R.id.firstTaskImage);
            secondTaskImage = view.findViewById(R.id.secondTaskImage);
            thirdTaskImage = view.findViewById(R.id.thirdTaskImage);
            taskNameTextView = view.findViewById(R.id.taskNameTextView);
            addImageToTask = view.findViewById(R.id.addImageToTask);
        }
    }

    private void enableTaskDesign(EditProjectCardAdapter.EditProjectViewHolder holder)
    {
        holder.addImageToTask.setEnabled(true);

        holder.layoutHeight.getLayoutParams().height = (int) context.getResources().getDimension(R.dimen.imageview_height);
        holder.layoutHeight.getLayoutParams().width = (int) context.getResources().getDimension(R.dimen.imageview_width);
        holder.cardLayout.setBackgroundColor(Color.parseColor("#1f2e37"));
        holder.taskNameTextView.setTextColor(Color.WHITE);
        LinearLayout.LayoutParams paramsText = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        paramsText.weight = 1.0f;
        paramsText.gravity = Gravity.CENTER;
        holder.taskNameTextView.setLayoutParams(paramsText);
        holder.firstTaskImage.setVisibility(View.VISIBLE);
        holder.secondTaskImage.setVisibility(View.VISIBLE);
        holder.thirdTaskImage.setVisibility(View.VISIBLE);
        holder.addImageToTask.setVisibility(View.VISIBLE);
       /* holder.firstTaskImage.setBackgroundResource(R.drawable.ic_crop_original_black_24dp);
        holder.secondTaskImage.setBackgroundResource(R.drawable.ic_crop_original_black_24dp);
        holder.thirdTaskImage.setBackgroundResource(R.drawable.ic_crop_original_black_24dp);*/
    }

    private void disableTaskDesign(EditProjectCardAdapter.EditProjectViewHolder holder)
    {
        holder.addImageToTask.setEnabled(false);
        holder.cardLayout.setBackgroundColor(Color.parseColor("#d6d3c9"));
        holder.taskNameTextView.setTextColor(Color.BLACK);
        ViewGroup.LayoutParams params = holder.layoutHeight.getLayoutParams();
        // Changes the height and width to the specified *pixels*
        params.height = 100;
        params.width = 0;
        holder.layoutHeight.setLayoutParams(params);
        LinearLayout.LayoutParams paramsText = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        paramsText.weight = 4.0f;
        holder.taskNameTextView.setLayoutParams(paramsText);
        holder.firstTaskImage.setVisibility(View.GONE);
        holder.secondTaskImage.setVisibility(View.GONE);
        holder.thirdTaskImage.setVisibility(View.GONE);
        holder.addImageToTask.setVisibility(View.GONE);
    }

}