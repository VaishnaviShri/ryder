package com.example.ryderr.ui.main.driver.driverHome.request_Driver;

import android.util.Log;

import com.example.ryderr.models.Request;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class RequestDriverViewModel extends ViewModel {

    private static final String TAG = "driver request vm";
    public MutableLiveData<ArrayList<Request>> driverRequests;
    public MutableLiveData<Request> mRequestLiveData;
    private DatabaseReference mDatabase;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();


    public ArrayList<Request> populate(){

        ArrayList<Request>  list = new ArrayList<>();
        list.add(new Request());
        list.add(new Request());

        return list;
    }

    public MutableLiveData<ArrayList<Request>> loadDriverRequests() {

        if (driverRequests == null) {
            driverRequests = new MutableLiveData<>();
        }
        mDatabase = FirebaseDatabase.getInstance().getReference();

        ArrayList<Request> requestList = new ArrayList<>();
        db.collection("requests")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                Request request = document.toObject(Request.class);
                                requestList.add(request);
                            }
                            driverRequests.setValue(requestList);
                        } else {
                            Log.d(TAG, "Error getting requests documents: ", task.getException());
                        }
                    }
                });

        return driverRequests;
    }
    public MutableLiveData<Request> getRequest(String requestId){
        final Request[] request = {new Request()};

        if(mRequestLiveData==null){
            mRequestLiveData = new MutableLiveData<Request>();
        }

        DocumentReference docRef = db.collection("requests").document(requestId);
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        request[0] = documentSnapshot.toObject(Request.class);
                        Log.d(TAG, request[0].getFrom_location());


                        mRequestLiveData.setValue(request[0]);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, "onFailure: count not fetched"+ e.getMessage() );
                    }
                });




        return mRequestLiveData;
    }
}