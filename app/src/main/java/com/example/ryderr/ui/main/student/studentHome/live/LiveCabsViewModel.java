package com.example.ryderr.ui.main.student.studentHome.live;

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

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class LiveCabsViewModel extends ViewModel {
    private static final String TAG = "Live cabs view model";
    // TODO: Implement the ViewModel
    public MutableLiveData<ArrayList<LiveCab>> studentLiveCabs;
    private DatabaseReference mDatabase;

    private final FirebaseFirestore db = FirebaseFirestore.getInstance();

    public MutableLiveData<ArrayList<LiveCab>> loadLiveCabs(){
        if(studentLiveCabs==null){
            studentLiveCabs = new MutableLiveData<>();
        }
        mDatabase = FirebaseDatabase.getInstance().getReference();


        ArrayList<LiveCab> liveLiveCabs = new ArrayList<>();
        db.collection("cabs")
           //     .whereEqualTo("live", true)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                LiveCab liveCab = document.toObject(LiveCab.class);
                                if(liveCab.isLive())
                                    liveLiveCabs.add(liveCab);
                            }
                            studentLiveCabs.setValue(liveLiveCabs);
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });

        return studentLiveCabs;
    }
    MutableLiveData<Integer> riderCount;
public MutableLiveData<Integer> getRiderCount(){
    if(riderCount==null)
        riderCount = new MutableLiveData<>(0);
    return riderCount;
}
    public boolean joinCabStudent(String cabId){
        final Boolean[] status = {false};


        DocumentReference d = db.collection("cabs").document(cabId);
        d.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                LiveCab cab = documentSnapshot.toObject(LiveCab.class);
                ArrayList<String> ids = cab.getRiders_ids();
                ArrayList<String> names = cab.getRiders_names();
                String currentId = FirebaseAuth.getInstance().getUid();
                ids.add(currentId);
                cab.setRiders(ids);
                cab.setRiders_names(names);
                int count = cab.getCount_riders();
                riderCount.setValue(count);
                cab.setCount_riders(count+1);
                db.collection("cabs").document(cabId).set(cab);

                status[0] = true;
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                status[0] = false;
            }
        });
        return status[0];

    }

    MutableLiveData<LiveCab> liveCabDetail;
    public MutableLiveData<LiveCab> getLiveCabDetail(String cabId){
        if(liveCabDetail==null)
            liveCabDetail = new MutableLiveData<>();

        final LiveCab[] cab = {new LiveCab()};

        DocumentReference docRef = db.collection("cabs").document(cabId);
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        cab[0] = documentSnapshot.toObject(LiveCab.class);
                        Log.d(TAG, cab[0].getFrom_location());

//                        ArrayList<String> ridersNames = new ArrayList<>();
//                        ArrayList<String> ridersIds = cab[0].getRiders_ids();
//                        for(int i=0;i<ridersIds.size();i++){
//                            String id = ridersIds.get(i);
//                            DocumentReference d = db.collection("students").document(id);
//                            d.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
//                                @Override
//                                public void onSuccess(DocumentSnapshot documentSnapshot) {
//                                    String name = documentSnapshot.get("displayName", String.class);
//                                    Log.d(TAG, "onSuccess: "+ name);
//                                    ridersNames.add(name);
//                                }
//                            });
//                        }
//                        cab[0].setRiders_names(ridersNames);


                        liveCabDetail.setValue(cab[0]);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, "onFailure: count not fetched"+ e.getMessage() );
                    }
                });

        return liveCabDetail;
    }

    public ArrayList<LiveCab> populate(){

        ArrayList<LiveCab>  list = new ArrayList<>();
        list.add(new LiveCab());
        list.add(new LiveCab());
        list.add(new LiveCab());
        list.add(new LiveCab());
        list.add(new LiveCab());
        list.add(new LiveCab());
        list.add(new LiveCab());

        return list;
    }


}