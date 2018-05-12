package com.filipe.agricontrole.data.model;

public class State {

    public static final String TABLE = "state";
    // Labels Table Columns names
    public static final String KEY_StateId = "id";
    public static final String KEY_Name = "name";
    public static final String KEY_UF = "uf";

    private int id;
    private String name, uf;

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

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    @Override
    public String toString() {
        return this.name;            //To display the name in spinner
    }
}
