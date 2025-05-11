package test_repositories;

import org.example.domain.PriceEntry;
import org.example.domain.Product;
import org.example.repositories.ProductsPricesRepository;
import org.example.utils.PriceCSVParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TestProductsPricesRepository {

    @Mock
    private PriceCSVParser mockParser;

    private ProductsPricesRepository repository;

    @BeforeEach
    public void setup() {
        repository = spy(new ProductsPricesRepository(mockParser));
    }

    @Test
    void loadProductPrices_successfullyParsesFiles() throws Exception {
        Product product1 = new Product("P001", "mere", "fructe", "brand", 1.0, "unit");
        Product product2 = new Product("P002", "pere", "fructe", "brand", 2.0, "unit");

        PriceEntry entry1 = new PriceEntry(product1, 10.99, "RON", "lidl", LocalDate.now());
        PriceEntry entry2 = new PriceEntry(product2, 20.99, "RON", "kaufland", LocalDate.now());

        List<PriceEntry> sampleEntries = new ArrayList<>();
        sampleEntries.add(entry1);
        sampleEntries.add(entry2);

        URL testUrl = getClass().getClassLoader().getResource("data_files");
        doReturn(testUrl).when(repository).getResourceUrl();

        when(mockParser.parse(any(Path.class))).thenReturn(sampleEntries);
        repository.loadProductPrices();

        // act
        List<PriceEntry> resultEntries = repository.getAllPriceEntries();
        List<Product> resultProducts = repository.getAllProducts();

        //check if entries and products have been loaded
        assertNotNull(resultEntries);
        assertEquals(2, resultEntries.size());

        assertNotNull(resultProducts);
        assertEquals(2, resultProducts.size());

        // check if parser is called
        verify(mockParser, atLeastOnce()).parse(any(Path.class));
    }

    @Test
    void getResourceUrl_throwsExceptionWhenResourceNotFound() {
        ProductsPricesRepository badRepo = spy(new ProductsPricesRepository(mockParser));

        try {
            doThrow(new IOException("data_files folder not found in resources"))
                    .when(badRepo).getResourceUrl();
        } catch (IOException e) {
            fail("Unexpected IOException setup");
        }

        assertThrows(IOException.class, badRepo::loadProductPrices);
    }

}