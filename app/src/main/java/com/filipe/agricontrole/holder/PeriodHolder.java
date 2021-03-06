package com.filipe.agricontrole.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.filipe.agricontrole.R;

public class PeriodHolder extends RecyclerView.ViewHolder {
    public TextView periodFarm, periodName;
    public ImageButton btnEdit, btnDelete, btnView;

    public PeriodHolder(View itemView) {
        super(itemView);
        periodName = (TextView) itemView.findViewById(R.id.periodName);
        btnView = (ImageButton) itemView.findViewById(R.id.btnView);
        btnEdit = (ImageButton) itemView.findViewById(R.id.btnEdit);
        btnDelete = (ImageButton) itemView.findViewById(R.id.btnDelete);
    }
}
