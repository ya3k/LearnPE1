package com.prm392.learnpe1.model;

import java.util.UUID;

public class Product {
    private String id;
    private String proName;
    private String description;
    private double price;
    private String img;

    public Product() {

    }

//    public Product(String proName, String description, double price, String img) {
//        this.proName = proName;
//        this.description = description;
//        this.price = price;
//        this.img = img;
//    }

    public Product(String id, String proName, String description, double price, String img) {
        this.id = id;
        this.proName = proName;
        this.description = description;
        this.price = price;
        this.img = img;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getProName() {
        return proName;
    }

    public void setProName(String name) {
        this.proName = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
