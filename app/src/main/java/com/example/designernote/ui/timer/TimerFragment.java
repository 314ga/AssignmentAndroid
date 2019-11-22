package com.example.designernote.ui.timer;
/*CODE for TIMER FROM*/
/*https://codinginflow.com/tutorials/android/countdowntimer/part-3-run-in-background*/
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.designernote.R;
import com.example.designernote.storageDB.Projects;
import com.example.designernote.storageDB.viewModel.CustomerViewModel;
import com.example.designernote.storageDB.viewModel.ProjectsViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static android.content.Context.MODE_PRIVATE;

public class TimerFragment extends Fragment {

    private TimerViewModel timerViewModel;
    private static final long START_TIME_IN_MILLIS = 28800000;

    private TextView mTextViewCountDown;
    private Button mButtonStartPause;
    private Button mButtonReset, buttonSaveTime;
    private ProjectsViewModel projectsViewModel;
    private CountDownTimer mCountDownTimer;
    private List<Projects> projectsList;
    private boolean mTimerRunning;
    private Spinner projectSpinner;
    private long mTimeLeftInMillis;
    private long mEndTime;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        timerViewModel =
                ViewModelProviders.of(this).get(TimerViewModel.class);
        View root = inflater.inflate(R.layout.fragment_timer, container, false);
        timerViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s){
            }
        });
        projectSpinner = root.findViewById(R.id.spinnerProjects);
        projectsViewModel = ViewModelProviders.of(this).get(ProjectsViewModel.class);
        projectsViewModel.getAllProjects().observe(this, new Observer<List<Projects>>() {
            @Override
            public void onChanged(List<Projects> projects)
            {
                projectsList = projects;
                ArrayList<String> projectsArrayList = new ArrayList<>();
                for (int i = 0; i < projects.size(); i++) {
                    projectsArrayList.add(projects.get(i).getP_name());
                }
                setProjectsToSpinner(projectSpinner,projectsArrayList);
            }
        });
        mTextViewCountDown = root.findViewById(R.id.text_view_countdown);
        buttonSaveTime = root.findViewById(R.id.buttonSaveTime);
        mButtonStartPause = root.findViewById(R.id.button_start_pause);
        mButtonReset = root.findViewById(R.id.button_reset);
        projectsViewModel = ViewModelProviders.of(this).get(ProjectsViewModel.class);
        mButtonStartPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mTimerRunning) {
                    pauseTimer();
                } else {
                    startTimer();
                }
            }
        });
        projectSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
               mButtonStartPause.setEnabled(true);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                mButtonStartPause.setEnabled(false);
            }

        });

        mButtonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetTimer();
            }
        });
        buttonSaveTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveTime();
            }
        });

        return root;
    }


    private void saveTime()
    {
        int minutes = (int) (mTimeLeftInMillis / 1000) / 60;
        int hours = minutes/ 60;
        minutes = minutes - (hours*60);
        int id = 0;
        double totalHours = hours + (double)minutes/100;
        double price = 0;
        for (int i = 0; i < projectsList.size(); i++)
        {
            projectsList.get(i);
            if(projectsList.get(i).getP_name().equals(projectSpinner.getSelectedItem().toString()))
            {
                id = projectsList.get(i).getProject_id();
                totalHours = totalHours + projectsList.get(i).getSpent_hours();
                price = projectsList.get(i).getPrice() + totalHours*projectsList.get(i).getAmountPerHour();
                break;
            }
        }
        projectsViewModel.updateTimePrice(id,totalHours, price);
    }

    private void setProjectsToSpinner(Spinner spinner, List<String> customerNames)
    {
        ArrayAdapter<String> adp1 = new ArrayAdapter<String>(getActivity(), R.layout.spinner_item, customerNames);
        adp1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adp1);
    }

    /*TIMER*/
    private void startTimer() {
        mEndTime = System.currentTimeMillis() + mTimeLeftInMillis;

        mCountDownTimer = new CountDownTimer(mTimeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis = millisUntilFinished;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                mTimerRunning = false;
                updateButtons();
            }
        }.start();

        mTimerRunning = true;
        updateButtons();
    }
    private void pauseTimer() {
        mCountDownTimer.cancel();
        mTimerRunning = false;
        updateButtons();
    }
    private void resetTimer() {
        mTimeLeftInMillis = START_TIME_IN_MILLIS;
        SharedPreferences preferences = this.getActivity().getSharedPreferences("prefs", 0);
        preferences.edit().remove("millisLeft").commit();
        preferences.edit().remove("timerRunning").commit();
        preferences.edit().remove("endTime").commit();
        updateCountDownText();
        updateButtons();
    }
    private void updateCountDownText() {
        int minutes = (int) (mTimeLeftInMillis / 1000) / 60;
        int seconds = (int) (mTimeLeftInMillis / 1000) % 60;
        int hours = minutes/ 60;
        minutes = minutes - (hours*60);
        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d:%02d",hours, minutes, seconds);

        mTextViewCountDown.setText(timeLeftFormatted);
    }
    private void updateButtons() {
        if (mTimerRunning) {
            mButtonReset.setEnabled(false);
            buttonSaveTime.setEnabled(false);
            mButtonStartPause.setText("Pause");
        } else {
            mButtonStartPause.setText("Start");

            if (mTimeLeftInMillis != START_TIME_IN_MILLIS) {
                mButtonReset.setEnabled(true);
                buttonSaveTime.setEnabled(true);
            } else {
                mButtonReset.setEnabled(false);
                buttonSaveTime.setEnabled(false);

            }
        }
    }
    @Override
    public void onStop() {
        super.onStop();

        SharedPreferences prefs = this.getActivity().getSharedPreferences("prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        editor.putLong("millisLeft", mTimeLeftInMillis);
        editor.putBoolean("timerRunning", mTimerRunning);
        editor.putLong("endTime", mEndTime);

        editor.apply();

        if (mCountDownTimer != null) {
            mCountDownTimer.cancel();
        }
    }
    @Override
    public void onStart() {
        super.onStart();

        SharedPreferences prefs = this.getActivity().getSharedPreferences("prefs", MODE_PRIVATE);

        mTimeLeftInMillis = prefs.getLong("millisLeft", START_TIME_IN_MILLIS);
        mTimerRunning = prefs.getBoolean("timerRunning", false);

        updateCountDownText();
        updateButtons();

        if (mTimerRunning) {
            mEndTime = prefs.getLong("endTime", 0);
            mTimeLeftInMillis = mEndTime - System.currentTimeMillis();

            if (mTimeLeftInMillis < 0) {
                mTimeLeftInMillis = 0;
                mTimerRunning = false;
                updateCountDownText();
                updateButtons();
            } else {
                startTimer();
            }
        }
    }

}