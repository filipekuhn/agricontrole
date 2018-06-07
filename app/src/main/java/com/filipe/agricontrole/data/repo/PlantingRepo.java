package com.filipe.agricontrole.data.repo;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.filipe.agricontrole.data.DatabaseManager;
import com.filipe.agricontrole.data.model.Planting;
import com.filipe.agricontrole.data.model.Plot;

import java.util.ArrayList;
import java.util.List;

public class PlantingRepo {

    private final String TAG = FarmRepo.class.getSimpleName().toString();

    private Planting planting;
    private Plot plot;

    public PlantingRepo(){
        planting = new Planting();
        plot = new Plot();
    }

    public static String createTable() {
        return "CREATE TABLE IF NOT EXISTS " + Planting.TABLE  + " ("
                + Planting.KEY_PlantingId  + "  INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Planting.KEY_PlotId + " INTEGER NOT NULL, "
                + Planting.KEY_PlantingType + " TEXT NOT NULL, "
                + Planting.KEY_PlantingDate + " TEXT NOT NULL, "
                + Planting.KEY_Population + " DOUBLE, "
                + Planting.KEY_EmergencyDate + " TEXT, "
                + Planting.KEY_HarvestDate + " TEXT, "
                + "FOREIGN KEY(" + Planting.KEY_PlotId + ") REFERENCES " + Plot.TABLE + "(" + Plot.KEY_PlotId + ") ON DELETE CASCADE);";
    }

    public static String insertInitialPlanting(){
        return "INSERT INTO " + Planting.TABLE + " (id, plot_id, type, date) VALUES (1, 1, 'Soja', '01/06/2018');";
    }
    public int insert(Planting planting){
        int plantingId;
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        ContentValues values = new ContentValues();
        values.put(Planting.KEY_PlotId, planting.getPlot().getId());
        values.put(Planting.KEY_PlantingType, planting.getType());
        values.put(Planting.KEY_PlantingDate, planting.getPlantingDate());
        values.put(Planting.KEY_EmergencyDate, planting.getEmergencyDate());
        values.put(Planting.KEY_HarvestDate, planting.getHarvestDate());

        if(planting.getPopulation() != 0.0)
            values.put(Planting.KEY_Population, planting.getPopulation());

        // Inserting Row
        plantingId=(int)db.insert(Planting.TABLE, null, values);
        DatabaseManager.getInstance().closeDatabase();

        return plantingId;
    }

    public int update(Planting planting){
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        ContentValues values = new ContentValues();
        values.put(Planting.KEY_PlantingId, planting.getId());
        values.put(Planting.KEY_PlotId, planting.getPlot().getId());
        values.put(Planting.KEY_PlantingType, planting.getType());
        values.put(Planting.KEY_PlantingDate, planting.getPlantingDate());
        values.put(Planting.KEY_EmergencyDate, planting.getEmergencyDate());
        values.put(Planting.KEY_HarvestDate, planting.getHarvestDate());

        if(planting.getPopulation() != 0.0)
            values.put(Planting.KEY_Population, planting.getPopulation());

        String id = String.valueOf(planting.getId());
        String where = Planting.KEY_PlantingId + "=?";
        String[] whereArgs = new String[] {id};
        int count = db.update(Planting.TABLE, values, where, whereArgs);
        DatabaseManager.getInstance().closeDatabase();
        return count;
    }


    public boolean delete(int id) {
        try{
            SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
            String pragma = "PRAGMA foreign_keys = ON;"; //SQLite need to be enable to exclude ON CASCADE
            db.execSQL(pragma); //Enable to exclude ON CASCADE
            db.delete(Planting.TABLE, Planting.KEY_PlantingId + "=" + id, null);

            return true;
        }catch (Exception e) {
            Log.d("Erro ao deletar Plantio", e.toString());
            return false;
        }finally {
            DatabaseManager.getInstance().closeDatabase();
        }
    }

    public Planting findById(int id){
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        String query = "SELECT * FROM planting WHERE id = ?";
        Cursor c =db.rawQuery(query, new String[] {String.valueOf(id)});

        if(c.getCount() > 0){
            c.moveToFirst();

            planting = new Planting();
            plot = new Plot();

            planting.setId(c.getInt(0));
            plot.setId(c.getInt(1));
            planting.setType(c.getString(2));
            planting.setPlantingDate(c.getString(3));
            planting.setPopulation(c.getDouble(4));
            planting.setEmergencyDate(c.getString(5));
            planting.setHarvestDate(c.getString(6));
            planting.setPlot(plot);

            DatabaseManager.getInstance().closeDatabase();
            return planting;
        }
        DatabaseManager.getInstance().closeDatabase();
        return planting;
    }

    public List<Planting> findAllByPlotId(int plotId){
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        String query = "SELECT p.id, p.type,p.date, p.population, p.emergency_date, p.harvest_date," +
                " pl.id, pl.name, pl.area FROM planting AS p INNER JOIN plot AS pl ON p.plot_id = pl.id WHERE p.plot_id = ?";
        Cursor c = db.rawQuery(query, new String[] {String.valueOf(plotId)});

        List<Planting> plantingList = new ArrayList<>();
        if(c.moveToFirst()){
            do{
                planting = new Planting();
                plot = new Plot();
                plantingList.add(planting);

                planting.setId(c.getInt(0));
                planting.setType(c.getString(1));
                planting.setPlantingDate(c.getString(2));
                planting.setPopulation((c.getDouble(3)));
                planting.setEmergencyDate(c.getString(4));
                planting.setHarvestDate(c.getString(5));
                plot.setId(c.getInt(6));
                plot.setName(c.getString(7));
                plot.setArea(c.getDouble(8));

                planting.setPlot(plot);
            }while (c.moveToNext());
        }
        return plantingList;
    }
}
