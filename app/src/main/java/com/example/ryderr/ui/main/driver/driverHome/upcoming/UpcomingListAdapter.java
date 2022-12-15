package com.example.ryderr.ui.main.driver.driverHome.upcoming;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ryderr.R;
import com.example.ryderr.models.LiveCab;
import com.example.ryderr.ui.main.driver.driverHome.DriverFragmentDirections;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

public class UpcomingListAdapter extends RecyclerView.Adapter<UpcomingViewHolder> {

    List<LiveCab> list = Collections.emptyList();
    Context context;
    ViewGroup parent;

    public UpcomingListAdapter(ArrayList<LiveCab> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public UpcomingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        this.parent = parent;
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

        int capacity = liveCab.getCapacity();
        int count_riders = liveCab.getCount_riders();
        String progressText = count_riders + "/" + capacity;
        holder.cab_progress_text.setText(progressText);
        holder.progress_bar.setMax(capacity);
        holder.progress_bar.setProgress(count_riders);

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DriverFragmentDirections.ActionDriverFragToDriverLiveCabDetailsFragment action = DriverFragmentDirections.actionDriverFragToDriverLiveCabDetailsFragment();
                action.setCabId(liveCab.getLive_cab_id());
                Navigation.findNavController(parent).navigate(action);
             //   Toast.makeText(context, "Clicked", Toast.LENGTH_SHORT).show();
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

