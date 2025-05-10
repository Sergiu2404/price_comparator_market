package org.example.services;

import org.example.domain.Discount;
import org.example.repositories.DiscountsRepository;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class DiscountsService {
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

    public List<Discount> getAllDiscountsAddedInLastDay(){
        String currentDate = LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE);

        return this.getAllDiscounts().stream()
                .filter(discount -> discount.getFromDate().toString().equals(currentDate))
                .collect(Collectors.toList());
    }
}
