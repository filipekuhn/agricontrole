package com.filipe.agricontrole.data.model;

public class Stock {

    public static final String TAG = Farm.class.getSimpleName();
    public static final String TABLE = "stock";
    // Labels Table Columns names
    public static final String KEY_StockId = "id";
    public static final String KEY_FarmId = "farm_id";

    private Integer id;
    private Integer farmId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getFarmId() {
        return farmId;
    }

    public void setFarmId(Integer farmId) {
        this.farmId = farmId;
    }
}
