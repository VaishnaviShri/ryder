package com.example.ryderr.ui.main.student.studentHome.request;

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

public class RequestViewModel extends ViewModel {
    private static final String TAG = "Request view model";
    // TODO: Implement the ViewModel

    public MutableLiveData<ArrayList<Request>> studentRequests;
    public MutableLiveData<Request> mRequestLiveData;
    private DatabaseReference mDatabase;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    public ArrayList<Request> populate() {

        ArrayList<Request> list = new ArrayList<>();
        list.add(new Request());
        list.add(new Request());
        list.add(new Request());


        return list;
    }

    public MutableLiveData<ArrayList<Request>> loadRequests() {

        if (studentRequests == null) {
            studentRequests = new MutableLiveData<>();
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
                            studentRequests.setValue(requestList);
                        } else {
                            Log.d(TAG, "Error getting requests documents: ", task.getException());
                        }
                    }
                });


        return studentRequests;
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

                        ArrayList<String> ridersNames = new ArrayList<>();
                        ArrayList<String> ridersIds = request[0].getRiders_ids();
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
                        request[0].setRiders_names(ridersNames);


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