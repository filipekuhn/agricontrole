package com.filipe.agricontrole.data.model;

public class City {

    public static final String TABLE = "city";
    // Labels Table Columns names
    public static final String KEY_CityId = "id";
    public static final String KEY_Name = "name";
    public static final String KEY_StateId = "state_id";

    private int id, state_id;
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getState_id() {
        return state_id;
    }

    public void setState_id(int state_id) {
        this.state_id = state_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
