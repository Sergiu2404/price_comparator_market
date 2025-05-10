package org.example.repositories;

import org.example.domain.Discount;
import org.example.domain.PriceEntry;
import org.example.domain.Product;
import org.example.utils.DiscountCSVParser;
import org.example.utils.PriceCSVParser;

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
    private static final String DATA_DIR = "data_files\\discounts";

    public void loadDiscounts() throws IOException {
        DiscountCSVParser discountCSVParser = new DiscountCSVParser();
        List<Discount> discounts = new ArrayList<>();

        try {
            URL resourceUrl = getClass().getClassLoader().getResource(DATA_DIR);
            if (resourceUrl == null) {
                throw new IOException(String.format("%s folder not found in resources", DATA_DIR));
            }

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

    public List<Discount> getAllDiscounts(){return this.discounts;}
}
