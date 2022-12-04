package com.example.ryderr.ui.main.student.studentHome.request;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ryderr.R;
import com.example.ryderr.models.Request;
import com.example.ryderr.ui.main.student.studentHome.CabsFragmentDirections;

import java.util.Collections;
import java.util.List;

import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

public class RequestCabListAdapter extends RecyclerView.Adapter<RequestCabViewHolder>{
    List<Request> list = Collections.emptyList();
    Context context;
    ViewGroup parent;

    RequestViewModel mRequestViewModel;
    public RequestCabListAdapter(List<Request> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public RequestCabViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        this.parent = parent;
        mRequestViewModel = new RequestViewModel();
        View view = inflater.inflate(R.layout.request_cab_card, parent, false);
        RequestCabViewHolder viewHolder = new RequestCabViewHolder(view);


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RequestCabViewHolder holder, int position) {


        Request request = list.get(position);
        holder.from.setText(request.getFrom_location());
        holder.to.setText(request.getTo_location());
        holder.timeText.setText(request.getTime());

        int capacity = request.getCapacity();
        int count_riders = request.getCount_riders();
        String progressText = count_riders + "/" + capacity;
        holder.request_progress_text.setText(progressText);
        holder.requestProgressBar.setMax(capacity);
        holder.requestProgressBar.setProgress(count_riders);


        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CabsFragmentDirections.ActionCabsFragToStudentRequestDetailsFragment action = CabsFragmentDirections.actionCabsFragToStudentRequestDetailsFragment();
                action.setRequestId(request.getRequest_id());
                Navigation.findNavController(parent).navigate(action);
                //Toast.makeText(context, "Clicked", Toast.LENGTH_SHORT).show();
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
