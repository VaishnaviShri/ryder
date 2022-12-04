package com.example.ryderr.ui.main.student.rideDetails;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ryderr.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
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


    }



}