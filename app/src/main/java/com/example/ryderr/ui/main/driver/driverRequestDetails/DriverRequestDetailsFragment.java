package com.example.ryderr.ui.main.driver.driverRequestDetails;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.ryderr.R;
import com.example.ryderr.models.LiveCab;
import com.example.ryderr.models.Request;
import com.example.ryderr.ui.main.driver.driverHome.request_Driver.RequestDriverViewModel;
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

public class DriverRequestDetailsFragment extends Fragment {
    private Request requestObj;
    private TextView from, to, vehicle, time, capacity;
    private MaterialButton acceptBtn;
    private ListView ridersList;
    RequestDriverViewModel mRequestViewModel;

    public DriverRequestDetailsFragment(){}
    @Override
    public void onCreate(Bundle savedInstanceState) {
        requestObj = new Request();
        mRequestViewModel = new RequestDriverViewModel();
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_driver_request_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        String requestId = DriverRequestDetailsFragmentArgs.fromBundle(getArguments()).getRequestId();
        Observer<Request> observer = new Observer<Request>() {
            @Override
            public void onChanged(Request request) {
                requestObj = request;
                from = (TextView) view.findViewById(R.id.from);
                to = (TextView) view.findViewById(R.id.to);
                vehicle = (TextView) view.findViewById(R.id.vehicle);
                time = (TextView) view.findViewById(R.id.time);
                capacity = (TextView) view.findViewById(R.id.capacity);
                acceptBtn = (MaterialButton) view.findViewById(R.id.acceptButton);
                view.findViewById(R.id.ridersText).setVisibility(View.GONE);
                //view.findViewById(R.id.capacityTextView)

                from.setText(requestObj.getFrom_location());
                to.setText(requestObj.getTo_location());
                vehicle.setText(requestObj.getVehicle_type());
                time.setText(requestObj.getTime());
                String count = String.valueOf(requestObj.getCount_riders());
                String capacityText = String.valueOf((requestObj.getCapacity()-requestObj.getCount_riders()));
                String filled = count + " students ready. " + capacityText +" more students can join.";
                capacity.setText(filled);


                acceptBtn.setOnClickListener(view1 -> {
                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                    DocumentReference docRef = db.collection("requests").document(requestId);
                    docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            LiveCab cab = new LiveCab(requestId,
                                    FirebaseAuth.getInstance().getUid(),
                                    true,
                                    request.getFrom_location(),
                                    request.getTo_location(),
                                    request.getTime(),
                                    request.getExpected_fare(),
                                    request.getRiders_ids(),
                                    request.getRiders_names());
                            db.collection("cabs").document(requestId).set(cab);

                            db.collection("requests").document(requestId).delete();
                            Navigation.findNavController(view).navigate(R.id.action_driverRequestDetailsFragment_to_driverFragment);

                        }
                    });

                });
            }
        };
        mRequestViewModel.getRequest(requestId).observe(getViewLifecycleOwner(), observer);

    }
}