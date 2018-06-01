package com.filipe.agricontrole.data.model;

public class Period {

    public static final String TAG = Farm.class.getSimpleName();
    public static final String TABLE = "period";
    // Labels Table Columns names
    public static final String KEY_PeriodId = "id";
    public static final String KEY_PeriodName = "name";
    public static final String KEY_FarmId = "farm_id";

    private Integer id;
    private Farm farm;
    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Farm getFarm() {
        return farm;
    }

    public void setFarm(Farm farm) {
        this.farm = farm;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
