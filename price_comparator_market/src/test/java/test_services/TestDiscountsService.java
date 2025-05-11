package test_services;

import org.example.domain.Discount;
import org.example.domain.Product;
import org.example.repositories.DiscountsRepository;
import org.example.services.DiscountsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TestDiscountsService {
    private DiscountsRepository mockRepository;
    private DiscountsService discountsService;

    private final Product product = new Product("id", "ciocolata", "gustari", "milka", 1.0, "unit");

    @BeforeEach
    void setUp() {
        mockRepository = mock(DiscountsRepository.class);
        discountsService = new DiscountsService(mockRepository);
    }

    @Test
    void getAllDiscounts_returnsAllFromRepository() {
        List<Discount> mockDiscounts = List.of(
                createDiscount("lidl", 10, daysAgo(2)),
                createDiscount("kaufland", 20, daysAgo(1))
        );

        when(mockRepository.getAllDiscounts()).thenReturn(mockDiscounts);

        List<Discount> result = discountsService.getAllDiscounts();
        assertEquals(2, result.size());
        assertEquals("lidl", result.get(0).getDiscountStore());
    }

    @Test
    void getAllDiscountsAddedToday_returnsOnlyTodayDiscounts() {
        Date today = Date.from(LocalDate.now()
                .atStartOfDay(ZoneId.systemDefault())
                .toInstant());

        Date twoDaysAgo = daysAgo(2);

        Discount todayDiscount = createDiscount("TodayStore", 15, today);
        Discount oldDiscount = createDiscount("OldStore", 20, twoDaysAgo);

        when(mockRepository.getAllDiscounts()).thenReturn(List.of(todayDiscount, oldDiscount));

        List<Discount> result = discountsService.getAllDiscountsAddedToday();

        assertEquals(1, result.size());
        assertEquals("TodayStore", result.get(0).getDiscountStore());
    }

    @Test
    void getHighestDiscountPercentageForEachStore_returnsMaxPerStore() {
        Discount d1 = createDiscount("lidl", 10, new Date());
        Discount d2 = createDiscount("lidl", 25, new Date()); // higher
        Discount d3 = createDiscount("kaufland", 5, new Date());

        when(mockRepository.getAllDiscounts()).thenReturn(List.of(d1, d2, d3));

        List<Discount> result = discountsService.getHighestDiscountPercentageForEachStore();

        assertEquals(2, result.size());
        assertTrue(result.stream().anyMatch(d -> d.getDiscountStore().equals("lidl") && d.getPercentageOfDiscount() == 25));
        assertTrue(result.stream().anyMatch(d -> d.getDiscountStore().equals("kaufland")));
    }

    private Discount createDiscount(String store, double percent, Date fileDate) {
        Discount discount = new Discount();
        discount.setDiscountStore(store);
        discount.setPercentageOfDiscount(percent);
        discount.setFileDate(fileDate);
        discount.setProduct(product);
        discount.setFromDate(new Date());
        discount.setToDate(new Date(System.currentTimeMillis() + 1000000));
        return discount;
    }

    private Date daysAgo(int days) {
        return Date.from(LocalDate.now().minusDays(days)
                .atStartOfDay(ZoneId.systemDefault())
                .toInstant());
    }
}