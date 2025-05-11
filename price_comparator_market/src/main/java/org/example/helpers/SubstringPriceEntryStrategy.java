package org.example.helpers;

import org.example.domain.PriceEntry;

public class SubstringPriceEntryStrategy implements PriceEntryFilterStrategy {
    private final String nameSubstring;
    private final String categorySubstring;

    public SubstringPriceEntryStrategy(String nameSubstring, String categorySubstring) {
        this.nameSubstring = nameSubstring;
        this.categorySubstring = categorySubstring;
    }

    @Override
    public boolean matches(PriceEntry entry) {
        boolean matchesName = nameSubstring.isEmpty() ||
                entry.getProduct().getName().toLowerCase().contains(nameSubstring.toLowerCase());
        boolean matchesCategory = categorySubstring.isEmpty() ||
                entry.getProduct().getCategory().toLowerCase().contains(categorySubstring.toLowerCase());

        return matchesName && matchesCategory;
    }
}
