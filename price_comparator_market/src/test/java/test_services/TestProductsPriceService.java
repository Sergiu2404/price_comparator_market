package test_services;

import org.example.domain.PriceEntry;
import org.example.domain.Product;
import org.example.repositories.ProductsPricesRepository;
import org.example.services.ProductsPriceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TestProductsPriceService {
    private ProductsPricesRepository mockRepository;
    private ProductsPriceService service;

    @BeforeEach
    void setUp() {
        mockRepository = mock(ProductsPricesRepository.class);
        service = new ProductsPriceService(mockRepository);
    }

    @Test
    void getAllPriceEntriesByName_returnsOnlyMatchingEntries() {
        Product p1 = new Product("P001", "suc portocale", "Bauturi", "cappy", 1, "l");
        Product p2 = new Product("P002", "iaurt grecesc", "Lactate", "zuzu", 0.5, "kg");

        PriceEntry e1 = new PriceEntry(p1, 5.0, "RON", "lidl", LocalDate.now());
        PriceEntry e2 = new PriceEntry(p2, 4.0, "RON", "kaufland", LocalDate.now());

        when(mockRepository.getAllPriceEntries()).thenReturn(List.of(e1, e2));

        List<PriceEntry> result = service.getAllPriceEntriesByName("iaurt grecesc");

        assertEquals(1, result.size());
        assertEquals("iaurt grecesc", result.get(0).getProduct().getName());
    }

    @Test
    void getSortedPriceEntriesByStoreAndDate_returnsSorted() {
        Product p = new Product("P003", "detergent lichid", "Curatenie", "ariel", 2, "l");

        PriceEntry e1 = new PriceEntry(p, 20.0, "RON", "lidl", LocalDate.of(2024, 1, 1));
        PriceEntry e2 = new PriceEntry(p, 18.0, "RON", "kaufland", LocalDate.of(2024, 1, 1));

        List<PriceEntry> sorted = service.getSortedPriceEntriesByStoreAndDate(List.of(e1, e2));
        assertEquals("kaufland", sorted.get(0).getStore());
    }

    @Test
    void getAllProducts_returnsFromRepository() {
        Product product = new Product("P004", "suc portocale", "Bauturi", "cappy", 1, "l");
        when(mockRepository.getAllProducts()).thenReturn(List.of(product));

        List<Product> result = service.getAllProducts();
        assertEquals(1, result.size());
        assertEquals("suc portocale", result.get(0).getName());
    }

    @Test
    void getAllSortedProductsById_returnsSortedList() {
        Product p1 = new Product("P002", "iaurt", "Lactate", "zuzu", 0.5, "kg");
        Product p2 = new Product("P001", "suc", "Bauturi", "cappy", 1, "l");

        when(mockRepository.getAllProducts()).thenReturn(List.of(p1, p2));

        List<Product> sorted = service.getAllSortedProductsById();
        assertEquals("P001", sorted.get(0).getId());
    }

    @Test
    void getPriceEntriesByProductNameAndCategory_filtersCorrectly() {
        Product p1 = new Product("P010", "iaurt grecesc", "Lactate", "zuzu", 0.5, "kg");
        Product p2 = new Product("P011", "detergent lichid", "Curatenie", "ariel", 2, "l");

        PriceEntry e1 = new PriceEntry(p1, 4.5, "RON", "lidl", LocalDate.now());
        PriceEntry e2 = new PriceEntry(p2, 15.0, "RON", "kaufland", LocalDate.now());

        when(mockRepository.getAllPriceEntries()).thenReturn(List.of(e1, e2));

        List<PriceEntry> result = service.getPriceEntriesByProductNameAndCategory("iaurt", "Lactate");

        assertEquals(1, result.size());
        assertTrue(result.get(0).getProduct().getName().contains("iaurt"));
    }
}
