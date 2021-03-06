package com.filipe.agricontrole.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.filipe.agricontrole.R;

public class FarmHolder extends RecyclerView.ViewHolder{

    public TextView farmName, farmAddress, farmOwner, farmCity;
    public ImageButton btnEdit, btnDelete, btnView;

    public FarmHolder(View itemView) {
        super(itemView);
        farmName = (TextView) itemView.findViewById(R.id.farmName);
        farmOwner = (TextView) itemView.findViewById(R.id.farmOwner);
        farmAddress = (TextView) itemView.findViewById(R.id.farmAddress);
        farmCity = (TextView) itemView.findViewById(R.id.farmCity);
        btnView = (ImageButton) itemView.findViewById((R.id.btnView));
        btnEdit = (ImageButton) itemView.findViewById(R.id.btnEdit);
        btnDelete = (ImageButton) itemView.findViewById(R.id.btnDelete);
    }
}

