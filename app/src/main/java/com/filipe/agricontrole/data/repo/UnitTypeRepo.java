package com.filipe.agricontrole.data.repo;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.filipe.agricontrole.data.DatabaseManager;
import com.filipe.agricontrole.data.model.UnitType;

import java.util.ArrayList;
import java.util.List;

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

    public List<UnitType> findAll(){
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        Cursor c = db.query(UnitType.TABLE, new String[] {}, null, null, null, null, null);

        List<UnitType> unitTypeList = new ArrayList<>();
        if(c.moveToFirst()){
            do{
                unitType = new UnitType();
                unitTypeList.add(unitType);

                unitType.setId(c.getInt(0));
                unitType.setName(c.getString(1));
            }while (c.moveToNext());
        }
        DatabaseManager.getInstance().closeDatabase();
        return unitTypeList;
    }

}
