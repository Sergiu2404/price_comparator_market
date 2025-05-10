package org.example.utils;

import org.example.domain.PriceEntry;
import org.example.domain.Product;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PriceCSVParser {
    public List<PriceEntry> parse(Path path) throws IOException {
        List<PriceEntry> entries = new ArrayList<>();
        List<Product> products = new ArrayList<>();

        String fileName = path.getFileName().toString();
        String store = extractStoreName(fileName);
        LocalDate date = extractDate(fileName);

        try (BufferedReader br = Files.newBufferedReader(path)) {
            String line = br.readLine();
            while ((line = br.readLine()) != null) {
                String[] tokens = line.split(";");
                if (tokens.length != 8) continue;

                String id = tokens[0];
                String name = tokens[1];
                String category = tokens[2];
                String brand = tokens[3];
                double quantity = Double.parseDouble(tokens[4]);
                String unit = tokens[5];
                double price = Double.parseDouble(tokens[6]);
                String currency = tokens[7];

                Product product = new Product(id, name, category, brand, quantity, unit);
                PriceEntry entry = new PriceEntry(product, price, currency, store, date);
                entries.add(entry);
            }
        }

        return entries;
    }

    private String extractStoreName(String filename) {
        return filename.split("_")[0]; // e.g., kaufland_2025-05-01.csv
    }

    private LocalDate extractDate(String filename) {
        String datePart = filename.split("_")[1].replace(".csv", "");
        return LocalDate.parse(datePart);
    }
}

