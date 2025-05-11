package org.example.helpers;

import org.example.domain.PriceEntry;

public interface PriceEntryFilterStrategy {
    boolean matches(PriceEntry priceEntry);
}
