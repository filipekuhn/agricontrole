package com.filipe.agricontrole.data.model;

public class UnitType {
    public static final String TABLE = "unit_type";
    // Labels Table Columns names
    public static final String KEY_UnitTypeId = "id";
    public static final String KEY_Name = "name";

    private int id;
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
