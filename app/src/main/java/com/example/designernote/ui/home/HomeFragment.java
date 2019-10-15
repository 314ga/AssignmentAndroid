package com.example.designernote.ui.home;

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

import com.example.designernote.Person;
import com.example.designernote.PersonCardAdapter;
import com.example.designernote.R;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private RecyclerView mRecyclerView;
    private PersonCardAdapter mAdapter;
    private List<Person> mProductList;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState)
    {
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        homeViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s)
            {
            }

        });

        mRecyclerView = (RecyclerView) root.findViewById(R.id.idRecyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        //Populate the products
        mProductList = new ArrayList<>();
        mProductList.add(new Person("Aladar",R.drawable.ic_menu_camera,"150€", "400€", "5"));
        mProductList.add(new Person("Pineapple",R.drawable.ic_menu_camera,"250€", "500€", "8"));
        mProductList.add(new Person("Mango",R.drawable.ic_menu_camera,"150€", "200€", "4"));
        mProductList.add(new Person("Johny",R.drawable.ic_menu_camera,"250€", "800", "20"));

        //set adapter to recyclerview
        mAdapter = new PersonCardAdapter(mProductList,getActivity());
        mRecyclerView.setAdapter(mAdapter);

        return root;
    }
}