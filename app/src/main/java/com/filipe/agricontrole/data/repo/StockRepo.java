package com.filipe.agricontrole.data.repo;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.filipe.agricontrole.data.DatabaseManager;
import com.filipe.agricontrole.data.model.Farm;
import com.filipe.agricontrole.data.model.Stock;

import java.util.ArrayList;
import java.util.List;

public class StockRepo {

    private final String TAG = FarmRepo.class.getSimpleName().toString();

    private Stock stock;

    public StockRepo(){
        stock = new Stock();
    }

    public static String createTable() {
        return "CREATE TABLE IF NOT EXISTS " + Stock.TABLE  + " ("
                + Stock.KEY_StockId  + "  INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Stock.KEY_FarmId + " INTEGER NOT NULL, "
                + "FOREIGN KEY(" + Stock.KEY_FarmId + ") REFERENCES " + Farm.TABLE + "(" + Farm.KEY_FarmId + ") ON DELETE CASCADE);";
    }

    public static String insertStock(){
        return "INSERT INTO stock (id, farm_id) VALUES (1, 1);";
    }

    public int insert(Stock stock){
        int stockId;
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        ContentValues values = new ContentValues();
        values.put(Stock.KEY_StockId, stock.getId());
        values.put(Stock.KEY_FarmId, stock.getFarmId());

        // Inserting Row
        stockId=(int)db.insert(Stock.TABLE, null, values);
        DatabaseManager.getInstance().closeDatabase();

        return stockId;
    }

    public List<Stock> findAllByFarmId(int farm){
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        //Cursor c = db.query(Farm.TABLE, new String[] {}, null, null, null, null, null);
        String query = "SELECT * FROM stock WHERE farm_id = ?";
        Cursor c = db.rawQuery(query, new String[] {String.valueOf(farm)});

        List<Stock> stockList = new ArrayList<>();
        if(c.moveToFirst()){
            do{
                Stock stock = new Stock();
                stockList.add(stock);

                stock.setId(c.getInt(0));
                stock.setFarmId(c.getInt(1));


            }while (c.moveToNext());
        }
        return stockList;
    }
}
