package com.filipe.agricontrole.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.filipe.agricontrole.PlotActivity;
import com.filipe.agricontrole.R;
import com.filipe.agricontrole.data.model.Plot;
import com.filipe.agricontrole.holder.PlotHolder;

import java.text.DecimalFormat;
import java.util.List;

public class PlotAdapter extends RecyclerView.Adapter<PlotHolder> {

    public List<Plot> plotList;
    PlotActivity plotActivity;

    public PlotAdapter(List<Plot> plotList, PlotActivity plotActivity) {
        this.plotList = plotList;
        this.plotActivity = plotActivity;
    }

    @Override
    public PlotHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new PlotHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_plot, parent, false));
    }

    @Override
    public void onBindViewHolder(PlotHolder holder, int position) {
        String periodName = plotActivity.getIntent().getExtras().getString("periodName");

        holder.periodName.setText("Safra: " + periodName);
        holder.plotName.setText("Nome do Talhão: " + plotList.get(position).getName());

        DecimalFormat decimalFormat = new DecimalFormat("##0.0000");
        String numberAsString = decimalFormat.format(plotList.get(position).getArea());
        numberAsString.replace(".", ",");
        holder.plotArea.setText("Área: " +  numberAsString + " ha");

        holder.btnView.setOnClickListener(view -> viewPlot(holder.getAdapterPosition()));//Go to the Plot Activity and show all plots with the Period ID.
        //holder.btnEdit.setOnClickListener(view -> update(holder.getAdapterPosition())); //Listener to update period
        holder.btnDelete.setOnClickListener(view -> delete(holder.getAdapterPosition())); //Listener to delete period from farm
    }

    @Override
    public int getItemCount() {
        return plotList != null ? plotList.size() : 0;
    }

    public void delete(int position) {
        int index = plotList.get(position).getId();

        plotActivity.deletePlot(position, index);
    }

    public void viewPlot(int position){
        int index = plotList.get(position).getId();
        String plotName = plotList.get(position).getName();

        plotActivity.plantingActivity(index, plotName);
    }
}
