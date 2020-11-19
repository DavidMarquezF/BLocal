package com.bteam.blocal.model;

public class ItemModel {
    private String uid;
    private String name;
    private String imageUrl;
    private float price;
    private int stock;

    public ItemModel(String uid, String name, String imageUrl, float price, int stock){
        this.price = price;
        this.uid = uid;
        this.name = name;
        this. imageUrl = imageUrl;
        this.stock = stock;
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

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
}
