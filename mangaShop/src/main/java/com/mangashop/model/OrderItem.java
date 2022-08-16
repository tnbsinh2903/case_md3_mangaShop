package com.mangashop.model;

import java.math.BigDecimal;

public class OrderItem {
    private int id ;
    private String name;
    private double price;
    private int quantity;
    private int userBuy;
    private double total ;

    public OrderItem() {

    }

    public OrderItem(int id, String name, double price, int quantity, int userBuy, double total) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.userBuy = userBuy;
        this.total = total;
    }

    public OrderItem(String name, double price, int quantity, int userbuy, double total) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.userBuy = userBuy;
        this.total = total;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }


    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getUserBuy() {
        return userBuy;
    }

    public void setUserBuy(int userBuy) {
        this.userBuy = userBuy;
    }

    @Override
    public String toString() {
        return "OrderItem{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                ", userBuy=" + userBuy +
                ", total=" + total +
                '}';
    }
}
