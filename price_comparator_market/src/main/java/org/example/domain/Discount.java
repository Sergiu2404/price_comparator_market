package org.example.domain;

import java.util.Date;

public class Discount {
    private Product product;
    private Date fromDate;
    private Date toDate;
    private double percentageOfDiscount;
    private String discountStore;
    private Date fileDate;

    public Discount(Product product, Date fromDate, Date toDate, double percentageOfDiscount, String discountStore, Date fileDate){
        this.product = product;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.percentageOfDiscount = percentageOfDiscount;
        this.discountStore = discountStore;
        this.fileDate = fileDate;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public double getPercentageOfDiscount() {
        return percentageOfDiscount;
    }

    public void setPercentageOfDiscount(double percentageOfDiscount) {
        this.percentageOfDiscount = percentageOfDiscount;
    }

    public String getDiscountStore() {
        return discountStore;
    }

    public void setDiscountStore(String discountStore) {
        this.discountStore = discountStore;
    }

    public Date getFileDate() {
        return fileDate;
    }

    public void setFileDate(Date fileDate) {
        this.fileDate = fileDate;
    }
}
