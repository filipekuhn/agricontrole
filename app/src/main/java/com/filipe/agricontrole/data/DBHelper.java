package com.filipe.agricontrole.data;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.filipe.agricontrole.app.App;
import com.filipe.agricontrole.data.repo.AgronomistRepo;
import com.filipe.agricontrole.data.repo.CategoryRepo;
import com.filipe.agricontrole.data.repo.CityRepo;
import com.filipe.agricontrole.data.repo.FarmRepo;
import com.filipe.agricontrole.data.repo.PeriodRepo;
import com.filipe.agricontrole.data.repo.PlantingRepo;
import com.filipe.agricontrole.data.repo.PlotRepo;
import com.filipe.agricontrole.data.repo.ProductRepo;
import com.filipe.agricontrole.data.repo.StateRepo;
import com.filipe.agricontrole.data.repo.StockRepo;
import com.filipe.agricontrole.data.repo.UnitTypeRepo;

public class DBHelper  extends SQLiteOpenHelper {
    //version number to upgrade database version
    //each time if you Add, Edit table, you need to change the
    //version number.
    private static final int DATABASE_VERSION =1;

    private static final String DATABASE_NAME = "agricontrole.db"; // Database Name
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
        db.execSQL(StateRepo.createTable());
        db.execSQL(StateRepo.insertStates());
        db.execSQL(CityRepo.createTable());
        db.execSQL(CityRepo.insertCities());
        db.execSQL(CityRepo.insertCities2());
        db.execSQL(CityRepo.insertCities3());
        db.execSQL(CityRepo.insertCities4());
        db.execSQL(StockRepo.createTable());
        db.execSQL(StockRepo.insertStock());
        db.execSQL(PeriodRepo.createTable());
        db.execSQL(PeriodRepo.insertPeriod());
        db.execSQL(PlotRepo.createTable());
        db.execSQL(PlotRepo.insertPlot());
        db.execSQL(PlantingRepo.createTable());
        db.execSQL(UnitTypeRepo.createTable());
        db.execSQL(UnitTypeRepo.initialUnitType());
        db.execSQL(UnitTypeRepo.initalUnitType2());
        db.execSQL(CategoryRepo.createTable());
        db.execSQL(CategoryRepo.initialCategory());
        db.execSQL(CategoryRepo.initialCategory2());
        db.execSQL(CategoryRepo.initialCategory3());
        db.execSQL(CategoryRepo.initialCategory4());
        db.execSQL(ProductRepo.createTable());
        db.execSQL(ProductRepo.insertInitialProduct());

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(TAG, String.format("SQLiteDatabase.onUpgrade(%d -> %d)", oldVersion, newVersion));

        // Drop table if existed, all data will be gone

    }

}
