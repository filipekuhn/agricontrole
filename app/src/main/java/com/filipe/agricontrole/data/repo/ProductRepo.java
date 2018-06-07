package com.filipe.agricontrole.data.repo;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.filipe.agricontrole.data.DatabaseManager;
import com.filipe.agricontrole.data.model.Category;
import com.filipe.agricontrole.data.model.Product;
import com.filipe.agricontrole.data.model.Stock;
import com.filipe.agricontrole.data.model.UnitType;

import java.util.ArrayList;
import java.util.List;

public class ProductRepo {
    private final String TAG = FarmRepo.class.getSimpleName().toString();

    private Product product;
    private Stock stock;
    private Category category;
    private UnitType unitType;

    public ProductRepo(){
        product = new Product();
        stock = new Stock();
        category = new Category();
        unitType = new UnitType();
    }

    public static String createTable() {
        return "CREATE TABLE IF NOT EXISTS " + Product.TABLE  + " ("
                + Product.KEY_ProductId  + "  INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Product.KEY_StockId + " INTEGER NOT NULL, "
                + Product.KEY_CategoryId + " INTEGER NOT NULL, "
                + Product.KEY_Name + " TEXT NOT NULL, "
                + Product.KEY_Quantity + " DOUBLE NOT NULL, "
                + Product.KEY_ExpirationDate + " TEXT NOT NULL, "
                + "FOREIGN KEY(" + Product.KEY_StockId + ") REFERENCES " + Stock.TABLE + "(" + Stock.KEY_StockId + ") ON DELETE CASCADE,"
                + "FOREIGN KEY(" + Product.KEY_CategoryId + ") REFERENCES " + Category.TABLE + "(" + Category.KEY_CategoryId + ") ON DELETE CASCADE);";
    }

    public static String insertInitialProduct(){
        return "INSERT INTO " + Product.TABLE + " VALUES (1, 1, 1, 'Forth Fungicida', 0.03, '30/07/2020');";
    }

    public int insert(Product product){
        int productId;
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        ContentValues values = new ContentValues();
        values.put(Product.KEY_StockId, product.getStock().getId());
        values.put(Product.KEY_CategoryId, product.getCategory().getId());
        values.put(Product.KEY_Name, product.getName());
        values.put(Product.KEY_Quantity, product.getQuantity());
        values.put(Product.KEY_ExpirationDate, product.getExpiration_date());

        // Inserting Row
        productId=(int)db.insert(Product.TABLE, null, values);
        DatabaseManager.getInstance().closeDatabase();

        return productId;
    }

    public boolean delete(int id) {
        try{
            SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
            String pragma = "PRAGMA foreign_keys = ON;"; //SQLite need to be enable to exclude ON CASCADE
            db.execSQL(pragma); //Enable to exclude ON CASCADE
            db.delete(Product.TABLE, Product.KEY_ProductId + "=" + id, null);

            return true;
        }catch (Exception e) {
            Log.d("Erro ao deletar Produto", e.toString());
            return false;
        }finally {
            DatabaseManager.getInstance().closeDatabase();
        }
    }

    public int update(Product product){
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        ContentValues values = new ContentValues();
        values.put(Product.KEY_ProductId, product.getId());
        values.put(Product.KEY_StockId, product.getStock().getId());
        values.put(Product.KEY_CategoryId, product.getCategory().getId());
        values.put(Product.KEY_Name, product.getName());
        values.put(Product.KEY_Quantity, product.getQuantity());
        values.put(Product.KEY_ExpirationDate, product.getExpiration_date());
        String id = String.valueOf(product.getId());
        String where = Product.KEY_ProductId + "=?";
        String[] whereArgs = new String[] {id};
        int count = db.update(Product.TABLE, values, where, whereArgs);
        DatabaseManager.getInstance().closeDatabase();
        return count;
    }

    public Product findById(int id){
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        String query = "SELECT p.id, p.name,p.quantity, p.expiration_date, s.id, s.farm_id," +
                " c.id, c.name, ut.id, ut.name FROM stock AS S INNER JOIN product AS p ON s.id = p.stock_id INNER JOIN category AS c" +
                " ON p.category_id = c.id INNER JOIN unit_type as ut ON c.unit_type_id = ut.id WHERE p.id = ?";
        Cursor c = db.rawQuery(query, new String[] {String.valueOf(id)});

        if(c.getCount() > 0){
            c.moveToFirst();

            product = new Product();
            stock = new Stock();
            category = new Category();
            unitType = new UnitType();

            product.setId(c.getInt(0));
            product.setName(c.getString(1));
            product.setQuantity(c.getDouble(2));
            product.setExpiration_date(c.getString(3));
            stock.setId(c.getInt(4));
            stock.setFarmId(c.getInt(5));
            category.setId(c.getInt(6));
            category.setName(c.getString(7));
            unitType.setId(c.getInt(8));
            unitType.setName(c.getString(9));

            category.setUnitType(unitType);
            product.setStock(stock);
            product.setCategory(category);

            return product;
        }
        return product;
    }

    public List<Product> findAllByStockId(int stockId){
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        String query = "SELECT p.id, p.name,p.quantity, p.expiration_date, s.id, s.farm_id," +
                " c.id, c.name, ut.id, ut.name FROM stock AS S INNER JOIN product AS p ON s.id = p.stock_id INNER JOIN category AS c" +
                " ON p.category_id = c.id INNER JOIN unit_type as ut ON c.unit_type_id = ut.id WHERE p.stock_id = ? ORDER BY p.id DESC";
        Cursor c = db.rawQuery(query, new String[] {String.valueOf(stockId)});

        List<Product> productList = new ArrayList<>();
        if(c.moveToFirst()){
            do{
                product = new Product();
                stock = new Stock();
                category = new Category();
                unitType = new UnitType();
                productList.add(product);

                product.setId(c.getInt(0));
                product.setName(c.getString(1));
                product.setQuantity(c.getDouble(2));
                product.setExpiration_date(c.getString(3));
                stock.setId(c.getInt(4));
                stock.setFarmId(c.getInt(5));
                category.setId(c.getInt(6));
                category.setName(c.getString(7));
                unitType.setId(c.getInt(8));
                unitType.setName(c.getString(9));

                category.setUnitType(unitType);
                product.setStock(stock);
                product.setCategory(category);
            }while (c.moveToNext());
        }
        return productList;
    }
}
