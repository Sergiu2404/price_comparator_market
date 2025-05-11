package test_helpers;

import org.example.domain.PriceEntry;
import org.example.domain.Product;
import org.example.helpers.SubstringPriceEntryStrategy;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class TestSubstringPriceEntryStrategy {

    @Test
    void matches_returnsTrue_whenBothMatch() {
        Product product = new Product("P1", "iaurt grecesc", "Lactate", "napolact", 1.0, "kg");
        PriceEntry entry = new PriceEntry(product, 5.0, "RON", "lidl", LocalDate.now());

        SubstringPriceEntryStrategy strategy = new SubstringPriceEntryStrategy("iaurt", "lactate");

        assertTrue(strategy.matches(entry));
    }

    @Test
    void matches_returnsTrue_whenOnlyCategoryGiven() {
        Product product = new Product("P1", "detergent lichid", "Curatenie", "ariel", 2.0, "l");
        PriceEntry entry = new PriceEntry(product, 12.0, "RON", "kaufland", LocalDate.now());

        SubstringPriceEntryStrategy strategy = new SubstringPriceEntryStrategy("", "curatenie");

        assertTrue(strategy.matches(entry));
    }

    @Test
    void matches_returnsFalse_whenNoMatch() {
        Product product = new Product("P1", "suc portocale", "Bauturi", "Cappy", 1.5, "l");
        PriceEntry entry = new PriceEntry(product, 8.0, "RON", "lidl", LocalDate.now());

        SubstringPriceEntryStrategy strategy = new SubstringPriceEntryStrategy("iaurt", "Lactate");

        assertFalse(strategy.matches(entry));
    }
}

