package org.example.services;

import org.example.data_transfer_objects.ProductPriceWithDiscountInfo;
import org.example.domain.Discount;
import org.example.domain.PriceEntry;
import org.example.helpers.UnitPriceCalculator;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ProductRecommendationService {
    private final DiscountsService discountsService;

    public ProductRecommendationService(DiscountsService discountsService) {
        this.discountsService = discountsService;
    }

    public List<ProductPriceWithDiscountInfo> getPriceInfoWithDiscounts(List<PriceEntry> priceEntries) {
        List<Discount> availableDiscounts = discountsService.getAllFilteredDiscountsByAvailability();

        Map<String, Discount> discountsMap = availableDiscounts.stream()
                .collect(Collectors.toMap(
                        discount -> discount.getProduct().getId(),
                        discount -> discount,
                        (discount1, discount2) -> discount1
                ));

        List<ProductPriceWithDiscountInfo> result = new ArrayList<>();

        for (PriceEntry entry : priceEntries) {
            String productId = entry.getProduct().getId();
            String productName = entry.getProduct().getName();
            String store = entry.getStore();
            double price = entry.getPrice();
            String currency = entry.getCurrency();
            double quantity = entry.getProduct().getQuantity();
            String unit = entry.getProduct().getUnit();

            Discount discount = discountsMap.get(productId);

            double discountPercentage = 0;
            double finalPrice = price;

            if (discount != null) {
                discountPercentage = discount.getPercentageOfDiscount();
                finalPrice = price * (1 - discountPercentage / 100.0);
            }

            double pricePerUnit = UnitPriceCalculator.calculatePricePerUnit(unit, finalPrice, quantity);

            result.add(new ProductPriceWithDiscountInfo(
                    productId,
                    productName,
                    store,
                    price,
                    currency,
                    discountPercentage,
                    finalPrice,
                    quantity,
                    unit,
                    pricePerUnit
            ));
        }

        return result;
    }


    public List<ProductPriceWithDiscountInfo> sortByPricePerUnit(List<ProductPriceWithDiscountInfo> products) {
        for (ProductPriceWithDiscountInfo product : products) {
            double pricePerUnit = UnitPriceCalculator.calculatePricePerUnit(
                    product.getUnit(),
                    product.getFinalPrice(),
                    product.getQuantity()
            );
            product.setPricePerUnit(pricePerUnit);
        }

        products.sort(Comparator.comparingDouble(ProductPriceWithDiscountInfo::getPricePerUnit));
        return products;
    }
}
