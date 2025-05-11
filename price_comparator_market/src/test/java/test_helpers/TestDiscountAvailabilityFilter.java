package test_helpers;

import org.example.domain.Discount;
import org.example.domain.Product;
import org.example.helpers.DiscountAvailabilityFilter;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class TestDiscountAvailabilityFilter {

    @Test
    void filter_returnsTrue_whenDiscountIsActiveToday() {
        Product product = new Product("P1", "ciocolata", "gustari", "milka", 1, "kg");
        LocalDate today = LocalDate.now();
        Date from = Date.from(today.minusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date to = Date.from(today.plusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date fileDate = new Date();

        Discount discount = new Discount(product, from, to, 10.0, "kaufland", fileDate);
        DiscountAvailabilityFilter filter = new DiscountAvailabilityFilter();

        assertTrue(filter.filter(discount));
    }

    @Test
    void filter_returnsFalse_whenDiscountIsExpired() {
        Product product = new Product("P1", "ciocolata", "gustari", "milka", 1, "kg");
        LocalDate today = LocalDate.now();
        Date from = Date.from(today.minusDays(5).atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date to = Date.from(today.minusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date fileDate = new Date();

        Discount discount = new Discount(product, from, to, 10.0, "kaufland", fileDate);
        DiscountAvailabilityFilter filter = new DiscountAvailabilityFilter();

        assertFalse(filter.filter(discount));
    }
}

