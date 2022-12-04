package com.example.ryderr.ui.main.student.studentHome.request;

import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.ryderr.R;

import androidx.recyclerview.widget.RecyclerView;

public class RequestCabViewHolder extends RecyclerView.ViewHolder{



    TextView from, to, timeText, fare_text, request_progress_text;
    ProgressBar requestProgressBar;
    Button joinButton;
    View view;


    public RequestCabViewHolder(View itemView) {
        super(itemView);

        from = itemView.findViewById(R.id.request_from_text);
        to = itemView.findViewById(R.id.request_to_text);
        timeText = itemView.findViewById(R.id.request_time_text);
        fare_text = itemView.findViewById(R.id.cab_fare_text);
        requestProgressBar = itemView.findViewById(R.id.request_linearProgressIndicator);
        request_progress_text = itemView.findViewById(R.id.request_progress_text);
        joinButton = itemView.findViewById(R.id.request_join_button);
        view = itemView;
    }
}