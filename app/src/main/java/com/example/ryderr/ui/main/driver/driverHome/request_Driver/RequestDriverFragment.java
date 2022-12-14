package com.example.ryderr.ui.main.driver.driverHome.request_Driver;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ryderr.R;
import com.example.ryderr.models.Request;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class RequestDriverFragment extends Fragment {

    private RequestDriverViewModel mViewModel;
    RecyclerView recyclerView;
    ArrayList<Request> list;

    public static RequestDriverFragment newInstance() {
        return new RequestDriverFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_request_driver, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(RequestDriverViewModel.class);

        recyclerView = view.findViewById(R.id.request_driver_recycler);
        list = mViewModel.populate();


        Observer<ArrayList<Request>> observer = new Observer<ArrayList<Request>>() {
            @Override
            public void onChanged(ArrayList<Request> requests) {
                list = requests;
                RequestDriverListAdapter adapter = new RequestDriverListAdapter(list, getContext());
                recyclerView.setAdapter(adapter);
            }
        };

        mViewModel.loadDriverRequests().observe(getViewLifecycleOwner(), observer);

        recyclerView.setLayoutManager(
                new LinearLayoutManager(getContext()));
    }

}