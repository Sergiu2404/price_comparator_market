package org.example.helpers;

import org.example.domain.Discount;

import java.time.LocalDate;
import java.time.ZoneId;

public class DiscountAvailabilityFilter  implements DiscountFilterStrategy{

    @Override
    public boolean filter(Discount discount){
        LocalDate today = LocalDate.now();

        LocalDate fromDate = discount.getFromDate().toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
        LocalDate toDate = discount.getToDate().toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();

        return (!today.isBefore(fromDate) && !today.isAfter(toDate));
    }
}
