package com.example.ryder;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class RideDetails extends Fragment {

    private RideDetailsViewModel mViewModel;

    public static RideDetails newInstance() {
        return new RideDetails();
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_ride_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView textView = view.findViewById(R.id.driver_name);
        TextView textView1 = view.findViewById(R.id.name);
        TextView textView2 = view.findViewById(R.id.registration_number);
        TextView textView3 = view.findViewById(R.id.pickup);
        TextView textView4 = view.findViewById(R.id.destination);
        TextView textView5 = view.findViewById(R.id.date_time);
        TextView textView6 = view.findViewById(R.id.cost);
        TextView textView7 = view.findViewById(R.id.space_left);
        TextView textView8 = view.findViewById(R.id.confirmed_passengers);

       // mViewModel.driverName.observe(getViewLifecycleOwner(),observer );
        final Observer<Ride> observer1 = new Observer<Ride>() {
            @Override
            public void onChanged(Ride ride) {
                String driverName = ride.driverName;
                String name = ride.name;
                String reg_number = ride.vehicleNumber;
                String pickup = ride.pickup;
                String destination = ride.destination;
                String date_time = ride.dateTime;
                String cost = ride.cost;
                String space = ride.space;
                String confirmed_pass = ride.confirmedPassengers;

            }
        };
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(RideDetailsViewModel.class);
        // TODO: Use the ViewModel
    }

}