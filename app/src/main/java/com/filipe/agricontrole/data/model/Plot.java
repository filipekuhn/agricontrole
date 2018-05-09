package com.filipe.agricontrole.data.model;

public class Plot {

    public static final String TABLE = "farm";
    // Labels Table Columns names
    public static final String KEY_PlotId = "id";
    public static final String KEY_FarmId = "farm_id";
    public static final String KEY_Name = "name";
    public static final String KEY_Area = "area";

    private int id, farm_id;
    private String name;
    private double area;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFarm_id() {
        return farm_id;
    }

    public void setFarm_id(int farm_id) {
        this.farm_id = farm_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getArea() {
        return area;
    }

    public void setArea(double area) {
        this.area = area;
    }
}
