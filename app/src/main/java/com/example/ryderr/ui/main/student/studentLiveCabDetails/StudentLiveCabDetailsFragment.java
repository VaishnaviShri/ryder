package com.example.ryderr.ui.main.student.studentLiveCabDetails;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.ryderr.R;
import com.example.ryderr.models.LiveCab;
import com.example.ryderr.ui.main.student.studentHome.live.LiveCabsViewModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

public class StudentLiveCabDetailsFragment extends Fragment {
    private LiveCab liveCabOb;
    private TextView fare, from, to, vehicle, vehicleNo, time, capacity, driver;
    private ListView ridersList;
    private LiveCabsViewModel mViewModel;
    public StudentLiveCabDetailsFragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        liveCabOb = new LiveCab();
        mViewModel = new LiveCabsViewModel();
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_student_live_cab_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        fare = (TextView)view.findViewById(R.id.estimatedFare_livecabdetails);
        from = (TextView)view.findViewById(R.id.from_livecabdetails);
        to = (TextView)view.findViewById(R.id.to_livecabdetails);
        vehicle = (TextView)view.findViewById(R.id.vehicleType_livecabdetails);
        vehicleNo = (TextView)view.findViewById(R.id.vehicleNumber_livecabdetails);
        time = (TextView)view.findViewById(R.id.time_livecabdetails);
        capacity = (TextView)view.findViewById(R.id.capacity_livecabdetails);
        driver = (TextView)view.findViewById(R.id.driver_livecabdetails);

        String cabId = StudentLiveCabDetailsFragmentArgs.fromBundle(getArguments()).getCabId();

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
                ArrayList<String> rider = liveCabOb.getRiders_names();
                double riderFare = liveCabOb.getFare()/ liveCabOb.getCount_riders();
//                ArrayList<String> display = new ArrayList<String>();
//                Iterator itr = rider.iterator();
//                while(itr.hasNext()){
//                    display.add(itr.next().toString()+"\t \t" + riderFare);
//                }
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, rider);
                ridersList.setAdapter(arrayAdapter);
            }
        };

        mViewModel.getLiveCabDetail(cabId).observe(getViewLifecycleOwner(), observer);

        Button studentJoinCabBtn = view.findViewById(R.id.studentLiveCabJoinBtn);
        studentJoinCabBtn.setOnClickListener(view1 -> {
            String uid = FirebaseAuth.getInstance().getUid();
            String ridername = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            db.collection("cabs").document(liveCabOb.getLive_cab_id()).update(
                            "riders_names", FieldValue.arrayUnion(ridername))
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Navigation.findNavController(view).navigate(R.id.action_studentCabDetailsFragment_to_cabsFragment);
                        }
                    });
            //

        });
        Button chatBtn = view.findViewById(R.id.studentLiveCabChatBtn);
        chatBtn.setOnClickListener(view1 -> {
            String requestId = liveCabOb.getLive_cab_id();
            StudentLiveCabDetailsFragmentDirections.ActionStudentLiveCabDetailsFragmentToChatFragment action = StudentLiveCabDetailsFragmentDirections.actionStudentLiveCabDetailsFragmentToChatFragment();
            action.setGroupId(requestId);
            Navigation.findNavController(view).navigate((NavDirections) action);

        });

        super.onViewCreated(view, savedInstanceState);
    }
}