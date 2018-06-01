package com.filipe.agricontrole.data.model;

public class Plot {

    public static final String TABLE = "farm";
    // Labels Table Columns names
    public static final String KEY_PlotId = "id";
    public static final String KEY_PeriodId = "period_id";
    public static final String KEY_Name = "name";
    public static final String KEY_Area = "area";

    private int id, periodId;
    private String name;
    private double area;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPeriodId() {
        return periodId;
    }

    public void setPeriodId(int farm_id) {
        this.periodId = periodId;
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
