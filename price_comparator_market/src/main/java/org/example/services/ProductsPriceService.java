package org.example.services;

import org.example.domain.PriceEntry;
import org.example.domain.Product;
import org.example.repositories.ProductsPricesRepository;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


public class ProductsPriceService {
    private final ProductsPricesRepository productPricesRepository;

    public ProductsPriceService(ProductsPricesRepository productPricesRepository){
        this.productPricesRepository = productPricesRepository;
    }

    public void loadPriceEntries() throws IOException {
        this.productPricesRepository.loadProductPrices();
    }

    public List<PriceEntry> getAllPriceEntries() {
        return this.productPricesRepository.getAllPriceEntries();
    }

    public List<PriceEntry> getAllProductsByName(String productName){
        return this.getAllPriceEntries().stream()
                .filter(entry -> entry.getProduct().getName().equalsIgnoreCase(productName))
                .collect(Collectors.toList());
    }

    public List<PriceEntry> getSortedPriceEntriesByStoreAndDate(List<PriceEntry> priceEntries){
        priceEntries = priceEntries.stream()
                .sorted(Comparator.comparing(PriceEntry::getStore)
                        .thenComparing(PriceEntry::getDate))
                .collect(Collectors.toList());

        return priceEntries;
    }

    public List<Product> getAllProducts(){
        return this.productPricesRepository.getAllProducts();
    }
    public List<Product> getAllSortedProductsById(){
        return this.getAllProducts().stream()
                .sorted(Comparator.comparing(Product::getId))
                .collect(Collectors.toList());
    }
}
