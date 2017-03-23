package dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import model.Agronomist;

/**
 * Created by filipe on 22/03/17.
 */

public class AgronomistDAO {

    private DataBaseHelper dataBaseHelper;
    private SQLiteDatabase database;

    public AgronomistDAO(Context context){
        dataBaseHelper = new DataBaseHelper(context);
    }

    private SQLiteDatabase getDatabase(){
        if(database == null){
            database =  dataBaseHelper.getWritableDatabase();
        }

        return database;
    }

    private Agronomist create(Cursor cursor){
        Agronomist model = new Agronomist(
                cursor.getInt(cursor.getColumnIndex(DataBaseHelper.Agronomist.ID)),
                cursor.getString(cursor.getColumnIndex(DataBaseHelper.Agronomist.NAME)),
                cursor.getString(cursor.getColumnIndex(DataBaseHelper.Agronomist.SURENAME)),
                cursor.getString(cursor.getColumnIndex(DataBaseHelper.Agronomist.CELLPHONE)),
                cursor.getString(cursor.getColumnIndex(DataBaseHelper.Agronomist.EMAIL)),
                cursor.getString(cursor.getColumnIndex(DataBaseHelper.Agronomist.PASSWORD)),
                cursor.getString(cursor.getColumnIndex(DataBaseHelper.Agronomist.CREATED_AT))
        );

        return model;
    }

    public List<Agronomist> listAll(){
        Cursor cursor = getDatabase().query(DataBaseHelper.Agronomist.TABELA,
                                DataBaseHelper.Agronomist.COLUNAS, null, null, null, null, null);
        List<Agronomist> agronomists = new ArrayList<Agronomist>();
        while(cursor.moveToNext()){
            Agronomist model = createAgronomist(cursor);
            agronomists.add(model);
        }
        cursor.close();
        return agronomists;
    }

    public long save(Agronomist agronomist){
        ContentValues values = new ContentValues();
        values.put(DataBaseHelper.Agronomist.NAME, agronomist.getName());
        values.put(DataBaseHelper.Agronomist.SURENAME, agronomist.getSurename());
        values.put(DataBaseHelper.Agronomist.CELLPHONE, agronomist.getCellphone());
        values.put(DataBaseHelper.Agronomist.EMAIL, agronomist.getEmail());
        values.put(DataBaseHelper.Agronomist.PASSWORD, agronomist.getPassword());
        values.put(DataBaseHelper.Agronomist.CREATED_AT, agronomist.getCreated_at());

        if(agronomist.getId() != null){
            return getDatabase().update(DataBaseHelper.Agronomist.TABELA, values,
                    "id = ?", new String[]{ agronomist.getId().toString() });
        }

        return getDatabase().insert(DataBaseHelper.Agronomist.TABELA, null, values);
    }

    public boolean delete(int id){
        return getDatabase().delete(DataBaseHelper.Agronomist.TABELA,
                "id = ?", new String[]{ Integer.toString(id) }) > 0;
    }

    public Agronomist findById(int id){
        Cursor cursor = getDatabase().query(DataBaseHelper.Agronomist.TABELA,
                DataBaseHelper.Agronomist.COLUNAS, "id = ?", new String[]{ Integer.toString(id) }, null, null, null);

        if(cursor.moveToNext()){
            Agronomist model = create(cursor);
            cursor.close();
            return model;
        }

        return null;
    }

    public boolean login(String email, String password){
        Cursor cursor = getDatabase().query(DataBaseHelper.Agronomist.TABELA,
                null, "EMAIL = ? AND PASSWORD = ?", new String[]{email, password}, null,null,null);

        if(cursor.moveToFirst()){
            return true;
        }

        return false;
    }

    public void close(){
        databaseHelper.close();
        database = null;
    }


}
