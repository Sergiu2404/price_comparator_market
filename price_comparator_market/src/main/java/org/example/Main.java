package org.example;

import org.example.repositories.DiscountsRepository;
import org.example.repositories.ProductsPricesRepository;
import org.example.services.DailyShoppingBasketService;
import org.example.services.DiscountsService;
import org.example.services.ProductRecommendationService;
import org.example.services.ProductsPriceService;
import org.example.ui.UI;

public class Main {
    public static void main(String[] args) {
        ProductsPricesRepository productPricesRepository = new ProductsPricesRepository();
        DiscountsRepository discountsRepository = new DiscountsRepository();

        ProductsPriceService productsPriceService = new ProductsPriceService(productPricesRepository);
        DiscountsService discountsService = new DiscountsService(discountsRepository);
        DailyShoppingBasketService dailyShoppingBasketService = new DailyShoppingBasketService();
        ProductRecommendationService productRecommendationService = new ProductRecommendationService(discountsService);

        UI ui = new UI(productsPriceService, discountsService, productRecommendationService, dailyShoppingBasketService);

        ui.init();
        ui.runMenu();
    }
}