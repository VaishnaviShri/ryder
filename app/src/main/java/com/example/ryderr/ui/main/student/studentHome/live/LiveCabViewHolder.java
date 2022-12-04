package com.example.ryderr.ui.main.student.studentHome.live;

import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ryderr.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class LiveCabViewHolder extends RecyclerView.ViewHolder {

    TextView from;
    TextView to;
    TextView timeText;
    TextView driver_name;
    TextView vehicle;
    TextView fare_text;
    CircleImageView driverAvatar;
    Button joinButton;
    Button seeDetailBtn;
    ProgressBar cabProgressBar;
    TextView cabProgressText;


    public LiveCabViewHolder(View itemView) {
        super(itemView);

        from = itemView.findViewById(R.id.cab_from_text);
        to = itemView.findViewById(R.id.cab_to_text);
        timeText = itemView.findViewById(R.id.cab_time_text);
        driver_name = itemView.findViewById(R.id.driver_name_text);
        vehicle = itemView.findViewById(R.id.cab_vehicle_number_text);
        fare_text = itemView.findViewById(R.id.cab_fare_text);
        joinButton = itemView.findViewById(R.id.cab_join_button);
        seeDetailBtn = itemView.findViewById(R.id.cab_see_detail_button);
        cabProgressBar = itemView.findViewById(R.id.cab_linearProgressIndicator);
        cabProgressText = itemView.findViewById(R.id.cab_progress_text);

    }
}