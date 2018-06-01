package com.filipe.agricontrole.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.filipe.agricontrole.R;
import com.filipe.agricontrole.data.model.Stock;
import com.filipe.agricontrole.holder.StockHolder;

import java.util.List;

public class StockAdapter extends RecyclerView.Adapter<StockHolder> {
    private final List<Stock> stockList;

    public StockAdapter(List<Stock> stockList) {
        this.stockList = stockList;
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
    }

    @Override
    public int getItemCount() {
        return stockList != null ? stockList.size() : 0;
    }
}
