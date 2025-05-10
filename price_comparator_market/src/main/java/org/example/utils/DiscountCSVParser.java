package org.example.utils;

import org.example.domain.Discount;
import org.example.domain.Product;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DiscountCSVParser {
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public List<Discount> parse(Path path) throws IOException {
        List<Discount> discounts = new ArrayList<>();

        String fileName = path.getFileName().toString();
        String discountStore = extractStoreName(fileName);
        String fileDate = extractDate(fileName).toString();

        try (BufferedReader br = Files.newBufferedReader(path)) {
            String line = br.readLine();
            while ((line = br.readLine()) != null) {
                String[] tokens = line.split(";");
                if (tokens.length != 9) continue;

                try {
                    String productId = tokens[0];
                    String name = tokens[1];
                    String brand = tokens[2];
                    double quantity = Double.parseDouble(tokens[3]);
                    String unit = tokens[4];
                    String category = tokens[5];
                    Date fromDate = dateFormat.parse(tokens[6]);
                    Date toDate = dateFormat.parse(tokens[7]);
                    double percentage = Double.parseDouble(tokens[8]);

                    Product product = new Product(productId, name, category, brand, quantity, unit);
                    Discount discount = new Discount(product, fromDate, toDate, percentage, discountStore, dateFormat.parse(fileDate));

                    discounts.add(discount);

                } catch (ParseException | NumberFormatException e) {
                    System.err.println("Skipping invalid row in file " + fileName + ": " + line);
                }
            }
        }

        return discounts;
    }

    private String extractStoreName(String filename) {
        return filename.split("_")[0];
    }

    private LocalDate extractDate(String filename) {
        String[] parts = filename.replace(".csv", "").split("_");
        return LocalDate.parse(parts[2]);
    }
}
