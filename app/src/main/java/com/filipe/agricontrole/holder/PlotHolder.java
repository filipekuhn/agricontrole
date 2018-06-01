package com.filipe.agricontrole.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.filipe.agricontrole.R;

public class PlotHolder extends RecyclerView.ViewHolder{

    public TextView plotName, plotArea, periodName;
    public ImageButton btnEdit, btnDelete, btnView;

    public PlotHolder(View itemView) {
        super(itemView);
        periodName = (TextView) itemView.findViewById(R.id.plotPeriod);
        plotName = (TextView) itemView.findViewById(R.id.plotName);
        plotArea = (TextView) itemView.findViewById(R.id.plotArea);
        btnView = (ImageButton) itemView.findViewById(R.id.btnView);
        btnEdit = (ImageButton) itemView.findViewById(R.id.btnEdit);
        btnDelete = (ImageButton) itemView.findViewById(R.id.btnDelete);
    }
}
