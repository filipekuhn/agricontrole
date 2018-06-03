package com.filipe.agricontrole.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.filipe.agricontrole.ProductActivity;
import com.filipe.agricontrole.R;
import com.filipe.agricontrole.data.model.Product;
import com.filipe.agricontrole.holder.ProductHolder;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductHolder> {
    public List<Product> productList;
    ProductActivity productActivity;

    public ProductAdapter(List<Product> productList, ProductActivity productActivity) {
        this.productList = productList;
        this.productActivity = productActivity;
    }

    @Override
    public ProductHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ProductHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_product, parent, false));
    }

    @Override
    public void onBindViewHolder(ProductHolder holder, int position) {
        holder.name.setText("Nome: " + productList.get(position).getName());
        holder.category.setText("Categoria: " + productList.get(position).getCategory().getName());

        if(productList.get(position).getQuantity() != 0.0)
            holder.quantity.setText("Quantidade: " + productList.get(position).getQuantity() + " " +
                    productList.get(position).getCategory().getUnitType().getName());

        if(productList.get(position).getExpiration_date() != null)
            holder.expirationDate.setText("Validade: " + productList.get(position).getExpiration_date());

        //holder.btnView.setOnClickListener(view -> viewPlot(holder.getAdapterPosition()));//Go to the Plot Activity and show all plots with the Period ID.
        //holder.btnEdit.setOnClickListener(view -> update(holder.getAdapterPosition())); //Listener to update period
        //holder.btnDelete.setOnClickListener(view -> delete(holder.getAdapterPosition())); //Listener to delete period from farm
    }

    @Override
    public int getItemCount() {
        return productList != null ? productList.size() : 0;
    }
}
