package com.example.ryderr.ui.main.chat;

import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ChatViewModel extends ViewModel {
    private static final String TAG = "chat view model";
    private MutableLiveData<ArrayList<ChatMessage>> mChatMessages;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    //TODO: initialize mutable live data

    public MutableLiveData<ArrayList<ChatMessage>> initChat(){
        if(mChatMessages==null){
            mChatMessages = new MutableLiveData<ArrayList<ChatMessage>>();
        }
        return mChatMessages;
    }
    public MutableLiveData<ArrayList<ChatMessage>> getmChatMessages(String groupId) {
        initChat();

        ArrayList<ChatMessage> chatsList = new ArrayList<>();
        db.collection("groups").document(groupId).collection(groupId)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(QueryDocumentSnapshot document: task.getResult()){
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                ChatMessage chatMessage = document.toObject(ChatMessage.class);
                                chatsList.add(chatMessage);
                            }
                            if(chatsList.isEmpty()){
                                ChatMessage dummy = new ChatMessage("Let's discuss our travel!", "", FirebaseAuth.getInstance().getUid());
                                chatsList.add(dummy);
                                createGroup(groupId, dummy);
                            }
                            //TODO: sort list by time

                            Collections.sort(chatsList, new Comparator<ChatMessage>() {
                                @Override
                                public int compare(ChatMessage c1, ChatMessage c2) {
                                    return c1.getMsgTime().compareTo(c2.getMsgTime());
                                }
                            });
                            mChatMessages.setValue(chatsList);
                        }else{
                            Log.d(TAG, "Error getting requests documents: ", task.getException());
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, "onFailure: get failed ");
                    }
                });


        //get collection for a particular chat id
        //if collection doesnt exist create a new one
        return mChatMessages;
    }

    public MutableLiveData<ArrayList<ChatMessage>> createGroup(String groupId, ChatMessage dummy){
        db.collection("groups").document(groupId).collection(groupId).add(dummy);

        return mChatMessages;
    }

    public void setmChatMessages(String groupId, MutableLiveData<ArrayList<ChatMessage>> mChatMessages) {
        this.mChatMessages = mChatMessages;
    }
    public void sendMessage(String groupId, ChatMessage chatMessage){
        //TODO
        db.collection("groups").document(groupId).collection(groupId).add(chatMessage);
        getmChatMessages(groupId);
    }
}
