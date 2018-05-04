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


    public static String createTable() {
        return "CREATE TABLE IF NOT EXISTS " + Agronomist.TABLE  + " ("
                + Agronomist.KEY_AgronomistId  + "  INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Agronomist.KEY_Name + " TEXT NOT NULL, "
                + Agronomist.KEY_SureName + " TEXT NOT NULL, "
                + Agronomist.KEY_CellPhone + " TEXT NOT NULL, "
                + Agronomist.KEY_Email + " TEXT NOT NULL UNIQUE, "
                + Agronomist.KEY_Password + " TEXT NOT NULL, "
                + Agronomist.KEY_CreatedAt + " TEXT NOT NULL " +
                ");";
    }

    public static String insertAdm() {
        return "INSERT INTO " + Agronomist.TABLE + " VALUES(1, 'Administrador', 'ADM', '(42)999387879', "
                + "'adm@agricontrole.com', 'abc123', datetime('now', 'localtime'));";
    }

    public int insert(Agronomist agronomist){
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

    public int update(Agronomist agronomist){
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        ContentValues values = new ContentValues();
        values.put(Agronomist.KEY_Name, agronomist.getName());
        values.put(Agronomist.KEY_SureName, agronomist.getSurename());
        values.put(Agronomist.KEY_CellPhone, agronomist.getCellphone());
        values.put(Agronomist.KEY_Email, agronomist.getEmail());
        values.put(Agronomist.KEY_Password, agronomist.getPassword());
        String id = String.valueOf(agronomist.getId());
        String where = Agronomist.KEY_AgronomistId + "=?";
        String[] whereArgs = new String[] {id};
        int count = db.update(Agronomist.TABLE, values, where, whereArgs);
        DatabaseManager.getInstance().closeDatabase();
        return count;
    }

    public Agronomist findByEmail(String email){
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        Cursor c = db.query(Agronomist.TABLE, new String[]{String.valueOf(Agronomist.KEY_AgronomistId) ,String.valueOf(Agronomist.KEY_Name), String.valueOf(Agronomist.KEY_SureName),
                String.valueOf(Agronomist.KEY_CellPhone), String.valueOf(Agronomist.KEY_Email), String.valueOf(Agronomist.KEY_Password)},
                Agronomist.KEY_Email + " = '" + email + "'", null, null, null, null, null);

        c.moveToFirst();
        Agronomist agronomist = new Agronomist();
        if(c.getCount() > 0) {
            agronomist.setId(c.getInt(0));
            agronomist.setName(c.getString(1));
            agronomist.setSurename(c.getString(2));
            agronomist.setCellphone(c.getString(3));
            agronomist.setEmail(c.getString(4));
            agronomist.setPassword(c.getString(5));

            return  agronomist;
        }

        return agronomist;
    }

    public void delete( ) {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        db.delete(Agronomist.TABLE,null,null);
        DatabaseManager.getInstance().closeDatabase();
    }

    public boolean login(String email, String password) {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        Cursor c = db.query(Agronomist.TABLE, new String[]{String.valueOf(Agronomist.KEY_Email), String.valueOf(Agronomist.KEY_Password)}, Agronomist.KEY_Email + " = '" + email + "'", null, null, null, null, null);

        System.out.println(c.getCount());
        if (c.getCount() > 0) {

            c.moveToFirst();

            System.out.println(c.getString(0));
            System.out.println(c.getString(1));
            if (email.equals(c.getString(0)) && password.equals(c.getString(1))) {
                DatabaseManager.getInstance().closeDatabase();
                return true;

            }
        }

        DatabaseManager.getInstance().closeDatabase();
        return false;
    }

    public String getCurrentUserName(String email){
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        Cursor c = db.query(Agronomist.TABLE, new String[]{String.valueOf(Agronomist.KEY_Name)}, Agronomist.KEY_Email + " = '" + email + "'", null, null, null, null, null);

        if(c.getCount() > 0){
            c.moveToFirst();

            return c.getString(0);
        }
        return "Usuário";
    }
}