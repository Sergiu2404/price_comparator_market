package org.example.helpers;

import org.example.domain.Discount;

public interface DiscountFilterStrategy {
    boolean filter(Discount discount);
}
