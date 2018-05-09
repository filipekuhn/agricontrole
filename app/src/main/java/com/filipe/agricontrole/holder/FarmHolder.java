package com.filipe.agricontrole.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.filipe.agricontrole.R;

public class FarmHolder extends RecyclerView.ViewHolder {

    public TextView farmName, farmAddress, farmOwner;
    public ImageButton btnEdit, btnDelete;

    public FarmHolder(View itemView) {
        super(itemView);
        farmName = (TextView) itemView.findViewById(R.id.farmName);
        farmOwner = (TextView) itemView.findViewById(R.id.farmOwner);
        farmAddress = (TextView) itemView.findViewById(R.id.farmAddress);
        btnEdit = (ImageButton) itemView.findViewById(R.id.btnEdit);
        btnDelete = (ImageButton) itemView.findViewById(R.id.btnDelete);
    }
}
