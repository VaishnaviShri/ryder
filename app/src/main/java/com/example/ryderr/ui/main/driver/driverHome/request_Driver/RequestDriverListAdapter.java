package com.example.ryderr.ui.main.driver.driverHome.request_Driver;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ryderr.R;
import com.example.ryderr.models.Request;
import com.example.ryderr.ui.main.driver.driverHome.DriverFragmentDirections;

import java.util.Collections;
import java.util.List;

import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

public class RequestDriverListAdapter extends RecyclerView.Adapter<RequestDriverViewHolder>{
    List<Request> list = Collections.emptyList();
    Context context;
    ViewGroup parent;

    public RequestDriverListAdapter(List<Request> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public RequestDriverViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        this.parent = parent;
        View view = inflater.inflate(R.layout.request_driver_card, parent, false);
        RequestDriverViewHolder viewHolder = new RequestDriverViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RequestDriverViewHolder holder, int position) {

        Request request = list.get(position);
        holder.from.setText(request.getFrom_location());
        holder.to.setText(request.getTo_location());
        holder.timeText.setText(request.getTime());

        int capacity = request.getCapacity();
        int count_riders = request.getCount_riders();
        String progressText = count_riders + "/" + capacity;
        holder.d_request_progress_text.setText(progressText);
        holder.d_requestProgressBar.setMax(capacity);
        holder.d_requestProgressBar.setProgress(count_riders);
        holder.fare_text.setText(request.getFare_text());

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DriverFragmentDirections.ActionDriverFragToDriverRequestDetailsFragment action = DriverFragmentDirections.actionDriverFragToDriverRequestDetailsFragment();
                action.setRequestId(request.getRequest_id());
                Navigation.findNavController(parent).navigate(action);
               // Toast.makeText(context, "Clicked", Toast.LENGTH_SHORT).show();
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
