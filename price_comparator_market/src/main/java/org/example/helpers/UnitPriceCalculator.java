package org.example.helpers;

public class UnitPriceCalculator {
    public static double calculatePricePerUnit(String unit, double price, double quantity) {
        if (unit == null || quantity <= 0) {
            return -1;
        }

        String normalized = unit.toLowerCase();

        switch (normalized) {
            case "l":
            case "kg":
            case "buc":
            case "role":
                return price / quantity;
            case "g":
                return price / (quantity / 1000.0); // g to kg
            case "ml":
                return price / (quantity / 1000.0); // ml to l
            default:
                return -1;
        }
    }
}
