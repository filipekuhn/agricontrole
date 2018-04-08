package com.filipe.agricontrole.data.repo;


import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.filipe.agricontrole.data.DatabaseManager;
import com.filipe.agricontrole.data.model.Agronomist;

public class AgronomistRepo  {

    private Agronomist agronomist;

    public AgronomistRepo(){

        agronomist = new Agronomist();

    }


    public static String createTable(){
        return "CREATE TABLE " + Agronomist.TABLE  + "("
                + Agronomist.KEY_AgronomistId  + "   PRIMARY KEY AUTOINCREMENT, "
                + Agronomist.KEY_Name + " VARCHAR(45) NOT NULL, "
                + Agronomist.KEY_SureName + "VARCHAR(45) NOT NULL, "
                + Agronomist.KEY_CellPhone + " VARCHAR(14) NOT NULL, "
                + Agronomist.KEY_Email + " VARCHAR(45) NOT NULL UNIQUE, "
                + Agronomist.KEY_Password + " VARCHAR(45) NOT NULL, "
                + Agronomist.KEY_CreatedAt+ " DATETIME);";
    }


    public int insert(Agronomist agronomist) {
        int agronomistId;
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        ContentValues values = new ContentValues();
        values.put(Agronomist.KEY_AgronomistId, agronomist.getId());
        values.put(Agronomist.KEY_Name, agronomist.getName());
        values.put(Agronomist.KEY_SureName, agronomist.getSurename());
        values.put(Agronomist.KEY_CellPhone, agronomist.getCellphone());
        values.put(Agronomist.KEY_Email, agronomist.getEmail());
        values.put(Agronomist.KEY_Password, agronomist.getPassword());
        values.put(Agronomist.KEY_CreatedAt, agronomist.getCreated_at());


        // Inserting Row
        agronomistId=(int)db.insert(Agronomist.TABLE, null, values);
        DatabaseManager.getInstance().closeDatabase();

        return agronomistId;
    }


    /*
    public void delete( ) {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        db.delete(Course.TABLE,null,null);
        DatabaseManager.getInstance().closeDatabase();
    }*/
}