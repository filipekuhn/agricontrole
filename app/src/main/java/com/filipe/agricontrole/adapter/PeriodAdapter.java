package com.filipe.agricontrole.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.filipe.agricontrole.R;
import com.filipe.agricontrole.data.model.Period;
import com.filipe.agricontrole.data.repo.PeriodRepo;
import com.filipe.agricontrole.holder.PeriodHolder;

import java.util.List;

public class PeriodAdapter extends RecyclerView.Adapter<PeriodHolder> {

    private final List<Period> periodList;

    public PeriodAdapter(List<Period> periodList) {
        this.periodList = periodList;
    }

    @Override
    public PeriodHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new PeriodHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_period, parent, false));
    }

    @Override
    public void onBindViewHolder(PeriodHolder holder, int position) {
        holder.periodName.setText(periodList.get(position).getName());
        //holder.farmCity.setText("Cidade: " + farmList.get(position).getCity().getName().toString() + " - "
            //    + farmList.get(position).getState().getUf().toString());
        //holder.btnDelete.setOnClickListener(view -> delete(holder.getAdapterPosition())); //Listener to delete farm...
    }

    @Override
    public int getItemCount() {
        return periodList != null ? periodList.size() : 0;
    }

    public void delete(int position) { //remove the row of the recycler and remove the farm from database
        PeriodRepo periodRepo = new PeriodRepo();
        int index = periodList.get(position).getId();

        //if(periodRepo.delete(index)){
            periodList.remove(position);
            notifyItemRemoved(position);
        //}
    }
}
