package org.example.data_transfer_objects;

import org.example.helpers.UnitPriceCalculator;

public class ProductPriceWithDiscountInfo {
    private String productId;
    private String productName;
    private String store;
    private double originalPrice;
    private String currency;
    private double discountPercentage; // can be null if no discount
    private double finalPrice;
    private double quantity;
    private String unit;
    private double pricePerUnit;

    public ProductPriceWithDiscountInfo(String productId, String productName, String store, double originalPrice, String currency, double discountPercentage, double finalPrice, double quantity, String unit, double pricePerUnit) {
        this.productId = productId;
        this.productName = productName;
        this.store = store;
        this.originalPrice = originalPrice;
        this.currency = currency;
        this.discountPercentage = discountPercentage;
        this.finalPrice = finalPrice;
        this.quantity = quantity;
        this.unit = unit;
        this.pricePerUnit = pricePerUnit;
    }

    public String getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getStore() {
        return store;
    }

    public void setStore(String store) {
        this.store = store;
    }

    public double getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(double originalPrice) {
        this.originalPrice = originalPrice;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public double getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(double finalPrice) {
        this.finalPrice = finalPrice;
    }

    public double getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(double discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public double getPricePerUnit() {
        return pricePerUnit;
    }

    public void setPricePerUnit(double pricePerUnit) {
        this.pricePerUnit = pricePerUnit;
    }

    @Override
    public String toString() {
        return String.format(
                "Product ID: %s\nProduct Name: %s\nStore: %s\nOriginal Price: %.2f %s\nDiscount: %s%%\nFinal Price: %.2f %s\nPrice per Unit: %.2f %s/%s\n-----------\n",
                productId,
                productName,
                store,
                originalPrice,
                currency,
                discountPercentage == 0 ? "No discount" : String.format("%.2f", discountPercentage),
                finalPrice,
                currency,
                pricePerUnit,
                currency,
                unit
        );
    }

}
