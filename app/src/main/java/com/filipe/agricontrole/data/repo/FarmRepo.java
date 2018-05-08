package com.filipe.agricontrole.data.repo;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.filipe.agricontrole.data.DatabaseManager;
import com.filipe.agricontrole.data.model.Agronomist;
import com.filipe.agricontrole.data.model.Farm;

import java.util.ArrayList;
import java.util.List;

public class FarmRepo {

    private final String TAG = FarmRepo.class.getSimpleName().toString();

    private Farm farm;
    private Agronomist agronomist;

    public FarmRepo(){
        farm = new Farm();
        agronomist = new Agronomist();
    }

    public static String createTable() {
        return "CREATE TABLE IF NOT EXISTS " + Farm.TABLE  + " ("
                + Farm.KEY_FarmId  + "  INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Farm.KEY_AgronomisId + " INTEGER NOT NULL, "
                + Farm.KEY_Name + " TEXT NOT NULL, "
                + Farm.KEY_Address + " TEXT NOT NULL, "
                + Farm.KEY_City + " INTEGER NOT NULL, "
                + Farm.KEY_State + " INTEGER NOT NULL, "
                + "FOREIGN KEY(" + Farm.KEY_AgronomisId + ") REFERENCES " + Agronomist.TABLE + "(" + Agronomist.KEY_AgronomistId + ")"
                + ");";
    }

    public static String insertTestFarm(){
        return "INSERT INTO " + Farm.TABLE + " VALUES(1, 1, 'Fazenda Rio Bonito', 'Linha Morangaba', 1, 1); " +
                "INSERT INTO " + Farm.TABLE + " VALUES(2, 1, 'Fazenda Nova Esperança', 'Linha Nova Baixada', 2,1); " +
                "INSERT INTO " + Farm.TABLE + " VALUES(3, 1, 'Fazenda Boi Com a Corda', 'Linha Velha', 3,1); ";
    }

    public static String insertSecondFarm(){
        return "INSERT INTO " + Farm.TABLE + " VALUES(2, 1, 'Fazenda Nova Esperança', 'Linha Nova Baixada', 2,1);";
    }


    public List<Farm> findAll(){
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        Cursor c = db.query(Farm.TABLE, new String[] {}, null, null, null, null, null);

        List<Farm> farmList = new ArrayList<>();
        if(c.moveToFirst()){
            do{
                Farm farm = new Farm();
                farmList.add(farm);

                farm.setId(c.getInt(0));
                farm.setAgronomistId(c.getInt(1));
                farm.setName(c.getString(2));
                farm.setAddress(c.getString(3));
                farm.setCity(c.getString(4));
                farm.setState(c.getString(5));
            }while (c.moveToNext());
        }
        return farmList;
    }


}
