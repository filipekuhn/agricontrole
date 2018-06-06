package com.filipe.agricontrole.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.filipe.agricontrole.ProductApplicationActivity;
import com.filipe.agricontrole.R;
import com.filipe.agricontrole.data.model.ProductApplication;
import com.filipe.agricontrole.holder.ProductApplicationHolder;

import java.text.DecimalFormat;
import java.util.List;

public class ProductApplicationAdapter extends RecyclerView.Adapter<ProductApplicationHolder> {
    public List<ProductApplication> productApplicationList;
    ProductApplicationActivity productApplicationActivity;

    public ProductApplicationAdapter(List<ProductApplication> productApplicationList, ProductApplicationActivity productApplicationActivity) {
        this.productApplicationList = productApplicationList;
        this.productApplicationActivity = productApplicationActivity;
    }

    @Override
    public ProductApplicationHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ProductApplicationHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_product_application, parent, false));
    }

    @Override
    public void onBindViewHolder(ProductApplicationHolder holder, int position) {
        holder.name.setText("Produto: " + productApplicationList.get(position).getProduct().getName());
        holder.category.setText("Categoria: " + productApplicationList.get(position).getProduct().getCategory().getName());

        DecimalFormat decimalFormat = new DecimalFormat("##0.00"); //Format with only 2 number after dot
        String numberAsString = decimalFormat.format(productApplicationList.get(position).getQuantity());
        numberAsString.replace(".", ",");

        holder.quantity.setText("Quantidade: " + numberAsString + " " +
                productApplicationList.get(position).getProduct().getCategory().getUnitType().getName());

        holder.date.setText("Data de Aplicação: " + productApplicationList.get(position).getDate());

        //holder.btnView.setOnClickListener(view -> viewPlot(holder.getAdapterPosition()));//Go to the Plot Activity and show all plots with the Period ID.
        holder.btnEdit.setOnClickListener(view -> update(holder.getAdapterPosition())); //Listener to update period
        holder.btnDelete.setOnClickListener(view -> delete(holder.getAdapterPosition())); //Listener to delete period from farm
    }

    @Override
    public int getItemCount() {
        return productApplicationList != null ? productApplicationList.size() : 0;
    }

    public void update(int position){
        int index = productApplicationList.get(position).getId();

        productApplicationActivity.updateProductApplication(index);
    }

    public void delete(int position) {
        //Get the ProductApplication id and quantity. Get the product id in application and set the new product quantity the sum of the quantity's
        int index = productApplicationList.get(position).getId();
        double applicationQuantity = productApplicationList.get(position).getQuantity();
        int productId = productApplicationList.get(position).getProduct().getId();
        double productQuantity = productApplicationList.get(position).getProduct().getQuantity();

        double quantity = applicationQuantity + productQuantity;

        productApplicationActivity.deleteProductApplication(position, index, productId, quantity);
    }
}
