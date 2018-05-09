package com.filipe.agricontrole.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.filipe.agricontrole.R;
import com.filipe.agricontrole.data.model.Farm;
import com.filipe.agricontrole.holder.FarmHolder;

import java.util.List;

public class FarmAdapter extends RecyclerView.Adapter<FarmHolder> {

    private final List<Farm> farmList;

    public FarmAdapter(List<Farm> farmList) {
        this.farmList = farmList;
    }

    @Override
    public FarmHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new FarmHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_farm, parent, false));
    }

    @Override
    public void onBindViewHolder(FarmHolder holder, int position) {
        holder.farmName.setText(farmList.get(position).getName());
        holder.farmOwner.setText("Proprietário: " + farmList.get(position).getOwner());
        holder.farmAddress.setText(farmList.get(position).getAddress());
    }

    @Override
    public int getItemCount() {
        return farmList != null ? farmList.size() : 0;
    }
}
