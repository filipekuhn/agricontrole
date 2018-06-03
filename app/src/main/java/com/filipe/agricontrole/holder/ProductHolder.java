package com.filipe.agricontrole.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.filipe.agricontrole.R;

public class ProductHolder extends RecyclerView.ViewHolder {
    public TextView name, category,quantity, expirationDate;
    public ImageButton btnEdit, btnDelete, btnView;

    public ProductHolder(View itemView) {
        super(itemView);
        name = (TextView) itemView.findViewById(R.id.product_name);
        category = (TextView) itemView.findViewById(R.id.product_category);
        quantity = (TextView) itemView.findViewById(R.id.product_quantity);
        expirationDate = (TextView) itemView.findViewById(R.id.product_expirationDate);
        btnView = (ImageButton) itemView.findViewById(R.id.btnView);
        btnEdit = (ImageButton) itemView.findViewById(R.id.btnEdit);
        btnDelete = (ImageButton) itemView.findViewById(R.id.btnDelete);
    }
}
