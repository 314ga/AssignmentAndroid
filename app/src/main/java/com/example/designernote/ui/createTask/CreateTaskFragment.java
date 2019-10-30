package com.example.designernote.ui.createTask;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.designernote.R;
import com.example.designernote.models.CreateTask;
import com.example.designernote.storageDB.Customers;
import com.example.designernote.storageDB.viewModel.CustomerViewModel;
import com.example.designernote.storageDB.viewModel.ProjectsViewModel;

public class CreateTaskFragment extends Fragment {

    private CreateTaskViewModel galleryViewModel;
    private ProjectsViewModel pViewModel;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel = ViewModelProviders.of(this).get(CreateTaskViewModel.class);
        View root = inflater.inflate(R.layout.fragment_create_task, container, false);
        final TextView spinnerPeopleTextView= root.findViewById(R.id.personSelection);
        final EditText projectNameTextView= root.findViewById(R.id.projectName);
        final TextView saveOnlineTextView= root.findViewById(R.id.saveOnline);
        final TextView chooseTypeOfWOrkTextView= root.findViewById(R.id.chooseTypeOfWork);
        final Button createTask = root.findViewById(R.id.createTaskBut);
        final CheckBox checkBox1 = root.findViewById(R.id.checkBox1);
        final CheckBox checkBox2 = root.findViewById(R.id.checkBox2);
        final CheckBox checkBox3 = root.findViewById(R.id.checkBox3);
        final CheckBox checkBox4 = root.findViewById(R.id.checkBox4);
        final CheckBox checkBox5= root.findViewById(R.id.checkBox5);
        final CheckBox checkBox6 = root.findViewById(R.id.checkBox6);
        final CheckBox checkBox7 = root.findViewById(R.id.checkBox7);
        galleryViewModel.getText().observe(this, new Observer<CreateTask>() {
            @Override
            public void onChanged(@Nullable CreateTask c)
            {
                spinnerPeopleTextView.setText(c.getSpinnerPeopleText());
                projectNameTextView.setHint(c.getProjectNameText());
                saveOnlineTextView.setText(c.getSaveOnlineText());
                chooseTypeOfWOrkTextView.setText(c.getChooseTypeOfWOrkText());
                createTask.setText(c.getCreateTaskButText());
                checkBox1.setText(c.getCheckBoxes()[0]);
                checkBox2.setText(c.getCheckBoxes()[1]);
                checkBox3.setText(c.getCheckBoxes()[2]);
                checkBox4.setText(c.getCheckBoxes()[3]);
                checkBox5.setText(c.getCheckBoxes()[4]);
                checkBox6.setText(c.getCheckBoxes()[5]);
                checkBox7.setText(c.getCheckBoxes()[6]);

            }
        });
        createTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

            }
        });
        return root;
    }
}