package com.example.designernote.ui.createCustomer;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.designernote.R;

public class CreateCustomerFragment extends Fragment {

    private CreateCustomerViewModel createCustomerViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        createCustomerViewModel =
                ViewModelProviders.of(this).get(CreateCustomerViewModel.class);
        View root = inflater.inflate(R.layout.fragment_create_customer, container, false);
        final TextView textView = root.findViewById(R.id.text_slideshow);
        createCustomerViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}