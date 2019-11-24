package com.example.designernote.ui.createProject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.designernote.modules.FieldChecker;
import com.example.designernote.R;
import com.example.designernote.models.CreateTask;
import com.example.designernote.storageDB.Projects;
import com.example.designernote.storageDB.viewModel.CustomerViewModel;
import com.example.designernote.storageDB.viewModel.ProjectsViewModel;

import java.util.ArrayList;
import java.util.List;

public class CreateProjectFragment extends Fragment {

    private CreateProjectViewModel galleryViewModel;
    private ProjectsViewModel pViewModel;
    private CustomerViewModel cViewModel;
    private FieldChecker fieldChecker;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel = ViewModelProviders.of(this).get(CreateProjectViewModel.class);
        View root = inflater.inflate(R.layout.fragment_create_project, container, false);
        cViewModel = ViewModelProviders.of(this).get(CustomerViewModel.class);
        pViewModel = ViewModelProviders.of(this).get(ProjectsViewModel.class);
        final Spinner spinner = root.findViewById(R.id.spinnerPeople);
        final TextView spinnerPeopleTextView= root.findViewById(R.id.personSelection);
        final EditText projectNameTextView= root.findViewById(R.id.projectName);
        final EditText amountPerHour = root.findViewById(R.id.amountPerHourEditText);
        final EditText ownTask= root.findViewById(R.id.ownTaskEditText);
        final TextView saveOnlineTextView= root.findViewById(R.id.saveOnline);
        final TextView chooseTypeOfWOrkTextView= root.findViewById(R.id.chooseTypeOfWork);
        final Switch saveOnlineProject = root.findViewById(R.id.saveOnline);
        final Button createTask = root.findViewById(R.id.createTaskBut);
        final CheckBox checkBox1 = root.findViewById(R.id.checkBox1);
        final CheckBox checkBox2 = root.findViewById(R.id.checkBox2);
        final CheckBox checkBox3 = root.findViewById(R.id.checkBox3);
        final CheckBox checkBox4 = root.findViewById(R.id.checkBox4);
        final CheckBox checkBox5= root.findViewById(R.id.checkBox5);
        final CheckBox checkBox6 = root.findViewById(R.id.checkBox6);
        final CheckBox checkBox7 = root.findViewById(R.id.checkBox7);
        fieldChecker = new FieldChecker();
            cViewModel.getCustomerNames().observe(this, new Observer<List<String>>() {
                @Override
                public void onChanged(List<String> customers)
                {
                    ArrayList<String> customerArrayList = new ArrayList<>(customers);
                    setCustomerToSpinner(spinner,customerArrayList);
                }
            });
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
            public void onClick(View v) {
                //TODO: fix if there is name with more spaces(first name would be everything except last one)
                String[] splited = spinner.getSelectedItem().toString().split("\\s+");
                List<String> customer = cViewModel.getCustomerIdByName(splited[0], splited[1]);
                ArrayList<String> fileDirs = new ArrayList<>();
                fileDirs.add("noImage");
                String differentTask = "";
                boolean differentTaskChecker = true;
                if(fieldChecker.checkIfFilled(projectNameTextView) && fieldChecker.checkIfFilled(amountPerHour))
                {
                    if (checkBox7.isChecked())
                    {
                        if(fieldChecker.checkIfFilled(ownTask) && fieldChecker.checkTextLong(ownTask,15))
                            differentTask = ownTask.getText().toString();
                        else
                            differentTaskChecker = false;

                    }
                    if(differentTaskChecker)
                    {
                        Projects project = new Projects(Integer.parseInt(customer.get(0)), 0.0, projectNameTextView.getText().toString(),differentTask,fileDirs, checkBox2.isChecked(),
                                checkBox3.isChecked(), checkBox4.isChecked(), checkBox5.isChecked(), checkBox6.isChecked(), checkBox7.isChecked(), checkBox1.isChecked(), false,
                                false,false, 0.0,Double.parseDouble(amountPerHour.getText().toString()));

                        if (saveOnlineProject.isChecked())
                        {
                            project.setStored_online(true);
                            if(!saveProjectOnline(project))
                            {
                                project.setStored_online(false);
                                Toast.makeText(getActivity(), "Problem with storing project online", Toast.LENGTH_LONG).show();
                            }
                            else
                                Toast.makeText(getActivity(), "Project successfully stored online", Toast.LENGTH_LONG).show();
                        }

                        saveProject(project);

                        projectNameTextView.setText("");
                        amountPerHour.setText("");
                        ownTask.setText("");

                        saveOnlineProject.setChecked(false);
                        checkBox1.setChecked(false);
                        checkBox2.setChecked(false);
                        checkBox3.setChecked(false);
                        checkBox4.setChecked(false);
                        checkBox5.setChecked(false);
                        checkBox6.setChecked(false);
                        checkBox7.setChecked(false);
                    }
                    else
                        Toast.makeText(getActivity(), "Different task name too long or not filled", Toast.LENGTH_LONG).show();

                }
                else
                    Toast.makeText(getActivity(), "Fill up project name and €/h", Toast.LENGTH_LONG).show();
            }
        });
        return root;
    }
    private void saveProject(Projects project)
    {
        pViewModel.insert(project);
    }
    private boolean saveProjectOnline(Projects project)
    {
        //TODO:save project to firebase DB
        return false;
    }

    private void setCustomerToSpinner(Spinner spinner, List<String> customerNames)
    {
        ArrayAdapter<String> adp1 = new ArrayAdapter<String>(getActivity(), R.layout.spinner_item, customerNames);
        adp1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adp1);
    }
}