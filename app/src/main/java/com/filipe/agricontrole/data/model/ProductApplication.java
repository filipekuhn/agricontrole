package com.filipe.agricontrole.data.model;

public class ProductApplication {
    public static final String TAG = Farm.class.getSimpleName();
    public static final String TABLE = "product_application";
    // Labels Table Columns names
    public static final String KEY_ProductApplicationId = "id";
    public static final String KEY_ProductId = "product_id";
    public static final String KEY_PlantingId = "planting_id";
    public static final String KEY_Quantity = "quantity";
    public static final String KEY_Date = "date";


    private int id;
    private Product product;
    private Planting planting;
    private double quantity;
    private String date;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Planting getPlanting() {
        return planting;
    }

    public void setPlanting(Planting planting) {
        this.planting = planting;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
