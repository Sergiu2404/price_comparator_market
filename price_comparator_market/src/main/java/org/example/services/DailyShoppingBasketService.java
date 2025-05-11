package org.example.services;
import org.example.data_transfer_objects.ProductPriceWithDiscountInfo;
import java.util.*;

public class DailyShoppingBasketService {
    public Map<String, List<ProductPriceWithDiscountInfo>> optimizeBasketForSavings(List<String> productNames, List<ProductPriceWithDiscountInfo> allProducts) {
        Map<String, List<ProductPriceWithDiscountInfo>> storeToBasketMap = new HashMap<>();

        for (String productName : productNames) {
            List<ProductPriceWithDiscountInfo> matchingProducts = allProducts.stream()
                    .filter(p -> p.getProductName().toLowerCase().contains(productName.toLowerCase()))
                    .toList();

            if (!matchingProducts.isEmpty()) {
                ProductPriceWithDiscountInfo bestDeal = matchingProducts.stream()
                        .min(Comparator.comparingDouble(ProductPriceWithDiscountInfo::getPricePerUnit))
                        .orElse(null);

                String store = bestDeal.getStore();
                storeToBasketMap.computeIfAbsent(store, k -> new ArrayList<>()).add(bestDeal);
            }
        }
        return storeToBasketMap;
    }
}
