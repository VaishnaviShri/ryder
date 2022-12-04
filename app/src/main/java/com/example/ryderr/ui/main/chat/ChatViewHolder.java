package com.example.ryderr.ui.main.chat;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.ryderr.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ChatViewHolder extends RecyclerView.ViewHolder {
    TextView msgTextR;
    TextView msgTextL;
    LinearLayout msgLayoutL;
    LinearLayout msgLayoutR;
    TextView msgTimeR;
    TextView msgTimeL;

    public ChatViewHolder(@NonNull View itemView) {
        super(itemView);
        if(itemView!=null) {
            msgTimeL = (TextView)itemView.findViewById(R.id.msg_time_left);
            msgTimeR = (TextView)itemView.findViewById(R.id.msg_time_right);
            msgTextR = (TextView)itemView.findViewById(R.id.right_msg_text);
            msgTextL = (TextView)itemView.findViewById(R.id.left_msg_text);
            msgLayoutL = (LinearLayout)itemView.findViewById(R.id.left_msg);
            msgLayoutR = (LinearLayout)itemView.findViewById(R.id.right_msg);

        }
    }

}
