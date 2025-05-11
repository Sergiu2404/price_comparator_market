package org.example.data_transfer_objects;

public class ProductPriceWithDiscountInfo {
    private final String productId;
    private final String productName;
    private final String store;
    private final double originalPrice;
    private String currency;
    private final double discountPercentage;
    private final double finalPrice;
    private final double quantity;
    private final String unit;
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

    public String getStore() {
        return store;
    }

    public double getOriginalPrice() {
        return originalPrice;
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

    public double getDiscountPercentage() {
        return discountPercentage;
    }

    public double getQuantity() {
        return quantity;
    }

    public String getUnit() {
        return unit;
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
