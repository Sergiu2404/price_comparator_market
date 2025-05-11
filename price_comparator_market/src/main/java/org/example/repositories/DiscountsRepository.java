package org.example.repositories;

import org.example.domain.Discount;
import org.example.utils.DiscountCSVParser;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class DiscountsRepository {
    private List<Discount> discounts;
    private final DiscountCSVParser discountCSVParser;
    private static final String DATA_DIR = "data_files\\discounts";

    public DiscountsRepository() {
        this(new DiscountCSVParser());
    }

    public DiscountsRepository(DiscountCSVParser discountCSVParser) {
        this.discountCSVParser = discountCSVParser;
        this.discounts = new ArrayList<>();
    }

    public URL getResourceUrl() throws IOException {
        URL resourceUrl = getClass().getClassLoader().getResource(DATA_DIR);
        if (resourceUrl == null) {
            throw new IOException(String.format("%s folder not found in resources", DATA_DIR));
        }
        return resourceUrl;
    }

    public void loadDiscounts() throws IOException {
        List<Discount> discounts = new ArrayList<>();

        try {
            URL resourceUrl = getResourceUrl();
            Path folderPath = Paths.get(resourceUrl.toURI());

            try (DirectoryStream<Path> stream = Files.newDirectoryStream(folderPath)) {
                for (Path filePath : stream) {
                    if (Files.isRegularFile(filePath)) {
                        List<Discount> fileDiscounts = discountCSVParser.parse(filePath);
                        discounts.addAll(fileDiscounts);
                    }
                }
            }

        } catch (URISyntaxException exception) {
            throw new IOException(
                    String.format("Invalid URI for %s directory", DATA_DIR),
                    exception
            );
        }

        this.discounts = new ArrayList<>(discounts);
    }

    public List<Discount> getAllDiscounts() {
        return this.discounts;
    }
}