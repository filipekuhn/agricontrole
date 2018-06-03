package com.filipe.agricontrole.data.repo;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.filipe.agricontrole.data.DatabaseManager;
import com.filipe.agricontrole.data.model.Category;
import com.filipe.agricontrole.data.model.UnitType;

import java.util.ArrayList;
import java.util.List;

public class CategoryRepo {
    private final String TAG = StateRepo.class.getSimpleName().toString();

    private Category category;
    private UnitType unitType;

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

    public List<Category> findAll(){
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        Cursor c = db.query(Category.TABLE, new String[] {}, null, null, null, null, null);

        List<Category> categoryList = new ArrayList<>();
        if(c.moveToFirst()){
            do{
                category = new Category();
                unitType = new UnitType();
                categoryList.add(category);

                category.setId(c.getInt(0));
                category.setName(c.getString(1));
                unitType.setId(c.getInt(2));

                category.setUnitType(unitType);
            }while (c.moveToNext());
        }
        DatabaseManager.getInstance().closeDatabase();
        return categoryList;
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
