package com.prm392.learnpe1.model;

public class Product {
    private String proName;
    private String description;
    private double price;
    private String img;

    public Product() {

    }

    public Product(String proName, String description, double price, String img) {
        this.proName = proName;
        this.description = description;
        this.price = price;
        this.img = img;
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
