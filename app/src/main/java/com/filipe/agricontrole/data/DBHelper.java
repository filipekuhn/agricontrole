package com.filipe.agricontrole.data;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.filipe.agricontrole.app.App;
import com.filipe.agricontrole.data.repo.AgronomistRepo;
import com.filipe.agricontrole.data.repo.FarmRepo;

public class DBHelper  extends SQLiteOpenHelper {
    //version number to upgrade database version
    //each time if you Add, Edit table, you need to change the
    //version number.
    private static final int DATABASE_VERSION =1;
    // Database Name
    private static final String DATABASE_NAME = "agricontrole.db";
    private static final String TAG = DBHelper.class.getSimpleName();

    public DBHelper( ) {
        super(App.getContext(), DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //All necessary tables you like to create will create here
        db.execSQL(AgronomistRepo.createTable());
        db.execSQL(AgronomistRepo.insertAdm());
        db.execSQL(FarmRepo.createTable());
        db.execSQL(FarmRepo.insertTestFarm());
        db.execSQL(FarmRepo.insertSecondFarm());
        System.out.println(FarmRepo.insertTestFarm());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(TAG, String.format("SQLiteDatabase.onUpgrade(%d -> %d)", oldVersion, newVersion));

        // Drop table if existed, all data will be gone!!!

    }

}
