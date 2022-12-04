package com.example.ryderr.ui.main.student.studentHome.live;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ryderr.R;
import com.example.ryderr.models.LiveCab;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class LiveCabsFragment extends Fragment {

    private LiveCabsViewModel mViewModel;
    RecyclerView recyclerView;
    ArrayList<LiveCab> list;

    public static LiveCabsFragment newInstance() {
        return new LiveCabsFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = new LiveCabsViewModel();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {

        mViewModel.loadLiveCabs();
        return inflater.inflate(R.layout.fragment_live_cabs, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(LiveCabsViewModel.class);


        recyclerView = getView().findViewById(R.id.recycler_view);
        list = mViewModel.populate();
     //   LiveCabListAdapter adapter = new LiveCabListAdapter(list, getContext());
        //recyclerView.setAdapter(adapter);

        Observer<ArrayList<LiveCab>> observer = new Observer<ArrayList<LiveCab>>() {
            @Override
            public void onChanged(ArrayList<LiveCab> cabs) {
                list = cabs;
                LiveCabListAdapter adapter = new LiveCabListAdapter(list, getContext());
                recyclerView.setAdapter(adapter);

            }
        };

        mViewModel.loadLiveCabs();
        mViewModel.studentLiveCabs.observe(getViewLifecycleOwner(), observer);
        // recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(
                new LinearLayoutManager(getContext()));

    }



}