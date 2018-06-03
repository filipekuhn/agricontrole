package com.filipe.agricontrole.data.model;

public class Category {
    public static final String TABLE = "category";
    // Labels Table Columns names
    public static final String KEY_CategoryId = "id";
    public static final String KEY_Name = "name";
    public static final String KEY_UnitType = "unit_type_id";

    private int id;
    private String name;
    private UnitType unitType;

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

    public UnitType getUnitType() {
        return unitType;
    }

    public void setUnitType(UnitType unitType) {
        this.unitType = unitType;
    }

    public String toString() {
        return this.name;            //To display the name in spinner
    }
}
