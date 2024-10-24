package com.prm392.learnpe1.model;

import java.util.List;

public class Order {
    private String userId;
    private List<Cart> orderItems;
    private double totalPrice;
    private long orderDate; // Timestamp for the order date

    public Order() {
        // Required empty constructor for Firestore
    }

    public Order(String userId, List<Cart> orderItems, double totalPrice, long orderDate) {
        this.userId = userId;
        this.orderItems = orderItems;
        this.totalPrice = totalPrice;
        this.orderDate = orderDate;
    }

    // Getters and setters
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<Cart> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<Cart> orderItems) {
        this.orderItems = orderItems;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public long getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(long orderDate) {
        this.orderDate = orderDate;
    }
}
