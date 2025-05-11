package test_repositories;

import org.example.domain.Discount;
import org.example.repositories.DiscountsRepository;
import org.example.utils.DiscountCSVParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;


import java.net.URL;
import java.nio.file.Path;
import java.util.List;

import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TestDiscountsRepository {

    @Mock
    private DiscountCSVParser mockParser;

    private DiscountsRepository repository;

    @BeforeEach
    public void setup() {
        repository = spy(new DiscountsRepository(mockParser));
    }

    @Test
    void loadDiscounts_successfullyParsesFiles() throws Exception {
        // arrange
        URL testUrl = getClass().getClassLoader().getResource("data_files/discounts");
        doReturn(testUrl).when(repository).getResourceUrl();

        Discount discount = new Discount();
        List<Discount> sampleDiscounts = List.of(discount);

        when(mockParser.parse(any(Path.class))).thenReturn(sampleDiscounts);

        // act
        repository.loadDiscounts();

        // assert
        List<Discount> result = repository.getAllDiscounts();
        assertNotNull(result);
        assertFalse(result.isEmpty());

        verify(mockParser, atLeastOnce()).parse(any(Path.class));
    }
}