package com.example.ryderr.ui.main.chat;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;


import com.example.ryderr.R;


import java.util.Calendar;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ChatFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChatFragment extends Fragment {
    private ChatViewModel chatViewModel;

    public static ChatFragment newInstance(){ return new ChatFragment(); }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        chatViewModel  = new ViewModelProvider(this).get(ChatViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_chat, container, false);
    }
        // TODO: Use the ViewModel, define list using observer and then set adapter for the list


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final RecyclerView msgRecyclerView = (RecyclerView)view.findViewById(R.id.chat_recycler_view);
        final Observer<List<ChatMessage>> chatObserver = new Observer<List<ChatMessage>>() {
            @Override
            public void onChanged(List<ChatMessage> chatMessages) {
                final ChatAdapter chatAdapter = new ChatAdapter(chatMessages);
                msgRecyclerView.setAdapter(chatAdapter);
            }
        };
        chatViewModel.getmChatMessages().observe(getViewLifecycleOwner(), chatObserver);

        final EditText msgInputText = (EditText)view.findViewById(R.id.chat_input_msg);
        ImageButton sendBtn = (ImageButton)view.findViewById(R.id.chat_send_msg);
        sendBtn.setOnClickListener(new View.OnClickListener() {
                                       @Override
                                       public void onClick(View view) {
                                           String msgContent = msgInputText.getText().toString();
                                           String curTime = Calendar.getInstance().getTime().toString();
                                           ChatMessage chatMessage = new ChatMessage(msgContent, curTime);
                                           chatViewModel.sendMessage(chatMessage);
                                       }
                                   });

        }
    }
