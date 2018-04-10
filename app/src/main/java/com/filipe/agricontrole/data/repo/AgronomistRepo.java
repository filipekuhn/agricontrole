package com.filipe.agricontrole.data.repo;


import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.filipe.agricontrole.data.DatabaseManager;
import com.filipe.agricontrole.data.model.Agronomist;

public class AgronomistRepo  {

    private Agronomist agronomist;

    public AgronomistRepo(){

        agronomist = new Agronomist();

    }


    public static String createTable()
    {
        return "CREATE TABLE IF NOT EXISTS '" + Agronomist.TABLE  + "' ('"
                + Agronomist.KEY_AgronomistId  + "'  INTEGER PRIMARY KEY AUTOINCREMENT, '"
                + Agronomist.KEY_Name + "' TEXT NOT NULL, '"
                + Agronomist.KEY_SureName + "' TEXT NOT NULL, '"
                + Agronomist.KEY_CellPhone + "' TEXT NOT NULL, '"
                + Agronomist.KEY_Email + "' TEXT NOT NULL UNIQUE, '"
                + Agronomist.KEY_Password + "' TEXT NOT NULL, '"
                + Agronomist.KEY_CreatedAt + "' TEXT NOT NULL " +
                ");";
    }

    public static String insertAdm()
    {
        return "INSERT INTO " + Agronomist.TABLE + " VALUES(1, 'Administrador', 'ADM', '(42)999387879', "
                + "'adm@agricontrole.com', 'abc123', datetime('now', 'localtime'));";
    }

    public int insert(Agronomist agronomist)
    {
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



    public void delete( )
    {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        db.delete(Agronomist.TABLE,null,null);
        DatabaseManager.getInstance().closeDatabase();
    }

    public boolean login(String email, String password)
    {
        String[] emailSplit = email.split("[@]");

        if(emailSplit.length  > 1)
        {
            SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
            Cursor c = db.query(Agronomist.TABLE, new String[]{String.valueOf(Agronomist.KEY_Email), String.valueOf(Agronomist.KEY_Password)}, Agronomist.KEY_Email + " = '" + emailSplit[0] + "@" + emailSplit[1] + "';", null, null, null, null, null);

            if (c.getCount() > 0) {

                c.moveToFirst();

                if (email.equals(c.getString(0)) && password.equals(c.getString(1))) {
                    DatabaseManager.getInstance().closeDatabase();
                    return true;

                }
            }
        }
        DatabaseManager.getInstance().closeDatabase();
        return false;
    }
}