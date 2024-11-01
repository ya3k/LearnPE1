package com.prm392.learnpe1.model;

public class Cart {
    private String productId;
    private String productName;
    private double productPrice;
    private String productImage;
    private int quantity;

    public Cart() {
    }

    public Cart(String productId, String productName, double productPrice, String productImage, int quantity) {
        this.productId = productId;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productImage = productImage;
        this.quantity = quantity;
    }

//    public Cart(String productName, double productPrice, String productImage, int quantity) {
//        this.productName = productName;
//        this.productPrice = productPrice;
//        this.productImage = productImage;
//        this.quantity = quantity;
//    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
