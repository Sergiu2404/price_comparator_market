package test_domain;

import org.example.domain.Discount;
import org.example.domain.PriceEntry;
import org.example.domain.Product;
import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class TestDomainEntities {

    @Test
    void testProductEntity() {
        Product product = new Product("P001", "iaurt grecesc", "lactate", "napolact", 0.5, "kg");

        assertEquals("P001", product.getId());
        assertEquals("iaurt grecesc", product.getName());
        assertEquals("lactate", product.getCategory());
        assertEquals("napolact", product.getBrand());
        assertEquals(0.5, product.getQuantity());
        assertEquals("kg", product.getUnit());

        String expectedToString = "P001, iaurt grecesc, lactate, napolact, quantity: 0.5, unit: kg";
        assertEquals(expectedToString, product.toString());
    }

    @Test
    void testPriceEntryEntity() {
        Product product = new Product("P002", "detergent lichid", "Curatenie", "ariel", 2.0, "l");
        LocalDate date = LocalDate.of(2024, 5, 10);
        PriceEntry entry = new PriceEntry(product, 15.0, "RON", "selgros", date);

        assertEquals(product, entry.getProduct());
        assertEquals(15.0, entry.getPrice());
        assertEquals("RON", entry.getCurrency());
        assertEquals("selgros", entry.getStore());
        assertEquals(date, entry.getDate());
    }

    @Test
    void testDiscountEntity() {
        Product product = new Product("P003", "suc portocale", "Bauturi", "Cappy", 1.5, "l");

        LocalDate fromLocalDate = LocalDate.of(2024, 5, 1);
        LocalDate toLocalDate = LocalDate.of(2024, 5, 20);
        LocalDate fileLocalDate = LocalDate.of(2024, 4, 30);

        Date fromDate = Date.from(fromLocalDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date toDate = Date.from(toLocalDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date fileDate = Date.from(fileLocalDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

        Discount discount = new Discount(product, fromDate, toDate, 10.0, "lidl", fileDate);

        assertEquals(product, discount.getProduct());
        assertEquals(fromDate, discount.getFromDate());
        assertEquals(toDate, discount.getToDate());
        assertEquals(10.0, discount.getPercentageOfDiscount());
        assertEquals("lidl", discount.getDiscountStore());
        assertEquals(fileDate, discount.getFileDate());

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String expectedToString = "Discount: " + sdf.format(fromDate) + " --> " + sdf.format(toDate)
                + ", 10.0%, lidl, " + sdf.format(fileDate);
        assertEquals(expectedToString, discount.toString());
    }
}
