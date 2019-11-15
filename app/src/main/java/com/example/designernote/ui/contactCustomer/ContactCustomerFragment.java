package com.example.designernote.ui.contactCustomer;

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

public class ContactCustomerFragment extends Fragment {

    private ContactCustomerViewModel contactCustomerViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        contactCustomerViewModel =
                ViewModelProviders.of(this).get(ContactCustomerViewModel.class);
        View root = inflater.inflate(R.layout.fragment_contact_customer, container, false);
        final TextView textView = root.findViewById(R.id.text_tools);
        contactCustomerViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}