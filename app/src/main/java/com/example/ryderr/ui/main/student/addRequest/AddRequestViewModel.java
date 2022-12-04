package com.example.ryderr.ui.main.student.addRequest;

import com.google.firebase.firestore.FirebaseFirestore;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class AddRequestViewModel extends ViewModel {
    // TODO: Implement the ViewModel
    LiveData<AddRequest> addRequest;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

}