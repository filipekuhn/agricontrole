package com.filipe.agricontrole.data.model;

public class Plot {

    public static final String TABLE = "plot";
    // Labels Table Columns names
    public static final String KEY_PlotId = "id";
    public static final String KEY_Name = "name";
    public static final String KEY_Area = "area";
    public static final String KEY_PeriodId = "period_id";

    private int id;
    private String name;
    private double area;
    private Period periodId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Period getPeriodId() {
        return periodId;
    }

    public void setPeriodId(Period periodId) {
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
