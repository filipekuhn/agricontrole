package dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by filipe on 22/03/17.
 */

public class DataBaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE = "agricontrole";
    private static final int VERSION = 1;

    public DataBaseHelper(Context context){
        super(context, DATABASE, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //agronomist table
        db.execSQL("create table agronomist(id integer primary key autoincrement, "
                +"name varchar(45) not null, surename varchar(45), cellphone varchar (14) not null," +
                "email varchar(45) not null unique, password varchar(45) not null, created_at datetime)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public static class Agronomist{
        public static final String TABELA = "agronomist";
        public static final String ID = "id";
        public static final String NAME = "name";
        public static final String SURENAME = "surename";
        public static final String CELLPHONE = "cellphone";
        public static final String EMAIL = "email";
        public static final String PASSWORD = "password";
        public static final String CREATED_AT = "created_at";

        public static final String[] COLUNAS = new String[]{
                ID, NAME, SURENAME, CELLPHONE,EMAIL, PASSWORD, CREATED_AT
        };
    }
}
