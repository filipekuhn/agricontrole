package com.filipe.agricontrole.data.model;

public class Planting {

    public static final String TAG = Farm.class.getSimpleName();
    public static final String TABLE = "planting";
    // Labels Table Columns names
    public static final String KEY_PlantingId = "id";
    public static final String KEY_PlotId = "plot_id";
    public static final String KEY_PlantingType = "type";
    public static final String KEY_PlantingDate = "date";
    public static final String KEY_Population = "population";
    public static final String KEY_EmergencyDate = "emergency_date";
    public static final String KEY_HarvestDate = "harvest_date";

    private int id;
    private Plot plot;
    private String type, plantingDate, emergencyDate, harvestDate;
    private double population;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Plot getPlot() {
        return plot;
    }

    public void setPlot(Plot plot) {
        this.plot = plot;
    }

    public String getType(){
        return type;
    }

    public void setType(String type){
        this.type = type;
    }

    public String getPlantingDate(){
        return plantingDate;
    }

    public void setPlantingDate(String plantingDate) {
        this.plantingDate = plantingDate;
    }

    public String getEmergencyDate(){
        return emergencyDate;
    }

    public void setEmergencyDate(String emergencyDate) {
        this.emergencyDate = emergencyDate;
    }

    public String getHarvestDate(){
        return harvestDate;
    }

    public void setHarvestDate(String harvestDate) {
        this.harvestDate = harvestDate;
    }

    public double getPopulation() {
        return population;
    }

    public void setPopulation(double population) {
        this.population = population;
    }
}
