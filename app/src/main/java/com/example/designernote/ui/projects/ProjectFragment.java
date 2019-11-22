package com.example.designernote.ui.projects;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.designernote.adapters.ProjectCardAdapter;
import com.example.designernote.R;
import com.example.designernote.storageDB.Projects;
import com.example.designernote.storageDB.viewModel.ProjectsViewModel;

import java.util.ArrayList;
import java.util.List;

public class ProjectFragment extends Fragment {

    private ProjectViewModel projectViewModel;
    private RecyclerView mRecyclerView;
    private ProjectCardAdapter mAdapter;
    private List<Projects> projects;
    private ProjectsViewModel pViewModel;
    //TODO:Add observer to the on Create view
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState)
    {
        projectViewModel = ViewModelProviders.of(this).get(ProjectViewModel.class);
        View root = inflater.inflate(R.layout.fragment_project, container, false);
        pViewModel = ViewModelProviders.of(this).get(ProjectsViewModel.class);
        projectViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s)
            {
            }

        });

        mRecyclerView = (RecyclerView) root.findViewById(R.id.idRecyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        pViewModel.getAllProjects().observe(this, new Observer<List<Projects>>() {
            @Override
            public void onChanged(List<Projects> projectsList)
            {
                projects = new ArrayList<>();
                for (int i = 0; i < projectsList.size(); i++)
                {
                    projects.add(projectsList.get(i));

                }
                    //set adapter to recyclerview
                mAdapter = new ProjectCardAdapter(projects,getActivity());
                mRecyclerView.setAdapter(mAdapter);

            }
        });

        return root;
    }
}