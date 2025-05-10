package org.example.domain;

public class Product {
    private final String id;
    private final String name;
    private final String category;
    private final String brand;
    private final double quantity;
    private final String unit;


    public Product(String id, String name, String category, String brand, double quantity, String unit) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.brand = brand;
        this.quantity = quantity;
        this.unit = unit;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public String getCategory() {
        return category;
    }

    public String getBrand() {
        return brand;
    }

    public double getQuantity() {
        return quantity;
    }

    public String getUnit() {
        return unit;
    }

    @Override
    public String toString() {
        return this.getId() + ", " + this.getName() + ", " + this.getCategory() + ", " + this.getBrand() + ", quantity: " + this.getQuantity() + ", unit: " + this.getUnit();
    }
}
