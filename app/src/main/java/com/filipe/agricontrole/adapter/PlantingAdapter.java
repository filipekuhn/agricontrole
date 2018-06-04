package com.filipe.agricontrole.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.filipe.agricontrole.PlantingActivity;
import com.filipe.agricontrole.R;
import com.filipe.agricontrole.data.model.Planting;
import com.filipe.agricontrole.holder.PlantingHolder;

import java.util.List;

public class PlantingAdapter extends RecyclerView.Adapter<PlantingHolder> {

    public List<Planting> plantingList;
    PlantingActivity plantingActivity;

    public PlantingAdapter(List<Planting> plantingList, PlantingActivity plantingActivity) {
        this.plantingList = plantingList;
        this.plantingActivity = plantingActivity;
    }

    @Override
    public PlantingHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new PlantingHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_planting, parent, false));
    }

    @Override
    public void onBindViewHolder(PlantingHolder holder, int position) {
        holder.plotName.setText("Talhão: " + plantingList.get(position).getPlot().getName());
        holder.type.setText("Tipo: " + plantingList.get(position).getType());
        holder.date.setText("Data: " + plantingList.get(position).getPlantingDate());

        if(plantingList.get(position).getPopulation() != 0.0)
            holder.population.setText("População: " + plantingList.get(position).getPopulation());

        if(plantingList.get(position).getEmergencyDate() != null)
            holder.emergencyDate.setText("Data de Emergência: " + plantingList.get(position).getEmergencyDate());

        if(plantingList.get(position).getHarvestDate() != null)
            holder.harvestDate.setText("Data de Colheita: " + plantingList.get(position).getHarvestDate());

        holder.btnView.setOnClickListener(view -> viewProductApplication(holder.getAdapterPosition()));//Go to the Plot Activity and show all plots with the Period ID.
        //holder.btnEdit.setOnClickListener(view -> update(holder.getAdapterPosition())); //Listener to update period
        //holder.btnDelete.setOnClickListener(view -> delete(holder.getAdapterPosition())); //Listener to delete period from farm
    }

    @Override
    public int getItemCount() {
        return plantingList != null ? plantingList.size() : 0;
    }

    /*public void delete(int position) {
        int index = plantingList.get(position).getId();

        //plotActivity.deletePlot(position, index);
    }*/

    public void viewProductApplication(int position){
        int index = plantingList.get(position).getId();

        plantingActivity.productApplicationActivity(index);
    }
}
