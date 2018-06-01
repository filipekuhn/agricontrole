package com.filipe.agricontrole.data.repo;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.filipe.agricontrole.data.DatabaseManager;
import com.filipe.agricontrole.data.model.Farm;
import com.filipe.agricontrole.data.model.Period;

import java.util.ArrayList;
import java.util.List;

public class PeriodRepo {

    private final String TAG = FarmRepo.class.getSimpleName().toString();

    private Period period;

    public PeriodRepo(){
        period = new Period();
    }

    public static String createTable() {
        return "CREATE TABLE IF NOT EXISTS " + Period.TABLE  + " ("
                + Period.KEY_PeriodId  + "  INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Period.KEY_PeriodName + " TEXT NOT NULL, "
                + Period.KEY_FarmId + " INTEGER NOT NULL, "
                + "FOREIGN KEY(" + Period.KEY_FarmId + ") REFERENCES " + Farm.TABLE + "(" + Farm.KEY_FarmId + ") ON DELETE CASCADE);";
    }

    public static String insertPeriod(){
        return "INSERT INTO period (id, name, farm_id) VALUES (1, '2016-2017', 1);";
    }

    public int insert(Period period){
        int periodId;
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        ContentValues values = new ContentValues();
        values.put(Period.KEY_PeriodId, period.getId());
        values.put(Period.KEY_PeriodName, period.getName());
        values.put(Period.KEY_FarmId, period.getFarmId());


        // Inserting Row
        periodId=(int)db.insert(Period.TABLE, null, values);
        DatabaseManager.getInstance().closeDatabase();

        return periodId;
    }

    public void delete(int id) {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        String query = "DELETE FROM period WHERE id = ?";
        db.rawQuery(query, new String[] {String.valueOf(id)});
        DatabaseManager.getInstance().closeDatabase();
    }


    public List<Period> findAllByFarmId(int farm){
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        //Cursor c = db.query(Farm.TABLE, new String[] {}, null, null, null, null, null);
        String query = "SELECT * FROM period WHERE farm_id = ?";
        Cursor c = db.rawQuery(query, new String[] {String.valueOf(farm)});

        List<Period> periodList = new ArrayList<>();
        if(c.moveToFirst()){
            do{
                Period period = new Period();
                periodList.add(period);

                period.setId(c.getInt(0));
                period.setName(c.getString(1));
                period.setFarmId(c.getInt(2));


            }while (c.moveToNext());
        }
        return periodList;
    }
}
