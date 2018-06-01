package com.filipe.agricontrole.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.filipe.agricontrole.R;

public class StockHolder extends RecyclerView.ViewHolder {
    public TextView periodName;
    public ImageButton btnEdit, btnDelete;

    public StockHolder(View itemView) {
        super(itemView);
        btnEdit = (ImageButton) itemView.findViewById(R.id.btnEdit);
        btnDelete = (ImageButton) itemView.findViewById(R.id.btnDelete);
    }
}
