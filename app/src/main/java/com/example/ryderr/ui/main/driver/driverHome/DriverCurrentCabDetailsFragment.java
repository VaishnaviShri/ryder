package com.example.ryderr.ui.main.driver.driverHome;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ryderr.R;
import com.example.ryderr.models.Driver;
import com.example.ryderr.models.LiveCab;
import com.example.ryderr.ui.main.driver.driverHome.upcoming.UpcomingViewModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.navigation.Navigation;

public class DriverCurrentCabDetailsFragment extends Fragment {
    private LiveCab liveCabOb;
    private TextView fare, from, to, vehicle, vehicleNo, time, capacity, driver;
    private MaterialButton endBtn;
    private ListView ridersList;
    UpcomingViewModel mViewModel;
    //FragmentDriverCurrentCabDetailsBinding binding;
    public DriverCurrentCabDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        liveCabOb = new LiveCab();
        mViewModel = new UpcomingViewModel();
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        binding = FragmentDriverLiveCabDetailsBinding.inflate(inflater, container, false);
//        View view = binding.getRoot();
//        return view;
        return inflater.inflate(R.layout.fragment_driver_current_cab_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
     //   String cabId = DriverLiveCabDetailsFragmentArgs.fromBundle(getArguments()).getCabId();


        fare = (TextView)view.findViewById(R.id.estimatedFare_livecabdetails);
        from = (TextView)view.findViewById(R.id.from_livecabdetails);
        to = (TextView)view.findViewById(R.id.to_livecabdetails);
        vehicle = (TextView)view.findViewById(R.id.vehicleType_livecabdetails);
        vehicleNo = (TextView)view.findViewById(R.id.vehicleNumber_livecabdetails);
        time = (TextView)view.findViewById(R.id.time_livecabdetails);
        capacity = (TextView)view.findViewById(R.id.capacity_livecabdetails);
        driver = (TextView)view.findViewById(R.id.driver_livecabdetails);
        endBtn = (MaterialButton)view.findViewById(R.id.endRideButton);

        Observer<LiveCab> observer = new Observer<LiveCab>() {
            @Override
            public void onChanged(LiveCab liveCab) {

                liveCabOb = liveCab;
                from.setText(liveCabOb.getFrom_location());
                fare.setText(liveCabOb.getFareText());
                to.setText(liveCabOb.getTo_location());
                vehicle.setText(liveCabOb.getVehicle_type());
                vehicleNo.setText(liveCabOb.getVehicle_number());
                time.setText(liveCabOb.getDeparture_time());
                capacity.setText(String.valueOf(liveCabOb.getCapacity()));
                driver.setText(liveCabOb.getDriver_name());

                ridersList = (ListView)view.findViewById(R.id.ridersList_livecabdetails);


                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, liveCabOb.getDisplay());
                ridersList.setAdapter(arrayAdapter);
            }
        };
        String driverId = FirebaseAuth.getInstance().getUid();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("drivers").document(driverId);
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Driver driver = documentSnapshot.toObject(Driver.class);
                String cabId = driver.getCurrent_ride_id();
                if(cabId!=null){
                    endBtn.setOnClickListener(view1 -> {
                        mViewModel.endRide(cabId);
                        Navigation.findNavController(view).navigate(R.id.action_driverCurrentCab_to_driverFragment);

                    });
                    mViewModel.getDriverLiveCabDetails(cabId).observe(getViewLifecycleOwner(), observer);
                }
                else{
                    view.findViewById(R.id.driverLiveCabDetailsCard).setVisibility(View.GONE);
                    view.findViewById(R.id.startRideButton).setVisibility(View.GONE);
                    Toast.makeText(getContext(),"No rides active right now!", Toast.LENGTH_LONG).show();
                }
            }
        });


//        Observer<ArrayList<String>> namesObserver = new Observer<ArrayList<String>>() {
//            @Override
//            public void onChanged(ArrayList<String> names) {
//                ridersList.setAdapter(new ArrayAdapter<String>(
//                        getContext(),
//                        android.R.layout.simple_list_item_1,
//                        names
//                ));
//            }
//        };
//        mViewModel.getRidersNames(cabId).observe(getViewLifecycleOwner(),namesObserver);
//        binding.driverLiveCabDetailsCard.ridersListLivecabdetails.setAdapter(
//                new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1,
//                        (List<String>) mViewModel.getRidersNames(cabId).getValue())
//        );


        super.onViewCreated(view, savedInstanceState);
    }
}