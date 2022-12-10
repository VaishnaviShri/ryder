package com.example.ryderr.ui.main.driver.driverHome.upcoming;

import android.util.Log;

import com.example.ryderr.models.LiveCab;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class UpcomingViewModel extends ViewModel {
    private static final String TAG = "Upcoming viewmodel";
    public MutableLiveData<ArrayList<LiveCab>> driverCabslist;
    private DatabaseReference mDatabase;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    public MutableLiveData<ArrayList<LiveCab>> getDriverCabs(){
        if(driverCabslist==null){
            driverCabslist = new MutableLiveData<>();
        }
        mDatabase = FirebaseDatabase.getInstance().getReference();


        String driverId = FirebaseAuth.getInstance().getUid();
        ArrayList<LiveCab> list = new ArrayList<>();
        db.collection("cabs")
                //.whereEqualTo("live", true)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                LiveCab liveCab = document.toObject(LiveCab.class);
                                if(Objects.equals(liveCab.getDriver_id(), driverId))
                                    list.add(liveCab);

                            }
                            driverCabslist.setValue(list);
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
        Log.d(TAG, list.toString());

        return driverCabslist;
    }

    MutableLiveData<LiveCab> driverLiveCabDetails;
    public MutableLiveData<LiveCab> getDriverLiveCabDetails(String cabId){
        if(driverLiveCabDetails ==null)
            driverLiveCabDetails = new MutableLiveData<>();


        final LiveCab[] cab = {new LiveCab()};

        DocumentReference docRef = db.collection("cabs").document(cabId);
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        cab[0] = documentSnapshot.toObject(LiveCab.class);
                        Log.d(TAG, cab[0].getFrom_location());

                        ArrayList<String> ridersNames = new ArrayList<>();
                        ArrayList<String> ridersIds = cab[0].getRiders_ids();
                        for(int i=0;i<ridersIds.size();i++){
                            String id = ridersIds.get(i);
                            DocumentReference d = db.collection("students").document(id);
                            d.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                @Override
                                public void onSuccess(DocumentSnapshot documentSnapshot) {
                                    String name = documentSnapshot.get("displayName", String.class);
                                    Log.d(TAG, "onSuccess: "+ name);
                                    ridersNames.add(name);
                                }
                            });
                        }
                        cab[0].setRiders_names(ridersNames);


                        driverLiveCabDetails.setValue(cab[0]);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, "onFailure: count not fetched"+ e.getMessage() );
                    }
                });

        return driverLiveCabDetails;
    }




    public ArrayList<LiveCab> populate(){

        ArrayList<LiveCab>  list = new ArrayList<>();
        list.add(new LiveCab());
        list.add(new LiveCab());
        list.add(new LiveCab());


        return list;
    }

}