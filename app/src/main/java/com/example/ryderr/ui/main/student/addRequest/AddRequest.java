package com.example.ryderr.ui.main.student.addRequest;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.ryderr.R;
import com.example.ryderr.models.Request;
import com.example.ryderr.models.Student;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

public class AddRequest extends Fragment {

    private static final String TAG = "Add request fragment";
    private AddRequestViewModel mViewModel;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    Button button;
    RadioButton typeButton;
    RadioGroup radioGroup;
    int r_id;

    public static AddRequest newInstance() {
        return new AddRequest();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_request, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        EditText timeET = view.findViewById(R.id.editViewDateTime);

        Spinner pickup_spinner = (Spinner) view.findViewById(R.id.pickup_spinner);
        ArrayAdapter<CharSequence> pickup_adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.locations, android.R.layout.simple_spinner_item);
        pickup_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        pickup_spinner.setAdapter(pickup_adapter);



        Spinner destination_spinner = (Spinner) view.findViewById(R.id.destination_spinner);
        ArrayAdapter<CharSequence> destination_adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.locations, android.R.layout.simple_spinner_item);
        destination_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        destination_spinner.setAdapter(destination_adapter);




        Spinner type_spinner = (Spinner) view.findViewById(R.id.vehicle_type_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.vechicle_types, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        type_spinner.setAdapter(adapter);



        Spinner capacity_spinner = (Spinner) view.findViewById(R.id.capacity_spinner);
        ArrayAdapter<CharSequence> capacityAdapter = ArrayAdapter.createFromResource(getContext(),
                R.array.available_capacities, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        capacity_spinner.setAdapter(capacityAdapter);




        //int r_id = 0;




        Student requestingStudent = new Student();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        ArrayList<String> riders_ids = new ArrayList<>();
        riders_ids.add(user.getUid());
        ArrayList<String> riders_names = new ArrayList<>();
        riders_names.add(user.getDisplayName());




        Button addButton = view.findViewById(R.id.submit_request_button);
        addButton.setOnClickListener(view1 -> {

            String from_location = String.valueOf(pickup_spinner.getSelectedItem());
            int fromCode = pickup_spinner.getSelectedItemPosition();

            String destination_location = String.valueOf(destination_spinner.getSelectedItem());
            int destinationCode = destination_spinner.getSelectedItemPosition();

            String capacity_string = String.valueOf(capacity_spinner.getSelectedItem());
            int capacity = Integer.parseInt(capacity_string);

            String vehicle_type = String.valueOf(type_spinner.getSelectedItem());


            if(fromCode==destinationCode){
                Toast.makeText(getContext(), "Choose valid locations", Toast.LENGTH_SHORT).show();
            }else{
                DocumentReference docRef = db.collection("values").document("values");
                docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                r_id = documentSnapshot.get("request_count", Integer.class);
                                Log.d(TAG, "onSuccess: "+ r_id);
                                DocumentReference docRef = db.collection("values").document("values");
                                docRef.update("request_count", r_id+1);

                                String request_id = String.valueOf(r_id);
                                Log.d(TAG, "onViewCreated: String id" + request_id);
                                Request request = new Request(
                                        request_id,
                                        capacity,
                                        vehicle_type,
                                        from_location,
                                        destination_location,
                                        timeET.getText().toString(),
                                        riders_ids,
                                        riders_names
                                );
                                //get fare
                                String tripCode = String.valueOf(fromCode) + String.valueOf(destinationCode);
                                db.collection("fares").document(tripCode).get()
                                                .addOnSuccessListener(
                                                        new OnSuccessListener<DocumentSnapshot>() {
                                                            @Override

                                                            public void onSuccess(
                                                                    DocumentSnapshot documentSnapshot) {
                                                                int fare =0;
                                                                fare =documentSnapshot.get("fare", Integer.class);
                                                                request.setExpected_fare(fare);
                                                                addReq(request, view);
                                                            }
                                                        });


                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.e(TAG, "onFailure: count not fetched"+ e.getMessage() );
                            }
                        });
            }



        });







    }

    private void addReq(Request request, View view){
        db.collection("requests").document(request.getRequest_id()).set(request)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Navigation.findNavController(view).navigate(R.id.action_addRequest_to_cabsFragment);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(), "Couldn't add request :(", Toast.LENGTH_SHORT).show();
                    }
                })
        ;

    }
    

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(AddRequestViewModel.class);
        // TODO: Use the ViewModel
    }

}