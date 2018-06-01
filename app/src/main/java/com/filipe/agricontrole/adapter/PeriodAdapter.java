package com.filipe.agricontrole.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.filipe.agricontrole.FarmManagementActivity;
import com.filipe.agricontrole.R;
import com.filipe.agricontrole.data.model.Period;
import com.filipe.agricontrole.holder.PeriodHolder;

import java.util.List;

public class PeriodAdapter extends RecyclerView.Adapter<PeriodHolder> {

    public List<Period> periodList;
    FarmManagementActivity farmManagementActivity;

    public PeriodAdapter(List<Period> periodList, FarmManagementActivity farmManagementActivity) {
        this.periodList = periodList;
        this.farmManagementActivity = farmManagementActivity;
    }

    @Override
    public PeriodHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new PeriodHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_period, parent, false));
    }

    @Override
    public void onBindViewHolder(PeriodHolder holder, int position) {
        holder.periodName.setText(periodList.get(position).getName());

        holder.btnView.setOnClickListener(view -> viewPlot(holder.getAdapterPosition()));//Go to the Plot Activity and show all plots with the Period ID.
        holder.btnEdit.setOnClickListener(view -> update(holder.getAdapterPosition())); //Listener to update period
        holder.btnDelete.setOnClickListener(view -> delete(holder.getAdapterPosition())); //Listener to delete period from farm
    }

    @Override
    public int getItemCount() {
        return periodList != null ? periodList.size() : 0;
    }

    public void delete(int position){
        int index = periodList.get(position).getId();

        farmManagementActivity.deletePeriod(position, index);
    }

    public void update(int position){
        int index = periodList.get(position).getId();

        farmManagementActivity.updatePeriod(position, index);
    }

    public void viewPlot(int position){
        int index = periodList.get(position).getId();
        String name = periodList.get(position).getName();

        farmManagementActivity.plotActivity(index, name);
    }
}
