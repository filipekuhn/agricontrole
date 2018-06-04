package com.filipe.agricontrole.data.repo;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.filipe.agricontrole.data.DatabaseManager;
import com.filipe.agricontrole.data.model.Category;
import com.filipe.agricontrole.data.model.Planting;
import com.filipe.agricontrole.data.model.Product;
import com.filipe.agricontrole.data.model.ProductApplication;
import com.filipe.agricontrole.data.model.UnitType;

import java.util.ArrayList;
import java.util.List;

public class ProductApplicationRepo {
    private final String TAG = FarmRepo.class.getSimpleName().toString();

    private ProductApplication productApplication;
    private Product product;
    private Planting planting;
    private Category category;
    private UnitType unitType;


    public ProductApplicationRepo(){
        productApplication = new ProductApplication();
        product = new Product();
        planting = new Planting();
        category = new Category();
        unitType = new UnitType();
    }

    public static String createTable() {
        return "CREATE TABLE IF NOT EXISTS " + ProductApplication.TABLE  + " ("
                + ProductApplication.KEY_ProductApplicationId  + "  INTEGER PRIMARY KEY AUTOINCREMENT, "
                + ProductApplication.KEY_ProductId + " INTEGER NOT NULL, "
                + ProductApplication.KEY_PlantingId + " INTEGER NOT NULL, "
                + ProductApplication.KEY_Quantity + " DOUBLE NOT NULL, "
                + ProductApplication.KEY_Date + " TEXT NOT NULL, "
                + "FOREIGN KEY(" + ProductApplication.KEY_ProductId + ") REFERENCES " + Product.TABLE + "(" + Product.KEY_ProductId + ") ON DELETE CASCADE,"
                + "FOREIGN KEY(" + ProductApplication.KEY_PlantingId + ") REFERENCES " + Planting.TABLE + "(" + Planting.KEY_PlantingId + ") ON DELETE CASCADE);";
    }

    public static String initialProductApplication(){
        return "INSERT INTO " + ProductApplication.TABLE + " VALUES(1, 1, 1, 0.01, '02/06/2018');";
    }

    public List<ProductApplication> findAllByPlantingId(int plantingId){
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        String query = "SELECT pa.id, pa.quantity, pa.date, p.id, p.name,p.quantity, p.expiration_date," +
                " c.id, c.name, ut.id, ut.name FROM product_application AS pa INNER JOIN product AS p ON pa.product_id = p.id INNER JOIN category AS c" +
                " ON p.category_id = c.id INNER JOIN unit_type as ut ON c.unit_type_id = ut.id WHERE pa.planting_id = ?";
        Cursor c = db.rawQuery(query, new String[] {String.valueOf(plantingId)});

        List<ProductApplication> productApplicationList = new ArrayList<>();
        if(c.moveToFirst()){
            do{
                productApplication = new ProductApplication();
                product = new Product();
                planting = new Planting();
                category = new Category();
                unitType = new UnitType();

                productApplicationList.add(productApplication);

                productApplication.setId(c.getInt(0));
                productApplication.setQuantity(c.getDouble(1));
                productApplication.setDate(c.getString(2));
                product.setId(c.getInt(3));
                product.setName(c.getString(4));
                product.setQuantity(c.getDouble(5));
                product.setExpiration_date(c.getString(6));
                category.setId(c.getInt(7));
                category.setName(c.getString(8));
                unitType.setId(c.getInt(9));
                unitType.setName(c.getString(10));
                planting.setId(plantingId);

                category.setUnitType(unitType);
                product.setCategory(category);
                productApplication.setProduct(product);
                productApplication.setPlanting(planting);
            }while (c.moveToNext());
        }
        return productApplicationList;
    }
}
