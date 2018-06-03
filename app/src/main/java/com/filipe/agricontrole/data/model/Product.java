package com.filipe.agricontrole.data.model;

public class Product {
    public static final String TAG = Farm.class.getSimpleName();
    public static final String TABLE = "product";
    // Labels Table Columns names
    public static final String KEY_ProductId = "id";
    public static final String KEY_StockId = "stock_id";
    public static final String KEY_CategoryId = "category_id";
    public static final String KEY_Name = "name";
    public static final String KEY_Quantity = "quantity";
    public static final String KEY_ExpirationDate = "expiration_date";


    private int id;
    private Stock stock;
    private Category category;
    private String name, expiration_date;
    private double quantity;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExpiration_date() {
        return expiration_date;
    }

    public void setExpiration_date(String expiration_date) {
        this.expiration_date = expiration_date;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public Stock getStock() {
        return stock;
    }

    public void setStock(Stock stock) {
        this.stock = stock;
    }
}
