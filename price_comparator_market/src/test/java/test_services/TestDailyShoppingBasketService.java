package test_services;

import org.example.data_transfer_objects.ProductPriceWithDiscountInfo;
import org.example.services.DailyShoppingBasketService;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;


public class TestDailyShoppingBasketService {
    @Test
    public void givenNoMatchingProducts_whenOptimizingBasket_thenReturnsEmptyMap() {
        DailyShoppingBasketService service = new DailyShoppingBasketService();

        List<String> productNames = List.of("masina");
        List<ProductPriceWithDiscountInfo> products = List.of(
                new ProductPriceWithDiscountInfo("1", "detergent lichid", "lidl", 5.0, "RON", 0, 5.0, 1.0, "l", 5.0)
        );

        Map<String, List<ProductPriceWithDiscountInfo>> result = service.optimizeBasketForSavings(productNames, products);

        assertTrue(result.isEmpty());
    }
    @Test
    public void givenMultipleProductNames_OptimizeBasket_SelectLowestPricesPerProductPerStore(){
        //arrange
        DailyShoppingBasketService dailyShoppingBasketService = new DailyShoppingBasketService();
        List<String> productNames = List.of("detergent lichid", "suc portocale");

        List<ProductPriceWithDiscountInfo> products = List.of(
                new ProductPriceWithDiscountInfo("1", "detergent lichid", "lidl", 5.0, "RON", 0, 5.0, 1.0, "l", 5.0),
                new ProductPriceWithDiscountInfo("2", "detergent lichid", "selgros", 4.5, "RON", 0, 4.5, 1.0, "l", 4.5),
                new ProductPriceWithDiscountInfo("3", "suc portocale", "lidl", 2.5, "RON", 0, 2.5, 1.0, "buc", 2.5),
                new ProductPriceWithDiscountInfo("4", "suc portocale", "penny", 2.0, "RON", 0, 2.0, 1.0, "buc", 2.0)
        );

        //act
        Map<String, List<ProductPriceWithDiscountInfo>> result = dailyShoppingBasketService.optimizeBasketForSavings(productNames, products);

        //assert
        List<ProductPriceWithDiscountInfo> storeBProducts = result.get("selgros");
        assertNotNull(storeBProducts);
        assertEquals(1, storeBProducts.size());
        assertEquals("detergent lichid", storeBProducts.get(0).getProductName());

        List<ProductPriceWithDiscountInfo> storeCProducts = result.get("penny");
        assertNotNull(storeCProducts);
        assertEquals(1, storeCProducts.size());
        assertEquals("suc portocale", storeCProducts.get(0).getProductName());
    }
}
