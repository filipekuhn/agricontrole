package com.filipe.agricontrole.data.repo;

import com.filipe.agricontrole.data.model.UnitType;

public class UnitTypeRepo {
    private final String TAG = StateRepo.class.getSimpleName().toString();

    private UnitType unitType;

    public UnitTypeRepo(){
        unitType = new UnitType();
    }

    public static String createTable() {
        return "CREATE TABLE IF NOT EXISTS " + UnitType.TABLE  + " ("
                + UnitType.KEY_UnitTypeId  + "  INTEGER PRIMARY KEY AUTOINCREMENT, "
                + UnitType.KEY_Name + " TEXT NOT NULL);";
    }

    public static String initialUnitType(){
        return "INSERT INTO " + UnitType.TABLE + " VALUES (1, 'Litros');";
    }

    public static String initalUnitType2(){
        return "INSERT INTO " + UnitType.TABLE + " VALUES (2, 'Kg');";
    }

}
