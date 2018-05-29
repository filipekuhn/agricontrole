package com.filipe.agricontrole.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.filipe.agricontrole.R;
import com.filipe.agricontrole.data.model.Farm;
import com.filipe.agricontrole.data.repo.FarmRepo;
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
        holder.farmAddress.setText("Endereço: " + farmList.get(position).getAddress());
        holder.farmCity.setText("Cidade: " + farmList.get(position).getCity().getName().toString() + " - "
                + farmList.get(position).getState().getUf().toString());

        holder.btnDelete.setOnClickListener(view -> delete(holder.getAdapterPosition()));
    }

    @Override
    public int getItemCount() {
        return farmList != null ? farmList.size() : 0;
    }


    public void delete(int position) { //removes the row
        FarmRepo farmRepo = new FarmRepo();
        int index = farmList.get(position).getId();

        if(farmRepo.delete(index)){
            farmList.remove(position);
            notifyItemRemoved(position);
        }

    }
}
