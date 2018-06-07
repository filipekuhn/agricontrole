package com.filipe.agricontrole.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.filipe.agricontrole.FarmActivity;
import com.filipe.agricontrole.R;
import com.filipe.agricontrole.data.model.Farm;
import com.filipe.agricontrole.holder.FarmHolder;

import java.util.List;

public class FarmAdapter extends RecyclerView.Adapter<FarmHolder> {

    public List<Farm> farmList;
    //private final List<Farm> farmList;
    FarmActivity farmActivity;

    public FarmAdapter(List<Farm> farmList, FarmActivity farmActivity) {
        this.farmList = farmList;
        this.farmActivity = farmActivity;
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
        holder.farmCity.setText("Cidade: " + farmList.get(position).getCity().getName() + " - "
                + farmList.get(position).getState().getUf());


        holder.btnView.setOnClickListener(view -> viewFarm((holder.getAdapterPosition())));
        holder.btnEdit.setOnClickListener(view -> editFarm((holder.getAdapterPosition())));
        holder.btnDelete.setOnClickListener(view -> delete(holder.getAdapterPosition())); //Listener to delete farm...
    }

    @Override
    public int getItemCount() {
        return farmList != null ? farmList.size() : 0;
    }

    public void delete(int position){
        int index = farmList.get(position).getId();

        farmActivity.deleteFarm(position, index);
    }

    public void editFarm(int position){
        int index = farmList.get(position).getId();

        farmActivity.updateFarm(index);
    }


    public void viewFarm(int position){ //Get the id from farm to open the farm management activity
        int index = farmList.get(position).getId();
        String name = farmList.get(position).getName();

        farmActivity.farmManagement(index, name);

    }
}
