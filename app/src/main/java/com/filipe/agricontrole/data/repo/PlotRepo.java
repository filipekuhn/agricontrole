package com.filipe.agricontrole.data.repo;

import com.filipe.agricontrole.data.model.Farm;
import com.filipe.agricontrole.data.model.Plot;

public class PlotRepo {

    private final String TAG = FarmRepo.class.getSimpleName().toString();

    //private Farm farm;
    private Plot plot;

    public PlotRepo(){
        plot = new Plot();
        //farm = new Farm();
    }

    public static String createTable() {
        return "CREATE TABLE IF NOT EXISTS " + Plot.TABLE  + " ("
                + Plot.KEY_PlotId  + "  INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Plot.KEY_FarmId + " INTEGER NOT NULL, "
                + Plot.KEY_Name + " TEXT NOT NULL, "
                + Plot.KEY_Area + " DOUBLE NOT NULL, "
                + "FOREIGN KEY(" + Plot.KEY_FarmId + ") REFERENCES " + Farm.TABLE + "(" + Farm.KEY_FarmId + "));";
    }


}
