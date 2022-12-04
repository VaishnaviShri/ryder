package com.example.ryderr.ui.main.driver.driverHome.upcoming;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.ryderr.R;
import com.example.ryderr.models.LiveCab;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class UpcomingListAdapter extends RecyclerView.Adapter<UpcomingViewHolder> {

    List<LiveCab> list = Collections.emptyList();
    Context context;

    public UpcomingListAdapter(ArrayList<LiveCab> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public UpcomingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.upcoming_cab_card, parent, false);
        UpcomingViewHolder viewHolder = new UpcomingViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(UpcomingViewHolder holder, int position) {

        LiveCab liveCab = list.get(position);
        holder.from.setText(liveCab.getFrom_location());
        holder.to.setText(liveCab.getTo_location());
        holder.timeText.setText(liveCab.getDeparture_time());
        holder.fare_text.setText(liveCab.getFareText());

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Clicked", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void onAttachedToRecyclerView(
            RecyclerView recyclerView)
    {
        super.onAttachedToRecyclerView(recyclerView);
    }
}

