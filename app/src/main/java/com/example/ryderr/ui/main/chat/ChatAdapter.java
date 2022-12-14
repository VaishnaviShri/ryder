package com.example.ryderr.ui.main.chat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.ryderr.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ChatAdapter extends RecyclerView.Adapter<ChatViewHolder> {
    List<ChatMessage> msgList;
    Context mContext;

    public ChatAdapter(List<ChatMessage> msgList){
        this.msgList = msgList;
    }
    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_message_view, parent, false);
        return new ChatViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ChatViewHolder holder, int position) {
        ChatMessage cur_msg = this.msgList.get(position);
        String text = cur_msg.getMsgText();
        String time = cur_msg.getMsgTime();
        String type = cur_msg.getMsgType();
        boolean sentMessage = Objects.equals(cur_msg.getUserId(),
                FirebaseAuth.getInstance().getUid());

        Toast.makeText(mContext,cur_msg.getMsgText(),Toast.LENGTH_SHORT).show();
        if(!sentMessage){
            holder.msgLayoutL.setVisibility(LinearLayout.VISIBLE);
            holder.msgTextL.setText(text);
            holder.msgLayoutR.setVisibility(LinearLayout.GONE);
            holder.msgTimeL.setText(time);

        }else{
            holder.msgLayoutR.setVisibility(LinearLayout.VISIBLE);
            holder.msgTextR.setText(text);
            holder.msgLayoutL.setVisibility(LinearLayout.GONE);
            holder.msgTimeR.setText(time);
        }
    }

    @Override
    public int getItemCount() {
        return msgList.size();
    }

}

