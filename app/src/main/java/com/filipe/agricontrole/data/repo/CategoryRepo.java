package com.filipe.agricontrole.data.repo;

import com.filipe.agricontrole.data.model.Category;
import com.filipe.agricontrole.data.model.UnitType;

public class CategoryRepo {
    private final String TAG = StateRepo.class.getSimpleName().toString();

    private Category category;

    public CategoryRepo(){
        category = new Category();
    }

    public static String createTable() {
        return "CREATE TABLE IF NOT EXISTS " + Category.TABLE  + " ("
                + Category.KEY_CategoryId  + "  INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Category.KEY_Name + " TEXT NOT NULL, "
                + Category.KEY_UnitType + " INTEGER NOT NULL,"
                + "FOREIGN KEY(" + Category.KEY_UnitType + ") REFERENCES " + UnitType.TABLE + "(" + UnitType.KEY_UnitTypeId + ") ON DELETE CASCADE);";
    }

    public static String initialCategory(){
        return "INSERT INTO " + Category.TABLE + " VALUES (1, 'Fungicida', 1);";
    }

    public static String initialCategory2(){
        return "INSERT INTO " + Category.TABLE + " VALUES (2, 'Herbicida', 1);";
    }

    public static String initialCategory3(){
        return "INSERT INTO " + Category.TABLE + " VALUES (3, 'Calcário', 2);";
    }

    public static String initialCategory4(){
        return "INSERT INTO " + Category.TABLE + " VALUES (4, 'Inseticida', 1);";
    }
}
