package com.example.ryderr.ui.main.chat;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.ryderr.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ChatFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChatFragment extends Fragment {
    private ChatViewModel chatViewModel;
    private RecyclerView mRecyclerView;
    private ChatAdapter adapter;

    public static ChatFragment newInstance(){ return new ChatFragment(); }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        chatViewModel  = new ViewModelProvider(this).get(ChatViewModel.class);
    }
    public static ArrayList<ChatMessage> getData(){
        ArrayList<ChatMessage> list = new ArrayList<>();
        list.add(new ChatMessage("hi hitaishi","",""));
        list.add(new ChatMessage("hi vaish","",""));
        return list;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_chat, container, false);
        mRecyclerView = (RecyclerView) layout.findViewById(R.id.chat_recycler_view);
        adapter = new ChatAdapter(getContext(),getData());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(adapter);
        return layout;
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String groupId = ChatFragmentArgs.fromBundle(getArguments()).getGroupId();

        RecyclerView msgRecyclerView = view.findViewById(R.id.chat_recycler_view);

        Observer<List<ChatMessage>> chatObserver = new Observer<List<ChatMessage>>() {
            @Override
            public void onChanged(List<ChatMessage> chatMessages) {
                ChatAdapter chatAdapter = new ChatAdapter(getContext(), chatMessages);
                msgRecyclerView.setAdapter(chatAdapter);
            }
        };
        chatViewModel.getmChatMessages(groupId).observe(getViewLifecycleOwner(), chatObserver);

        final EditText msgInputText = (EditText)view.findViewById(R.id.chat_input_msg);
        ImageButton sendBtn = (ImageButton)view.findViewById(R.id.chat_send_msg);
        sendBtn.setOnClickListener(new View.OnClickListener() {
                                       @Override
                                       public void onClick(View view) {
                                           String msgContent = msgInputText.getText().toString();
                                           String curTime = Calendar.getInstance().getTime().toString();
                                           ChatMessage chatMessage = new ChatMessage(msgContent, curTime);
                                           chatMessage.setUserId(FirebaseAuth.getInstance().getUid());
                                           chatMessage.setUsername(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
                                           msgInputText.setText("");
                                           chatViewModel.sendMessage(groupId, chatMessage);
                                       }
                                   });

        }
    }
