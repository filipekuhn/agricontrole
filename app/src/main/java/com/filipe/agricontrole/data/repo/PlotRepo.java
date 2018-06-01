package com.filipe.agricontrole.data.repo;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.filipe.agricontrole.data.DatabaseManager;
import com.filipe.agricontrole.data.model.Period;
import com.filipe.agricontrole.data.model.Plot;

import java.util.ArrayList;
import java.util.List;

public class PlotRepo {

    private final String TAG = FarmRepo.class.getSimpleName().toString();

    private Plot plot;
    private Period period;

    public PlotRepo(){
        plot = new Plot();
    }

    public static String createTable() {
        return "CREATE TABLE IF NOT EXISTS " + Plot.TABLE  + " ("
                + Plot.KEY_PlotId  + "  INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Plot.KEY_PeriodId + " INTEGER NOT NULL, "
                + Plot.KEY_Name + " TEXT NOT NULL, "
                + Plot.KEY_Area + " DOUBLE NOT NULL, "
                + "FOREIGN KEY(" + Plot.KEY_PeriodId + ") REFERENCES " + Period.TABLE + "(" + Period.KEY_PeriodId + ") ON DELETE CASCADE);";
    }

    public static String insertPlot(){
        return "INSERT INTO " + Plot.TABLE + " VALUES (1, 1, 'Talhão A', 23.30);";
    }

    public int insert(Plot plot){
        int plotId;
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        ContentValues values = new ContentValues();
        //values.put(Plot.KEY_PlotId, plot.getId());
        values.put(Plot.KEY_Name, plot.getName());
        values.put(Plot.KEY_Area, plot.getArea());
        values.put(Plot.KEY_PeriodId, plot.getPeriodId().getId());

        System.out.println(values);
        // Inserting Row
        plotId=(int)db.insert(Plot.TABLE, null, values);
        System.out.println(plotId);
        DatabaseManager.getInstance().closeDatabase();

        return plotId;
    }

    public boolean delete(int id) {
        try{
            SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
            //String query = "DELETE FROM farm WHERE id = ?";
            String pragma = "PRAGMA foreign_keys = ON;"; //SQLite need to be enable to exclude ON CASCADE
            db.execSQL(pragma); //Enable to exclude ON CASCADE
            db.delete(Plot.TABLE, Plot.KEY_PlotId + "=" + id, null);

            return true;
        }catch (Exception e) {
            Log.d("Erro ao deletar Talhão", e.toString());
            return false;
        }finally {
            DatabaseManager.getInstance().closeDatabase();
        }
    }

    public List<Plot> findAllByPeriodId(int periodId){
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        //Cursor c = db.query(Farm.TABLE, new String[] {}, null, null, null, null, null);
        String query = "SELECT * FROM plot WHERE period_id = ? ORDER BY id ASC";
        Cursor c = db.rawQuery(query, new String[] {String.valueOf(periodId)});

        List<Plot> plotList = new ArrayList<>();
        if(c.moveToFirst()){
            do{
                plot = new Plot();
                period = new Period();

                plotList.add(plot);

                plot.setId(c.getInt(0));
                period.setId(c.getInt(1));
                plot.setName(c.getString(2));
                plot.setArea(c.getDouble(3));

                plot.setPeriodId(period);
            }while (c.moveToNext());
        }
        return plotList;
    }

}
