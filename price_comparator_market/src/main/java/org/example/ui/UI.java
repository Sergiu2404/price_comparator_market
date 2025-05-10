package org.example.ui;

import org.example.domain.Discount;
import org.example.domain.PriceEntry;
import org.example.domain.Product;
import org.example.services.DiscountsService;
import org.example.services.ProductsPriceService;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class UI {
    private final ProductsPriceService productsPriceService;
    private final DiscountsService discountsService;

    public UI(ProductsPriceService productsPriceService, DiscountsService discountsService){
        this.productsPriceService = productsPriceService;
        this.discountsService = discountsService;
    }

    public void init(){
        try{
            productsPriceService.loadPriceEntries();
        } catch (IOException exception){
            System.out.println("Failed to load data files due to error:  " + exception.getMessage());
        }
    }

    private void printMenu(){
        System.out.println("---------------------------< MENU >---------------------------");
        System.out.println("1. Get optimized shopping list");
        System.out.println("2. Get list of products with highest discounts across all tracked stores");
        System.out.println("3. Find discounts newly added (24 hours)");
        System.out.println("4. Get price history data points for given product name (filterable data)");
        System.out.println("5. Highlight val / unit to identify best buys (even if pack size differs)");
        System.out.println("6. Set target price alert for product name");
        System.out.println("7. See all tracked products");
        System.out.println("0. Exit");
        System.out.println("Choose one of the option above (input example: 1)");
        System.out.println("--------------------------------------------------------------");
    }

    public void runMenu(){
        Scanner scanner = new Scanner(System.in);
        String option;

        while(true){
            printMenu();
            System.out.println("option: ");
            option = scanner.nextLine();

            switch(option){
                case "1":
                    System.out.println("optim shop list");
                    break;

                case "2":
                    System.out.println("highest discounts");
                    break;

                case "3":
                    viewAllDiscountsAddedInLastDay();
                    System.out.println("newly added discounts");
                    break;

                case "4":
                    viewPriceHistoryForProduct();
                    break;

                case "5":
                    System.out.println("val / unit comparison");
                    break;

                case "6":
                    System.out.println("set target price alert for prod");
                    break;

                case "7":
                    viewAllRegisteredProducts();
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

    private void viewPriceHistoryForProduct(){
        String productName = "";
        Scanner scanner = new Scanner(System.in);

        System.out.println("product name");
        productName = scanner.nextLine();

        while(productName.equals("")){
            productName = scanner.nextLine();
        }

        List<PriceEntry> productsWithGivenName = productsPriceService.getAllProductsByName(productName);
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

    private void viewAllDiscountsAddedInLastDay(){
        for(Discount discount : this.discountsService.getAllDiscountsAddedInLastDay())
        {
            System.out.println(String.format("Discounts added in the last 24 hours: product name: %s"));
        }
    }
}

