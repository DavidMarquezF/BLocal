package com.bteam.blocal.model;

public class ItemModel {
    private String name;
    private String imageUrl;
    private float price;
    private int stock;
    private String store;

    public ItemModel(String name, String imageUrl, float price, int stock, String store){
        this.price = price;
        this.name = name;
        this. imageUrl = imageUrl;
        this.stock = stock;
        this.store = store;
    }

    public boolean isInStock(){
        return stock > 0;
    }
    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getStore() {
        return store;
    }

    public void setStore(String store) {
        this.store = store;
    }
}
