package com.filipe.agricontrole.data.model;

public class Period {

    public static final String TAG = Farm.class.getSimpleName();
    public static final String TABLE = "period";
    // Labels Table Columns names
    public static final String KEY_PeriodId = "id";
    public static final String KEY_PeriodName = "name";
    public static final String KEY_FarmId = "farm_id";

    private Integer id;
    private Integer farmId;
    private String name;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
