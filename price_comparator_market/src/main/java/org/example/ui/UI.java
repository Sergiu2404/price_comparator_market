package org.example.ui;

import org.example.data_transfer_objects.ProductPriceWithDiscountInfo;
import org.example.domain.Discount;
import org.example.domain.PriceEntry;
import org.example.domain.Product;
import org.example.helpers.UnitPriceCalculator;
import org.example.services.DailyShoppingBasketService;
import org.example.services.DiscountsService;
import org.example.services.ProductRecommendationService;
import org.example.services.ProductsPriceService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class UI {
    private final ProductsPriceService productsPriceService;
    private final DiscountsService discountsService;
    private final ProductRecommendationService productRecommendationService;
    private final DailyShoppingBasketService dailyShoppingBasketService;
    Scanner scanner;


    public UI(ProductsPriceService productsPriceService, DiscountsService discountsService, ProductRecommendationService productRecommendationService, DailyShoppingBasketService dailyShoppingBasketService){
        this.productsPriceService = productsPriceService;
        this.discountsService = discountsService;
        this.productRecommendationService = productRecommendationService;
        this.dailyShoppingBasketService = dailyShoppingBasketService;

        this.scanner = new Scanner(System.in);
    }

    public void init(){
        try{
            productsPriceService.loadPriceEntries();
        } catch (IOException exception){
            System.out.println("Failed to load data files with prices due to error:  " + exception.getMessage());
        }

        try{
            discountsService.loadDiscounts();
        } catch (IOException exception){
            System.out.println("Failed to load data files with discounts due to error:  " + exception.getMessage());
        }
    }

    private void printMenu(){
        System.out.println("---------------------------< MENU >---------------------------");
        System.out.println("1. Get optimized shopping list (including only currently available discounts)");
        System.out.println("2. Get list of products with highest discounts across all tracked stores (only those currently available)");
        System.out.println("3. Find discounts newly announced / posted (today)");
        System.out.println("4. Get price history data points for given product name (filterable data)");
        System.out.println("5. See recommended products (product name, category) considering available discount and value / unit");
        System.out.println("6. See all tracked products");
        System.out.println("7. See all discounts / all available discounts");
        System.out.println("0. Exit");
        System.out.println("Choose one of the options above (input example: 1)");
        System.out.println("--------------------------------------------------------------");
    }

    private void printViewDiscountsSubMenu(){
        System.out.println("--------------------------------------------------------------");
        System.out.println("1. See all discounts");
        System.out.println("2. See all currently available discounts");
        System.out.println("0. Exit this submenu");
        System.out.println("Choose one of the options above (input example: 1)");
        System.out.println("--------------------------------------------------------------");
    }

    private void runViewDiscountsMenu(){
        String option;

        while (true) {
            printViewDiscountsSubMenu();
            System.out.print("option: ");
            option = this.scanner.nextLine();

            switch (option) {
                case "1":
                    viewAllDiscounts();
                    break;
                case "2":
                    viewAllAvailableDiscounts();
                    break;
                case "0":
                    return;
                default:
                    System.out.println("Invalid option: please enter 0, 1, or 2.");
            }
        }
    }

    public void runMenu(){
        Scanner scanner = new Scanner(System.in);
        String option;

        while(true){
            printMenu();
            System.out.println("option: ");
            option = this.scanner.nextLine();

            switch(option){
                case "1":
                    createProductsBasket();
                    break;

                case "2":
                    viewHighestAvailableDiscounts();
                    break;

                case "3":
                    viewAllDiscountsAddedToday();
                    break;

                case "4":
                    viewPriceHistoryForProduct();
                    break;

                case "5":
                    viewRecommendedProductsConsideringValuePerUnitAndDiscount();
                    break;

                case "6":
                    viewAllRegisteredProducts();
                    break;

                case "7":
                    runViewDiscountsMenu();
                    break;

                case "0":
                    System.out.println("Exiting program...");
                    scanner.close();
                    return;

                default:
                    System.out.println("Invalid option: must be an integer between 0 and 6");
            }
        }
    }

    private void createProductsBasket(){
        List<String> productNames = new ArrayList<>();
        System.out.println("Enter product names to add to you shopping basket (type '0' when you are done");

        while(true)
        {
            System.out.println("product name:");
            String input = this.scanner.nextLine().trim();

            if(input.equals("0"))
            {
                break;
            }

            if(!input.isEmpty())
            {
                productNames.add(input);
            }
            else{
                System.out.println("Product name can't be empty");
            }
        }

        System.out.println("You’ve added the following products to your basket:");
        for (String name : productNames) {
            System.out.print(" , " + name);
        }

        List<PriceEntry> priceEntries = this.productsPriceService.getAllPriceEntries();
        List<ProductPriceWithDiscountInfo> productPricesWithDiscountInfo = productRecommendationService.getPriceInfoWithDiscounts(priceEntries);
        Map<String, List<ProductPriceWithDiscountInfo>> optimizedBasketForSavings = this.dailyShoppingBasketService.optimizeBasketForSavings(productNames, productPricesWithDiscountInfo);

        viewOptimizedShoppingLists(optimizedBasketForSavings);
    }

    private void viewOptimizedShoppingLists(Map<String, List<ProductPriceWithDiscountInfo>> optimizedBasketForSavings)
    {
        System.out.println("Optimized Shopping Basket (grouped by store):");
        for (Map.Entry<String, List<ProductPriceWithDiscountInfo>> entry : optimizedBasketForSavings.entrySet()) {
            String store = entry.getKey();
            List<ProductPriceWithDiscountInfo> products = entry.getValue();

            System.out.println("Store: " + store);
            for (ProductPriceWithDiscountInfo product : products) {
                System.out.println(" - Product: " + product.getProductName());
                System.out.println("   Original Price: " + product.getOriginalPrice() + " " + product.getCurrency());
                if (product.getDiscountPercentage() != 0) {
                    System.out.println("   Discount: " + product.getDiscountPercentage() + "%");
                    System.out.println("   Discounted Price: " + product.getFinalPrice() + " " + product.getCurrency());
                } else {
                    System.out.println("   No discount available.");
                }
                System.out.println();
            }
        }
    }

    private void viewRecommendedProductsConsideringValuePerUnitAndDiscount(){
        System.out.println("product name: ");
        String productName = this.scanner.nextLine();
        System.out.println("product category: ");
        String productCategory = this.scanner.nextLine();

        List<PriceEntry> priceEntriesNameAndCategory = this.productsPriceService.getPriceEntriesByProductNameAndCategory(productName, productCategory);
        List<ProductPriceWithDiscountInfo> results = productRecommendationService.getPriceInfoWithDiscounts(priceEntriesNameAndCategory);

        System.out.println("Price per unit for each product:");

        for (ProductPriceWithDiscountInfo productInfo : results) {
            System.out.println("Product ID: " + productInfo.getProductId());
            System.out.println("name: " + productInfo.getProductName());
            System.out.println("In Store: " + productInfo.getStore());
            System.out.println("Original price: " + productInfo.getOriginalPrice() + " " + productInfo.getCurrency());

            if (productInfo.getDiscountPercentage() != 0) {
                System.out.println("Discount: " + productInfo.getDiscountPercentage() + "%");
                System.out.println("Discounted price: " + productInfo.getFinalPrice() + " " + productInfo.getCurrency());
            } else {
                System.out.println("No discount available.");
            }

            try {
                double pricePerUnit = UnitPriceCalculator.calculatePricePerUnit(
                        productInfo.getUnit(),
                        productInfo.getFinalPrice(),
                        productInfo.getQuantity()
                );
                System.out.printf("Price per unit: %.2f %s/ (kg / l / buc / role)%n",
                        pricePerUnit,
                        productInfo.getCurrency()
                );
                System.out.println("-----------");
            } catch (IllegalArgumentException e) {
                System.out.println("Could not calculate price per unit: " + e.getMessage());
            }
        }
        List<ProductPriceWithDiscountInfo> sortedResults = productRecommendationService.sortByPricePerUnit(results);

        System.out.println("-----------");
        System.out.println("Sorted products by lowest price per unit, recommended products (best value first):");

        for (ProductPriceWithDiscountInfo product : sortedResults) {
            System.out.println(product.toString());
        }
    }

    private void viewPriceHistoryForProduct(){
        System.out.println("product name: ");
        String productName = this.scanner.nextLine();

        if (productName.isEmpty()) {
            do {
                productName = this.scanner.nextLine();
            } while (productName.isEmpty());
        }

        List<PriceEntry> productsWithGivenName = productsPriceService.getAllPriceEntriesByName(productName);
        List<PriceEntry> sortedEntries = productsPriceService.getSortedPriceEntriesByStoreAndDate(productsWithGivenName);

        for(PriceEntry priceEntry : sortedEntries){
            System.out.printf("Product name: %s, Store: %s, Date: %s %n", productName, priceEntry.getStore(), priceEntry.getDate());
        }
    }

    private void viewAllRegisteredProducts(){
        System.out.println("All registered products: ");

        List<Product> sortedProductsById = this.productsPriceService.getAllSortedProductsById();

        for(Product product : sortedProductsById)
        {
            System.out.println(product.toString());
        }
    }

    private void viewAllDiscounts(){
        for(Discount discount : this.discountsService.getAllDiscounts()){
            System.out.println(discount.toString() + " at product " + discount.getProduct().getName());
        }
    }

    private void viewAllDiscountsAddedToday(){
        for(Discount discount : this.discountsService.getAllDiscountsAddedToday())
        {
            System.out.printf("Product: %s, %s%n", discount.getProduct().toString(), discount);
        }
    }

    private void viewAllAvailableDiscounts(){
        for(Discount discount : this.discountsService.getAllFilteredDiscountsByAvailability())
        {
            System.out.printf("Product: %s, %s%n", discount.getProduct().toString(), discount);
        }
    }

    private void viewHighestAvailableDiscounts(){
        for(Discount discount : this.discountsService.getHighestDiscountPercentageForEachStore())
        {
            System.out.printf("Product: %s, %s%n", discount.getProduct().toString(), discount);
        }
    }
}

