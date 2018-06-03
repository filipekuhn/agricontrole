package com.filipe.agricontrole.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.filipe.agricontrole.R;

public class PlantingHolder extends RecyclerView.ViewHolder {

    public TextView plotName, type, date, population, emergencyDate, harvestDate;
    public ImageButton btnEdit, btnDelete, btnView;

    public PlantingHolder(View itemView) {
        super(itemView);
        plotName = (TextView) itemView.findViewById(R.id.planting_plotName);
        type = (TextView) itemView.findViewById(R.id.planting_type);
        date = (TextView) itemView.findViewById(R.id.planting_date);
        population = (TextView) itemView.findViewById(R.id.planting_population);
        emergencyDate = (TextView) itemView.findViewById(R.id.planting_emergencyDate);
        harvestDate = (TextView) itemView.findViewById(R.id.planting_harvestDate);
        btnView = (ImageButton) itemView.findViewById(R.id.btnView);
        btnEdit = (ImageButton) itemView.findViewById(R.id.btnEdit);
        btnDelete = (ImageButton) itemView.findViewById(R.id.btnDelete);
    }
}
