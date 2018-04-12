package com.filipe.agricontrole.data.repo;

import com.filipe.agricontrole.data.model.Farm;

public class FarmRepo {

    private Farm farm;

    public FarmRepo(){
        farm = new Farm();
    }

    public static String createTable() {
        return "CREATE TABLE IF NOT EXISTS '" + Farm.TABLE  + "' ('"
                + Farm.KEY_FarmId  + "'  INTEGER PRIMARY KEY AUTOINCREMENT, '"
                + Farm.KEY_AgronomisId + "' INTEGER NOT NULL, '"
                + Farm.KEY_Name + "' TEXT NOT NULL, '"
                + Farm.KEY_Address + "' TEXT NOT NULL, '"
                + Farm.KEY_City + "' INTEGER NOT NULL UNIQUE, '"
                + Farm.KEY_State + "' INTEGER NOT NULL "
                + ");";
    }

}
