package org.example.entity;

import org.example.annotations.SerializableField;
import org.example.annotations.Serialized;
import org.example.annotations.SerializerGenerator;

@SerializerGenerator
@Serialized()
public class Product {

    @SerializableField(name = "name")
    private String name;

    @SerializableField(name = "price")
    private double price;

    @SerializableField
    private String category;

    public Product(String name, double price, String category) {
        this.name = name;
        this.price = price;
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public String getCategory() {
        return category;
    }
}