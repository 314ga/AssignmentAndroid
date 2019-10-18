package com.example.designernote.ui.createTask;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.designernote.R;

public class CreateTaskFragment extends Fragment {

    private CreateTaskViewModel galleryViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel = ViewModelProviders.of(this).get(CreateTaskViewModel.class);
        View root = inflater.inflate(R.layout.fragment_create_task, container, false);
        final TextView spinnerPeopleTextView= root.findViewById(R.id.personSelection);
        final EditText projectNameTextView= root.findViewById(R.id.projectName);
        final TextView saveOnlineTextView= root.findViewById(R.id.saveOnline);
        final TextView chooseTypeOfWOrkTextView= root.findViewById(R.id.chooseTypeOfWork);
        galleryViewModel.getText().observe(this, new Observer<CreateTask>() {
            @Override
            public void onChanged(@Nullable CreateTask c)
            {
                spinnerPeopleTextView.setText(c.getSpinnerPeopleText());
                projectNameTextView.setHint(c.getProjectNameText());
                saveOnlineTextView.setText(c.getSaveOnlineText());
                chooseTypeOfWOrkTextView.setText(c.getChooseTypeOfWOrkText());

            }
        });
        return root;
    }
}