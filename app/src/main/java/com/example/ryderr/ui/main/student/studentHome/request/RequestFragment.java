package com.example.ryderr.ui.main.student.studentHome.request;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ryderr.R;
import com.example.ryderr.models.Request;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class RequestFragment extends Fragment {

    private RequestViewModel mViewModel;
    RecyclerView recyclerView;
    ArrayList<Request> list;


    public static RequestFragment newInstance() {
        return new RequestFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_request, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(RequestViewModel.class);


        recyclerView = getView().findViewById(R.id.request_recycler);
        list = mViewModel.populate();
       // RequestCabListAdapter adapter = new RequestCabListAdapter(list, getContext());

        Observer<ArrayList<Request>> observer = new Observer<ArrayList<Request>>() {
            @Override
            public void onChanged(ArrayList<Request> requests) {
               // list = requests;
                RequestCabListAdapter adapter = new RequestCabListAdapter(requests, getContext());
                recyclerView.setAdapter(adapter);
            }
        };

        mViewModel.loadRequests().observe(getViewLifecycleOwner(), observer);
        recyclerView.setLayoutManager(
                new LinearLayoutManager(getContext()));

        FloatingActionButton addRequestBtn = view.findViewById(R.id.add_request_button);
        addRequestBtn.setOnClickListener(view1 -> {
            NavController navController = Navigation.findNavController(view);
            Log.e("navigate error", navController.getCurrentDestination().getDisplayName() );
            Navigation.findNavController(view).navigate(R.id.action_cabsFrag_to_addRequest);

        });
    }



}