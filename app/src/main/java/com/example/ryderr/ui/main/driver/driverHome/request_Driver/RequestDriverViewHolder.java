package com.example.ryderr.ui.main.driver.driverHome.request_Driver;

import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.ryderr.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RequestDriverViewHolder extends RecyclerView.ViewHolder{

    TextView from, to, timeText, fare_text, request_name, d_request_progress_text;
    ProgressBar d_requestProgressBar;
    Button acceptButton;
    View view;

    public RequestDriverViewHolder(@NonNull View itemView) {
        super(itemView);

        from = itemView.findViewById(R.id.request_driver_from_text);
        to = itemView.findViewById(R.id.request_driver_to_text);
        timeText = itemView.findViewById(R.id.request_driver_text_time);
        fare_text = itemView.findViewById(R.id.request_driver_fare);
        acceptButton = itemView.findViewById(R.id.request_accept_button);
        d_request_progress_text = itemView.findViewById(R.id.request_driver_progress_text);
        d_requestProgressBar = itemView.findViewById(R.id.request_driver_linearProgressIndicator);

        view = itemView;
    }
}
