package com.example.designernote.ui.createCustomer;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.designernote.modules.FieldChecker;
import com.example.designernote.R;
import com.example.designernote.storageDB.Customers;
import com.example.designernote.storageDB.viewModel.CustomerViewModel;

public class CreateCustomerFragment extends Fragment {

    private CreateCustomerViewModel createCustomerViewModel;
    private CustomerViewModel cViewModel;
    private FieldChecker fieldChecker;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        createCustomerViewModel =
                ViewModelProviders.of(this).get(CreateCustomerViewModel.class);
        View root = inflater.inflate(R.layout.fragment_create_customer, container, false);
        cViewModel = ViewModelProviders.of(this).get(CustomerViewModel.class);
        final EditText firstName = root.findViewById(R.id.firstName);
        final EditText lastName = root.findViewById(R.id.lastName);
        final EditText email = root.findViewById(R.id.emailCreateCustomer);
        final EditText phoneNumber = root.findViewById(R.id.phoneNumber);
        final Button createCustomerBut = root.findViewById(R.id.createCustomer);
        final Switch saveOnlineCustomer = root.findViewById(R.id.saveOnlineCustomer);
        createCustomerViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s)
            {

            }
        });
        fieldChecker = new FieldChecker();
        createCustomerBut.setOnClickListener(v -> {
            Customers customer;
            if(fieldChecker.checkIfFilled(firstName) &&  fieldChecker.checkIfFilled(lastName))
            {
                if(fieldChecker.checkIfEmail(email))
                {
                    if(fieldChecker.checkIfPhone(phoneNumber))
                    {
                        customer = new Customers(firstName.getText().toString(),lastName.getText().toString(),
                                email.getText().toString(), phoneNumber.getText().toString());
                        if(saveOnlineCustomer.isChecked())
                            saveCustomerOnline(customer);
                        saveCustomer(customer);
                        phoneNumber.setText("");
                        email.setText("");
                        saveOnlineCustomer.setChecked(false);
                        firstName.setText("");
                        lastName.setText("");
                    }
                    else
                        Toast.makeText(getContext(), "Wrong phone number format", Toast.LENGTH_SHORT).show();

                }
                else
                    Toast.makeText(getContext(), "Wrong e-mail format", Toast.LENGTH_SHORT).show();
            }
            else
                Toast.makeText(getContext(), "Fill up all fields", Toast.LENGTH_SHORT).show();

        });


        return root;


    }

    private void saveCustomer(Customers customer)
    {
        cViewModel.insert(customer);
    }
    private void saveCustomerOnline(Customers customer)
    {
        //TODO:save customer to firebase DB
    }
}