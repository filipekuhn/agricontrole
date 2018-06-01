package com.filipe.agricontrole.data.repo;

import com.filipe.agricontrole.data.model.Period;
import com.filipe.agricontrole.data.model.Plot;

public class PlotRepo {

    private final String TAG = FarmRepo.class.getSimpleName().toString();

    private Plot plot;

    public PlotRepo(){
        plot = new Plot();
    }

    public static String createTable() {
        return "CREATE TABLE IF NOT EXISTS " + Plot.TABLE  + " ("
                + Plot.KEY_PlotId  + "  INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Plot.KEY_PeriodId + " INTEGER NOT NULL, "
                + Plot.KEY_Name + " TEXT NOT NULL, "
                + Plot.KEY_Area + " DOUBLE NOT NULL, "
                + "FOREIGN KEY(" + Plot.KEY_PeriodId + ") REFERENCES " + Period.TABLE + "(" + Period.KEY_PeriodId + ") ON DELETE CASCADE);";
    }


}
