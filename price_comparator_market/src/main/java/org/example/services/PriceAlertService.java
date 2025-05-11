package org.example.services;

import org.example.domain.PriceAlert;
import org.example.domain.PriceEntry;
import org.example.domain.Product;

import java.util.ArrayList;
import java.util.List;

public class PriceAlertService {
    private List<PriceAlert> priceAlerts;
    private ProductsPriceService productsPriceService;
    private DiscountsService discountsService;

    public PriceAlertService(ProductsPriceService productsPriceService, DiscountsService discountsService){
        this.productsPriceService = productsPriceService;
        this.discountsService = discountsService;
    }

    public boolean addPriceAlert(String productName, double targetPrice){
        List<PriceEntry> priceEntries = this.productsPriceService.getAllPriceEntriesByName(productName);
        if(priceEntries.isEmpty()){
            return false;
        }

        PriceAlert alert = new PriceAlert(productName, targetPrice);
        priceAlerts.add(alert);
        return true;
    }

    public List<PriceAlert> checkAlerts() {
        List<PriceAlert> triggeredAlerts = new ArrayList<>();

        for (PriceAlert alert : priceAlerts) {
            List<PriceEntry> currentPriceEntries = productsPriceService.getAllPriceEntriesByName(alert.getProductName());

            for(PriceEntry priceEntry : currentPriceEntries){
                if (priceEntry.getPrice() <= alert.getTargetPrice()) {
                    System.out.println("PRICE ALERT: " + priceEntry.getProduct().getName() +
                            " is now " + priceEntry.getPrice() +
                            " (Target: " + alert.getTargetPrice() + ")");

                    triggeredAlerts.add(alert);
                }
            }
        }
        return triggeredAlerts;
    }

    public List<PriceAlert> getAllAlerts() {
        return this.priceAlerts;
    }

    public boolean deleteAlert(String productName) {
        return priceAlerts.removeIf(alert -> alert.getProductName().equals(productName));
    }
}
