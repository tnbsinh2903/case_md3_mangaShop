package com.mangashop.model;

import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

public class Product {
    private int id;
    private String name;
    private double price;
    private int quantity;
    private String image;
    private int category;

    public Product() {
    }

    public Product(int id, String name, double price, int quantity, String image, int category) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.image = image;
        this.category = category;
    }

    public Product(String name, double price, int quantity, String image, int category) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.image = image;
        this.category = category;
    }

    public Product(int id, String name, String image, double price, int quantity, int category) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.image = image;
        this.category = category;
    }

    public Product(String name, String image) {
        this.name = name;
        this.image = image;

    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NotEmpty(message = " Name Not Empty")
    @Pattern(regexp = "^([A-Z]+[a-z]*[ ]?)+$", message = "Format name not right")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

//    @NotEmpty
//    @Min(1000)
//    @Max(100000)
    public double getPrice() {return price;}

    public void setPrice(double price) {
        this.price = price;
    }


    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @NotEmpty(message = "Image not empty")
    @URL
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                ", image='" + image + '\'' +
                ", category=" + category +
                '}';
    }
}
