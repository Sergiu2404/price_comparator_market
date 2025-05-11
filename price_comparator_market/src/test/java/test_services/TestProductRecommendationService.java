package test_services;

import org.example.data_transfer_objects.ProductPriceWithDiscountInfo;
import org.example.domain.Discount;
import org.example.domain.PriceEntry;
import org.example.domain.Product;
import org.example.services.DiscountsService;
import org.example.services.ProductRecommendationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TestProductRecommendationService {
    private DiscountsService mockDiscountsService;
    private ProductRecommendationService recommendationService;

    @BeforeEach
    void setup() {
        mockDiscountsService = mock(DiscountsService.class);
        recommendationService = new ProductRecommendationService(mockDiscountsService);
    }

    @Test
    void getPriceInfoWithDiscounts_appliesDiscountCorrectly() {
        // arrange
        Product product = new Product("P001", "iaurt grecesc", "Lactate", "zuzu", 1.0, "kg");
        PriceEntry entry = new PriceEntry(product, 10.0, "RON", "lidl", LocalDate.now());

        Date fromDate = Date.from(LocalDate.now().minusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date toDate = Date.from(LocalDate.now().plusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date fileDate = new Date();
        Discount discount = new Discount(product, fromDate, toDate, 20.0, "lidl", fileDate);

        when(mockDiscountsService.getAllFilteredDiscountsByAvailability()).thenReturn(List.of(discount));

        // act
        List<ProductPriceWithDiscountInfo> result = recommendationService.getPriceInfoWithDiscounts(List.of(entry));

        // assert
        assertEquals(1, result.size());
        ProductPriceWithDiscountInfo info = result.get(0);
        assertEquals(20.0, info.getDiscountPercentage());
        assertEquals(8.0, info.getFinalPrice());
        assertEquals(8.0, info.getPricePerUnit());
    }
}
