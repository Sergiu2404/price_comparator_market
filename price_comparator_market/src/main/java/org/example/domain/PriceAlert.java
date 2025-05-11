package org.example.domain;

public class PriceAlert {
    private String productName;
    private double targetPrice;

    public PriceAlert(String productName, double targetPrice){
        this.productName = productName;
        this.targetPrice = targetPrice;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getTargetPrice() {
        return targetPrice;
    }

    public void setTargetPrice(double targetPrice) {
        this.targetPrice = targetPrice;
    }

    @Override
    public String toString() {
        return this.productName + " at target price " + this.targetPrice;
    }
}
