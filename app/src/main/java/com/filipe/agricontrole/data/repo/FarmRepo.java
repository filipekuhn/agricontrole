package com.filipe.agricontrole.data.repo;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.filipe.agricontrole.data.DatabaseManager;
import com.filipe.agricontrole.data.model.Agronomist;
import com.filipe.agricontrole.data.model.City;
import com.filipe.agricontrole.data.model.Farm;
import com.filipe.agricontrole.data.model.State;

import java.util.ArrayList;
import java.util.List;

public class FarmRepo {

    private final String TAG = FarmRepo.class.getSimpleName().toString();

    private Farm farm;

    public FarmRepo(){
        farm = new Farm();
    }

    public static String createTable() {
        return "CREATE TABLE IF NOT EXISTS " + Farm.TABLE  + " ("
                + Farm.KEY_FarmId  + "  INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Farm.KEY_AgronomisId + " INTEGER NOT NULL, "
                + Farm.KEY_Name + " TEXT NOT NULL, "
                + Farm.KEY_Owner + " TEXT NOT NULL, "
                + Farm.KEY_Address + " TEXT NOT NULL, "
                + Farm.KEY_City + " INTEGER NOT NULL, "
                + "FOREIGN KEY(" + Farm.KEY_AgronomisId + ") REFERENCES " + Agronomist.TABLE + "(" + Agronomist.KEY_AgronomistId + "), "
                + "FOREIGN KEY(" + Farm.KEY_City + ") REFERENCES " + City.TABLE + "(" + City.KEY_CityId + "));";
    }

    public int insert(Farm farm){
        int farmId;
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        ContentValues values = new ContentValues();
        values.put(Farm.KEY_FarmId, farm.getId());
        values.put(Farm.KEY_AgronomisId, farm.getAgronomistId());
        values.put(Farm.KEY_Name, farm.getName());
        values.put(Farm.KEY_Owner, farm.getOwner());
        values.put(Farm.KEY_Address, farm.getAddress());
        values.put(Farm.KEY_City, farm.getCity().getId());

        // Inserting Row
        farmId=(int)db.insert(Farm.TABLE, null, values);
        DatabaseManager.getInstance().closeDatabase();

        return farmId;
    }

    public boolean delete(int id) {
        try{
            SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
            String query = "DELETE FROM farm WHERE id = ?";
            db.delete(Farm.TABLE, Farm.KEY_FarmId + "=" + id, null);

            return true;

        }catch (Exception e) {
            Log.d("Erro ao deletar Fazenda", e.toString());
            return false;
        }finally {
            DatabaseManager.getInstance().closeDatabase();
        }
    }

    public static String insertTestFarm(){
        return "INSERT INTO " + Farm.TABLE + " VALUES(1, 1, 'Fazenda Rio Bonito', 'Romildo Bolzan','Linha Morangaba', 1);";
    }

    public static String insertSecondFarm(){
        return "INSERT INTO " + Farm.TABLE + " VALUES(2, 1, 'Fazenda Nova Esperança', 'Renato Portaluppi','Linha Nova Baixada', 2);";
    }


    public List<Farm> findAllByEmail(String email){
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        //Cursor c = db.query(Farm.TABLE, new String[] {}, null, null, null, null, null);
        String query = "SELECT f.id, f.agronomist_id, f.name, f.owner, f.address, c.id, c.name, c.state_id, s.id, s.name, s.uf FROM agronomist AS a INNER JOIN farm AS f ON f.agronomist_id = a.id INNER JOIN city as c ON f.city = c.id INNER JOIN state AS s ON c.state_id = s.id WHERE a.email = ?";
        Cursor c = db.rawQuery(query, new String[] {String.valueOf(email)});

        List<Farm> farmList = new ArrayList<>();
        if(c.moveToFirst()){
            do{
                Farm farm = new Farm();
                City city = new City();
                State state = new State();
                farmList.add(farm);


                farm.setId(c.getInt(0));
                farm.setAgronomistId(c.getInt(1));
                farm.setName(c.getString(2));
                farm.setOwner(c.getString(3));
                farm.setAddress(c.getString(4));
                city.setId(c.getInt(5));
                city.setName(c.getString(6));
                city.setState_id(c.getInt(7));
                state.setId(c.getInt(8));
                state.setName(c.getString(9));
                state.setUf(c.getString(10));

                farm.setCity(city);
                farm.setState(state);

            }while (c.moveToNext());
        }
        DatabaseManager.getInstance().closeDatabase();
        return farmList;
    }


}
