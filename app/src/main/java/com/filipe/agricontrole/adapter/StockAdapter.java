package com.filipe.agricontrole.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.filipe.agricontrole.FarmManagementActivity;
import com.filipe.agricontrole.R;
import com.filipe.agricontrole.data.model.Stock;
import com.filipe.agricontrole.holder.StockHolder;

import java.util.List;

public class StockAdapter extends RecyclerView.Adapter<StockHolder> {
    private final List<Stock> stockList;
    private FarmManagementActivity farmManagementActivity;

    public StockAdapter(List<Stock> stockList, FarmManagementActivity farmManagementActivity) {
        this.stockList = stockList;
        this.farmManagementActivity = farmManagementActivity;
    }

    @Override
    public StockHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new StockHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_stock, parent, false));
    }

    @Override
    public void onBindViewHolder(StockHolder holder, int position) {
        //holder.periodName.setText(stockList.get(position).getName());
        //holder.farmCity.setText("Cidade: " + farmList.get(position).getCity().getName().toString() + " - "
        //    + farmList.get(position).getState().getUf().toString());
        //holder.btnDelete.setOnClickListener(view -> delete(holder.getAdapterPosition())); //Listener to delete farm...
        holder.btnView.setOnClickListener(view -> view(holder.getAdapterPosition()));
    }

    @Override
    public int getItemCount() {
        return stockList != null ? stockList.size() : 0;
    }

    public void view(int position){
        int index = stockList.get(position).getId();

        farmManagementActivity.productActivity(index);
    }
}


