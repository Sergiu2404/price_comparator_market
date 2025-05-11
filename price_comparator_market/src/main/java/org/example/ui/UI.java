package org.example.ui;

import org.example.data_transfer_objects.ProductPriceWithDiscountInfo;
import org.example.domain.Discount;
import org.example.domain.PriceAlert;
import org.example.domain.PriceEntry;
import org.example.domain.Product;
import org.example.helpers.UnitPriceCalculator;
import org.example.services.DiscountsService;
import org.example.services.PriceAlertService;
import org.example.services.ProductRecommendationService;
import org.example.services.ProductsPriceService;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

public class UI {
    private final ProductsPriceService productsPriceService;
    private final DiscountsService discountsService;
    private final ProductRecommendationService productRecommendationService;
    private final PriceAlertService priceAlertService;
    Scanner scanner;


    public UI(ProductsPriceService productsPriceService, DiscountsService discountsService, ProductRecommendationService productRecommendationService, PriceAlertService priceAlertService){
        this.productsPriceService = productsPriceService;
        this.discountsService = discountsService;
        this.productRecommendationService = productRecommendationService;
        this.priceAlertService = priceAlertService;

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
        System.out.println("1. Get optimized shopping list");
        System.out.println("2. Get list of products with highest discounts across all tracked stores");
        System.out.println("3. Find discounts newly announced / posted (today)");
        System.out.println("4. Get price history data points for given product name (filterable data)");
        System.out.println("5. See recommended products (product name, category) considering available discount and value / unit");
        System.out.println("6. Set target price alert for product name");
        System.out.println("7. See all tracked products");
        System.out.println("8. See all discounts / all available discounts");
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

    private void printPriceAlertsMenu(){
        System.out.println("--------------------------------------------------------------");
        System.out.println("1. See all alerts");
        System.out.println("2. Add new alert for product (by product name)");
        System.out.println("3. Delete alert for product (by product name)");
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

    private void runPriceAlertsMenu() {
        String option;

        while (true) {
            printPriceAlertsMenu();
            System.out.print("option: ");
            option = this.scanner.nextLine();

            switch (option) {
                case "1":
                    //viewAllAlerts();
                    break;
                case "2":
                    //addNewPriceAlert();
                    break;
                case "3":
                    //deletePriceAlert();
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
                    System.out.println("optim shop list");
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
                    runPriceAlertsMenu();
                    break;

                case "7":
                    viewAllRegisteredProducts();
                    break;

                case "8":
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

    private void addNewPriceAlert(){
        String productName = "";
        double targetPrice = -1;

        System.out.println("product name: ");
        productName = this.scanner.nextLine();

        while(productName.equals(""))
        {
            System.out.println("product name: ");
            productName = this.scanner.nextLine();
        }

        while (true) {
            System.out.print("Target price: ");
            String priceInput = this.scanner.nextLine().trim();

            try {
                targetPrice = Double.parseDouble(priceInput);
                if (targetPrice <= 0) {
                    System.out.println("Price must be greater than 0. Try again.");
                } else {
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid number format. Please enter a numeric value.");
            }
        }
        if(!this.priceAlertService.addPriceAlert(productName, targetPrice))
        {
            System.out.println("Price alert couldn't be added for the product with the given name");
        }
    }

    private void deletePriceAlert(){
        String productName = "";

        System.out.println("product name: ");
        productName = this.scanner.nextLine();

        while(productName.equals(""))
        {
            System.out.println("product name: ");
            productName = this.scanner.nextLine();
        }

        if (this.priceAlertService.deleteAlert(productName)) {
            System.out.println("Alert removed for product: " + productName);
        } else {
            System.out.println("No alert found for product: " + productName);
        }
    }

    private void viewAllAlerts(){
        List<PriceAlert> priceAlerts = this.priceAlertService.getAllAlerts();
        if (priceAlerts.isEmpty()) {
            System.out.println("No alerts found");
            return;
        }

        for (PriceAlert alert : priceAlerts) {
            List<PriceEntry> priceEntries = productsPriceService.getAllPriceEntriesByName(alert.getProductName());
            double currentPrice;
            for(PriceEntry priceEntry : priceEntries){
                currentPrice = priceEntry.getPrice();

                System.out.println("Product: " + priceEntry.getProduct().getId() +
                        ", Name: " + priceEntry.getProduct().getName());
                System.out.println("Current price: " + currentPrice);
                System.out.println("Target price: " + alert.getTargetPrice());
                System.out.println("-----------------------------");
            }
        }
    }

    private void viewRecommendedProductsConsideringValuePerUnitAndDiscount(){
        String productName = "", productCategory = "";

        System.out.println("product name: ");
        productName = this.scanner.nextLine();
        System.out.println("product category: ");
        productCategory = this.scanner.nextLine();

        List<PriceEntry> priceEntriesNameAndCategory = this.productsPriceService.getPriceEntriesByProductNameAndCategory(productName, productCategory);
        List<ProductPriceWithDiscountInfo> results = productRecommendationService.getPriceInfoWithDiscounts(priceEntriesNameAndCategory);

        System.out.println("Price per unit for each product:");

        for (ProductPriceWithDiscountInfo productInfo : results) {
            System.out.println("Product ID: " + productInfo.getProductId());
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
        System.out.println("-----------");
        System.out.println("Sorted products by lowest price per unit:");

        List<ProductPriceWithDiscountInfo> sortedResults = productRecommendationService.sortByPricePerUnit(results);

        System.out.println("Recommended Products (best value first):");

        for (ProductPriceWithDiscountInfo product : sortedResults) {
            System.out.println(product.toString());
        }
    }

    private void viewPriceHistoryForProduct(){
        String productName = "";

        System.out.println("product name: ");
        productName = this.scanner.nextLine();

        while(productName.equals("")){
            productName = this.scanner.nextLine();
        }

        List<PriceEntry> productsWithGivenName = productsPriceService.getAllPriceEntriesByName(productName);
        List<PriceEntry> sortedEntries = productsPriceService.getSortedPriceEntriesByStoreAndDate(productsWithGivenName);

        for(PriceEntry priceEntry : sortedEntries){
            System.out.println(String.format("Product name: %s, Store: %s, Date: %s ", productName, priceEntry.getStore(), priceEntry.getDate()));
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
            System.out.println(discount.toString());
        }
    }

    private void viewAllDiscountsAddedToday(){
        for(Discount discount : this.discountsService.getAllDiscountsAddedToday())
        {
            System.out.println(String.format("Product: %s, %s", discount.getProduct().toString(), discount.toString()));
        }
    }

    private void viewAllAvailableDiscounts(){
        for(Discount discount : this.discountsService.getAllFilteredDiscountsByAvailability())
        {
            System.out.println(String.format("Product: %s, %s", discount.getProduct().toString(), discount.toString()));
        }
    }

    private void viewHighestAvailableDiscounts(){
        for(Discount discount : this.discountsService.getHighestDiscountPercentageForEachStore())
        {
            System.out.println(String.format("Product: %s, %s", discount.getProduct().toString(), discount.toString()));
        }
    }
}

