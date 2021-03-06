package com.filipe.agricontrole.data.model;

public class Farm {

    public static final String TAG = Farm.class.getSimpleName();
    public static final String TABLE = "farm";
    // Labels Table Columns names
    public static final String KEY_FarmId = "id";
    public static final String KEY_AgronomisId = "agronomist_id";
    public static final String KEY_Name = "name";
    public static final String KEY_Owner = "owner";
    public static final String KEY_Address = "address";
    public static final String KEY_City = "city";

    private Integer id;
    private Integer agronomistId;
    private String name;
    private String owner;
    private String address;
    private City city;
    private State state;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAgronomistId() {
        return agronomistId;
    }

    public void setAgronomistId(Integer agronomistId) {
        this.agronomistId = agronomistId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }
}
