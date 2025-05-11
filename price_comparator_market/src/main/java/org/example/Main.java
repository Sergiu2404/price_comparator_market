package org.example;

import org.example.repositories.DiscountsRepository;
import org.example.repositories.ProductsPricesRepository;
import org.example.services.DiscountsService;
import org.example.services.PriceAlertService;
import org.example.services.ProductRecommendationService;
import org.example.services.ProductsPriceService;
import org.example.ui.UI;

public class Main {
    public static void main(String[] args) {
        ProductsPricesRepository productPricesRepository = new ProductsPricesRepository();
        DiscountsRepository discountsRepository = new DiscountsRepository();

        ProductsPriceService productsPriceService = new ProductsPriceService(productPricesRepository);
        DiscountsService discountsService = new DiscountsService(discountsRepository);
        PriceAlertService priceAlertService = new PriceAlertService(productsPriceService, discountsService);
        ProductRecommendationService productRecommendationService = new ProductRecommendationService(discountsService, productsPriceService);

        UI ui = new UI(productsPriceService, discountsService, productRecommendationService, priceAlertService);

        ui.init();
        ui.runMenu();
    }
}