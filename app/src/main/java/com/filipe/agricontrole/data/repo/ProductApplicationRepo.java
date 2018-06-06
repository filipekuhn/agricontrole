package com.filipe.agricontrole.data.repo;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

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

    public int insert(ProductApplication productApplication, Double finalQuantity){
        if(updateQuantity(finalQuantity, productApplication.getProduct().getId())){
            int productApplicationId;
            SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
            ContentValues values = new ContentValues();
            values.put(ProductApplication.KEY_ProductId, productApplication.getProduct().getId());
            values.put(ProductApplication.KEY_PlantingId, productApplication.getPlanting().getId());
            values.put(ProductApplication.KEY_Quantity, productApplication.getQuantity());
            values.put(ProductApplication.KEY_Date, productApplication.getDate());

            // Inserting Row
            productApplicationId=(int)db.insert(ProductApplication.TABLE, null, values);
            DatabaseManager.getInstance().closeDatabase();

            return productApplicationId;
        }
        return 0;
    }

    public boolean delete(int id,int productId ,Double quantity){
        try{
            updateQuantity(quantity, productId);//update the product quantity if a application is created, deleted or updated.

            SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
            db.delete(ProductApplication.TABLE, ProductApplication.KEY_ProductApplicationId + "=" + id, null);

            return true;
        }catch (Exception e) {
            Log.d("Erro ao deletar Fazenda", e.toString());
            return false;
        }finally {
            DatabaseManager.getInstance().closeDatabase();
        }
    }

    public int update(ProductApplication productApplication, Double quantity){

        if(updateQuantity(quantity, productApplication.getProduct().getId())){
            SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
            ContentValues values = new ContentValues();
            values.put(ProductApplication.KEY_ProductId, productApplication.getProduct().getId());
            values.put(ProductApplication.KEY_PlantingId, productApplication.getPlanting().getId());
            values.put(ProductApplication.KEY_Quantity, productApplication.getQuantity());
            values.put(ProductApplication.KEY_Date, productApplication.getDate());
            String id = String.valueOf(productApplication.getId());
            String where = ProductApplication.KEY_ProductApplicationId + "=?";
            String[] whereArgs = new String[] {id};
            int count = db.update(ProductApplication.TABLE, values, where, whereArgs);
            DatabaseManager.getInstance().closeDatabase();
            return count;
        }

        return 0;
    }

    private boolean updateQuantity(Double quantity, int productId){
        try{
            SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
            String query = "UPDATE product SET quantity = " + quantity + " WHERE id = " + productId + ";"; //Set the product quantity the
            db.execSQL(query);                                                                         //result of the old quantity minus the quantity applicated

            return true;
        }catch(Exception e){
            Log.d("Erro ao deletar Produto", e.toString());
            return false;
        }finally {
            DatabaseManager.getInstance().closeDatabase();
        }
    }

    public ProductApplication findById(int id){
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        String query = "SELECT * FROM " + ProductApplication.TABLE + " WHERE id = ?";
        Cursor c = db.rawQuery(query, new String[] {String.valueOf(id)});


        if(c.getCount() > 0){
            c.moveToFirst();
            productApplication = new ProductApplication();
            product = new Product();
            planting = new Planting();
            category = new Category();
            unitType = new UnitType();

            productApplication.setId(c.getInt(0));
            product.setId(c.getInt(1));
            planting.setId(c.getInt(2));
            productApplication.setQuantity(c.getDouble(3));
            productApplication.setDate(c.getString(4));

            productApplication.setProduct(product);
            productApplication.setPlanting(planting);

            return productApplication;
        }

        return productApplication;
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
