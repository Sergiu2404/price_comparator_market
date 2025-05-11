package org.example.helpers;

public class UnitPriceCalculator {
    public static double calculatePricePerUnit(String unit, double price, double quantity) {
        if (unit == null || quantity <= 0) {
            return -1;
        }

        String normalized = unit.toLowerCase();

        return switch (normalized) {
            case "l", "kg", "buc", "role" -> price / quantity;
            case "g" -> price / (quantity / 1000.0); // g to kg
            case "ml" -> price / (quantity / 1000.0); // ml to l
            default -> -1;
        };
    }
}
