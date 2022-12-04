package com.example.ryderr.ui.main.chat;

import java.util.List;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ChatViewModel extends ViewModel {
    private MutableLiveData<List<ChatMessage>> mChatMessages;

    //TODO: initialize mutable live data

    public MutableLiveData<List<ChatMessage>> initChat(){
        if(mChatMessages==null){
            mChatMessages = new MutableLiveData<>();
        }
        return mChatMessages;
    }
    public MutableLiveData<List<ChatMessage>> getmChatMessages() {
        initChat();
        return mChatMessages;
    }

    public void setmChatMessages(MutableLiveData<List<ChatMessage>> mChatMessages) {
        this.mChatMessages = mChatMessages;
    }
    public void sendMessage(ChatMessage chatMessage){
        //TODO
    }
}
