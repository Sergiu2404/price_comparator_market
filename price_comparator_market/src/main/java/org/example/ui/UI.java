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
    Scanner scanner;


    public UI(ProductsPriceService productsPriceService, DiscountsService discountsService){
        this.productsPriceService = productsPriceService;
        this.discountsService = discountsService;

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
        System.out.println("5. Highlight val / unit to identify best buys (even if pack size differs)");
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

    private void runViewDiscountsMenu(){
        String option;

        while (true) {
            printViewDiscountsSubMenu();
            System.out.print("option: ");
            option = this.scanner.nextLine();

            switch (option) {
                case "1":
                    viewAllDiscounts(); // replace with your method
                    break;
                case "2":
                    viewAllAvailableDiscounts(); // replace with your method
                    break;
                case "0":
                    return; // exits submenu
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
                    //System.out.println("highest discounts");
                    break;

                case "3":
                    viewAllDiscountsAddedToday();
                    //System.out.println("newly added discounts");
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

    private void viewPriceHistoryForProduct(){
        String productName = "";

        System.out.println("product name");
        productName = scanner.nextLine();

        while(productName.equals("")){
            productName = this.scanner.nextLine();
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
        for(Discount discount : this.discountsService.getAllAvailableDiscounts())
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

