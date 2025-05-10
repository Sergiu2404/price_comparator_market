package org.example.domain;

import java.time.LocalDate;

public class PriceEntry {
    private final Product product;
    private final double price;
    private final String currency;
    private final String store;
    private final LocalDate date;

    public PriceEntry(Product product, double price, String currency, String store, LocalDate date) {
        this.product = product;
        this.price = price;
        this.currency = currency;
        this.store = store;
        this.date = date;
    }

    public Product getProduct() {
        return product;
    }

    public double getPrice() {
        return price;
    }

    public String getCurrency() {
        return currency;
    }

    public String getStore() {
        return store;
    }

    public LocalDate getDate() {
        return date;
    }
}

