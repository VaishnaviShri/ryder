package com.example.ryderr.ui.main.student.studentHome.live;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ryderr.R;
import com.example.ryderr.models.LiveCab;
import com.example.ryderr.ui.main.student.studentHome.CabsFragmentDirections;

import java.util.Collections;
import java.util.List;

import androidx.lifecycle.LifecycleOwner;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

public class LiveCabListAdapter extends RecyclerView.Adapter<LiveCabViewHolder> {

    List<LiveCab> list = Collections.emptyList();
    Context context;
    LiveCabsViewModel cabViewModel;

    public LiveCabListAdapter(List<LiveCab> list, Context context) {
        this.list = list;
        this.context = context;
    }
    LifecycleOwner l;
    ViewGroup parent;

    @Override
    public LiveCabViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        this.parent = parent;
        LayoutInflater inflater = LayoutInflater.from(context);
        cabViewModel = new LiveCabsViewModel();
l = (LifecycleOwner) parent.getContext();
        View view = inflater.inflate(R.layout.live_cab_card, parent, false);
        LiveCabViewHolder viewHolder = new LiveCabViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(LiveCabViewHolder holder, int position) {

        final int index = holder.getAdapterPosition();

        LiveCab liveCab = list.get(position);//observe list
        String fromText = "from: "+ liveCab.getFrom_location();
        holder.from.setText(fromText);
        String toText = "to: "+ liveCab.getTo_location();
        holder.to.setText(toText);
        holder.timeText.setText(liveCab.getDeparture_time());
        holder.driver_name.setText(liveCab.getDriver_name());
        holder.vehicle.setText(liveCab.getVehicle_number());
        holder.fare_text.setText(liveCab.getFareText());
        int capacity = liveCab.getCapacity();
        int count_riders = liveCab.getCount_riders();
        String progressText = count_riders + "/" + capacity;
        holder.cabProgressText.setText(progressText);
        holder.cabProgressBar.setMax(capacity);
        holder.cabProgressBar.setProgress(count_riders);

//        Observer<Integer> observer = new Observer<Integer>() {
//            @Override
//            public void onChanged(Integer count) {
//                String progressText = count + "/" + capacity;
//                holder.cabProgressText.setText(progressText);
//                holder.cabProgressBar.setProgress(count);
//            }
//        };
//
//        cabViewModel.getRiderCount().observe(l, observer);

        holder.joinButton.setOnClickListener(view -> {
            cabViewModel.joinCabStudent(liveCab.getLive_cab_id());
            holder.joinButton.setVisibility(View.GONE);
            holder.seeDetailBtn.setVisibility(View.VISIBLE);
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CabsFragmentDirections.ActionCabsFragToStudentCabDetailsFragment action = CabsFragmentDirections.actionCabsFragToStudentCabDetailsFragment();
                action.setCabId(liveCab.getLive_cab_id());
                Navigation.findNavController(parent).navigate(action);
            }
        });

//        holder.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //Toast.makeText(context, "Clicked", Toast.LENGTH_SHORT).show();
//                //go to details
//            }
//        });
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

