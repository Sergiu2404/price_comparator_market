package org.example.repositories;

import org.example.domain.PriceEntry;
import org.example.domain.Product;
import org.example.utils.PriceCSVParser;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class ProductsPricesRepository {
    private List<PriceEntry> priceEntries;
    private List<Product> products;
    private static final String DATA_DIR = "data_files";
    private final PriceCSVParser priceCSVParser;

    public ProductsPricesRepository() {
        this(new PriceCSVParser());
    }

    public ProductsPricesRepository(PriceCSVParser priceCSVParser) {
        this.priceCSVParser = priceCSVParser;
        this.priceEntries = new ArrayList<>();
        this.products = new ArrayList<>();
    }

    public URL getResourceUrl() throws IOException {
        URL resourceUrl = getClass().getClassLoader().getResource(DATA_DIR);
        if (resourceUrl == null) {
            throw new IOException("data_files folder not found in resources");
        }
        return resourceUrl;
    }

    public void loadProductPrices() throws IOException {
        Set<PriceEntry> uniquePriceEntriesSet = new HashSet<>();
        Map<String, Product> productMap = new HashMap<>();

        try {
            URL resourceUrl = getResourceUrl();
            Path folderPath = Paths.get(resourceUrl.toURI());

            try (DirectoryStream<Path> stream = Files.newDirectoryStream(folderPath)) {
                for (Path filePath : stream) {
                    if (Files.isRegularFile(filePath)) {
                        List<PriceEntry> fileEntries = priceCSVParser.parse(filePath);
                        for (PriceEntry entry : fileEntries) {
                            uniquePriceEntriesSet.add(entry);

                            Product product = entry.getProduct();
                            productMap.putIfAbsent(product.getId(), product);
                        }
                    }
                }
            }

        } catch (URISyntaxException e) {
            throw new IOException("Invalid URI for data_files directory", e);
        }

        this.priceEntries = new ArrayList<>(uniquePriceEntriesSet);
        this.products = new ArrayList<>(productMap.values());
    }

    public List<PriceEntry> getAllPriceEntries() {
        return this.priceEntries;
    }

    public List<Product> getAllProducts() {
        return this.products;
    }
}