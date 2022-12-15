package com.example.ryderr.ui.main.driver;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.ryderr.R;
import com.example.ryderr.models.LiveCab;
import com.example.ryderr.ui.main.driver.driverHome.upcoming.UpcomingListAdapter;
import com.example.ryderr.ui.main.driver.driverHome.upcoming.UpcomingViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class DriverProfileFragment extends Fragment {

    FirebaseAuth mAuth;
    public DriverProfileFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_driver_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        Button logoutBtn = view.findViewById(R.id.driver_logout_button);
        TextView name = view.findViewById(R.id.driver_profile_name);
        TextView email = view.findViewById(R.id.driver_profile_email);
        FirebaseUser user = mAuth.getCurrentUser();
        String nameText = "Name: " + user.getDisplayName();
        String emailText = "Email: " + user.getEmail();
        name.setText(nameText);
        email.setText(emailText);
        logoutBtn.setOnClickListener(view1 -> {
            mAuth.signOut();
            Navigation.findNavController(view).navigate(R.id.action_driverProfileFragment_to_splashScreen);

        });

        UpcomingViewModel mViewModel = new UpcomingViewModel();
        RecyclerView recyclerView = getView().findViewById(R.id.past_ride_recycler);
        Observer<ArrayList<LiveCab>> observer = new Observer<ArrayList<LiveCab>>() {
            @Override
            public void onChanged(ArrayList<LiveCab> cabs) {
                ArrayList<LiveCab> list = cabs;
                UpcomingListAdapter adapter = new UpcomingListAdapter(cabs, getContext());
                recyclerView.setAdapter(adapter);

            }
        };
        mViewModel.getDriverPastRides().observe(getViewLifecycleOwner(), observer);
        recyclerView.setLayoutManager(
                new LinearLayoutManager(getContext()));

    }
}