package org.example.services;

import org.example.domain.Discount;
import org.example.repositories.DiscountsRepository;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;


public class DiscountsService {
    private static final int HOURS_IN_A_DAY = 24;
    private final DiscountsRepository discountsRepository;

    public DiscountsService(DiscountsRepository discountsRepository){
        this.discountsRepository = discountsRepository;
    }

    public void loadDiscounts() throws IOException {
        this.discountsRepository.loadDiscounts();
    }

    public List<Discount> getAllDiscounts(){
        return this.discountsRepository.getAllDiscounts();
    }

    public List<Discount> getAllDiscountsAddedToday() {
        LocalDate today = LocalDate.now();

        return this.getAllDiscounts().stream()
                .filter(discount -> discount.getFileDate().toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate()
                        .equals(today))
                .collect(Collectors.toList());
    }

    public List<Discount> getAllAvailableDiscounts() {
        LocalDate today = LocalDate.now();

        return this.getAllDiscounts().stream()
                .filter(discount -> {
                    LocalDate fromDate = discount.getFromDate().toInstant()
                            .atZone(ZoneId.systemDefault())
                            .toLocalDate();
                    LocalDate toDate = discount.getToDate().toInstant()
                            .atZone(ZoneId.systemDefault())
                            .toLocalDate();

                    return ( !today.isBefore(fromDate) && !today.isAfter(toDate) );
                })
                .collect(Collectors.toList());
    }
}
