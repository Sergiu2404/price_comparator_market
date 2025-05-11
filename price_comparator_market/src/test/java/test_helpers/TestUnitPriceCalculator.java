package test_helpers;

import org.example.helpers.UnitPriceCalculator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestUnitPriceCalculator {

    @Test
    void calculatePricePerUnit_returnsCorrectForKg() {
        double result = UnitPriceCalculator.calculatePricePerUnit("kg", 10.0, 2.0);
        assertEquals(5.0, result);
    }

    @Test
    void calculatePricePerUnit_returnsCorrectForGrams() {
        double result = UnitPriceCalculator.calculatePricePerUnit("g", 5.0, 500);
        assertEquals(10.0, result);
    }

    @Test
    void calculatePricePerUnit_returnsNegativeForZeroQuantity() {
        double result = UnitPriceCalculator.calculatePricePerUnit("kg", 10.0, 0);
        assertEquals(-1, result);
    }

    @Test
    void calculatePricePerUnit_returnsNegativeForUnknownUnit() {
        double result = UnitPriceCalculator.calculatePricePerUnit("unknown", 10.0, 1.0);
        assertEquals(-1, result);
    }
}

