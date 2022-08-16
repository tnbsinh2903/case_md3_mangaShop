package com.mangashop.model;

public class Order {
    private int id;
    private int buyer;
    private long TotalPrice ;


    public Order(int id, int buyer, long totalPrice) {
        this.id = id;
        this.buyer = buyer;
        TotalPrice = totalPrice;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBuyer() {
        return buyer;
    }

    public void setBuyer(int buyer) {
        this.buyer = buyer;
    }


    public long getTotalPrice() {
        return TotalPrice;
    }

    public void setTotalPrice(long totalPrice) {
        TotalPrice = totalPrice;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", buyer=" + buyer +
                ", TotalPrice=" + TotalPrice +
                '}';
    }
}
