package com.example.designernote.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.palette.graphics.Palette;
import androidx.recyclerview.widget.RecyclerView;

import com.example.designernote.EditProjectActivity;
import com.example.designernote.R;
import com.example.designernote.ViewProjectActivity;
import com.example.designernote.modules.ImageToISModule;
import com.example.designernote.storageDB.Projects;

import java.util.ArrayList;
import java.util.List;

public class ProjectCardAdapter extends RecyclerView.Adapter<ProjectCardAdapter.ProjectViewHolder>{
    private List<Projects> projects;
    Context context;

    public ProjectCardAdapter(List<Projects> projects, Context context) {
        this.projects = projects;
        this.context = context;
    }

    @Override
    public ProjectViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflate the layout file
        View projecView = LayoutInflater.from(parent.getContext()).inflate(R.layout.project_card, parent, false);
        ProjectViewHolder gvh = new ProjectViewHolder(projecView);
        return gvh;
    }

    @Override
    public void onBindViewHolder(ProjectViewHolder holder, final int position) {

        Bitmap image;
        if (projects.get(position).getImage_path().get(0).equals("noImage"))
        {
            int drawable;
            if(projects.get(position).isBussinness_card())
                drawable = R.mipmap.ic_bussiness_card_foreground;
            else if(projects.get(position).isLogo())
                drawable = R.mipmap.ic_logo_foreground;
            else if(projects.get(position).isMenu_design())
                drawable = R.mipmap.ic_menu_foreground;
            else if(projects.get(position).isPoster())
                drawable = R.mipmap.ic_poster_foreground;
            else
                drawable = R.drawable.ic_launcher_foreground;

            holder.personImage.setImageDrawable(ContextCompat.getDrawable(holder.projectName.getContext(), drawable));
            image = BitmapFactory.decodeResource(context.getResources(), drawable);
        }
         else
        {
            ImageToISModule imageToISModule = new ImageToISModule();
            ArrayList<String> images = projects.get(position).getImage_path();
            image = imageToISModule.loadImageFromStorage(images.get(0),context);
            holder.personImage.setImageBitmap(image);
        }
         if(image != null)
            setDesignColor(holder, getColorsFromImage(image));

        holder.projectName.setText(projects.get(position).getP_name());
        holder.projectPrice.setText("" + projects.get(position).getPrice());
        if(projects.get(position).isDone())
            holder.doneButton.setImageDrawable(ContextCompat.getDrawable(holder.projectName.getContext(), R.drawable.ic_check_black_24dp));

        holder.personImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String productName = projects.get(position).getP_name();
                Toast.makeText(context, productName + " is selected", Toast.LENGTH_SHORT).show();
            }
        });

        holder.viewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Projects project = projects.get(position);
                Intent intent = new Intent(context, ViewProjectActivity.class);
                intent.putExtra("Projects", project);
                context.startActivity(intent);
            }
        });
        holder.editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Projects project = projects.get(position);
                Intent intent = new Intent(context, EditProjectActivity.class);
                intent.putExtra("Projects", project);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return projects.size();
    }

    public class ProjectViewHolder extends RecyclerView.ViewHolder {
        ImageView personImage;
        View gradientLayout;
        LinearLayout cardBackground;
        TextView projectName;
        TextView projectPrice;
        ImageButton editButton,viewButton, deleteButton, doneButton;
        public ProjectViewHolder(View view) {
            super(view);
            personImage =view.findViewById(R.id.idPersonImage);
            projectName =view.findViewById(R.id.idProjectName);
            projectPrice = view.findViewById(R.id.idProjectPrice);
            gradientLayout = view.findViewById(R.id.gradientLayout);
            cardBackground = view.findViewById(R.id.cardBackground);
            editButton = view.findViewById(R.id.editButton);
            viewButton = view.findViewById(R.id.viewButton);
            deleteButton = view.findViewById(R.id.deleteButton);
            doneButton = view.findViewById(R.id.doneButton);
        }
    }


    private void setDesignColor(ProjectViewHolder holder, int[] colors)
    {
        GradientDrawable gradient = new GradientDrawable(
                GradientDrawable.Orientation.LEFT_RIGHT,
                new int[] {colors[1],(colors[1] & 0x00FFFFFF) | 0xF2000000,(colors[1] & 0x00FFFFFF) | 0xCC000000, (colors[1] & 0x00FFFFFF) | 0x40000000});
        gradient.setCornerRadius(0f);
        holder.gradientLayout.setForeground(gradient);
        holder.cardBackground.setBackgroundColor(colors[1]);
        holder.projectName.setTextColor(colors[0]);
        holder.projectPrice.setTextColor(colors[0]);
        PorterDuffColorFilter porterDuffColorFilter = new PorterDuffColorFilter(colors[0],
                PorterDuff.Mode.SRC_ATOP);

        holder.doneButton.setColorFilter(porterDuffColorFilter);
        holder.editButton.setColorFilter(porterDuffColorFilter);
        holder.deleteButton.setColorFilter(porterDuffColorFilter);
        holder.viewButton.setColorFilter(porterDuffColorFilter);
    }


    private int[] getColorsFromImage(Bitmap bitmap)
    {

        Palette p = Palette.from(bitmap).generate();
        int[] colors = new int[2];
        Palette.Swatch vibrantSwatch = p.getDominantSwatch();
        colors[0] = 0xFFFFFFFF;
        colors[1] = 0xFF000000;
        // Check that the Vibrant swatch is available
        if(vibrantSwatch != null) {
            colors[1] = vibrantSwatch.getRgb();
            colors[0] = vibrantSwatch.getBodyTextColor();
        }
        return colors;
    }

}