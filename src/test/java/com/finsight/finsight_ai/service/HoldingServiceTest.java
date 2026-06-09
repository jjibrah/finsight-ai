package com.finsight.finsight_ai.service;

import com.finsight.finsight_ai.model.Holding;
import com.finsight.finsight_ai.repository.HoldingRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class HoldingServiceTest {

    @Mock
    private HoldingRepository repository;

    @Mock
    private PriceService priceService;

    @InjectMocks
    private HoldingService holdingService;

    @Test
    void shouldCalculateProfitCorrectly() {

        Holding holding = new Holding(
                "AAPL",
                10,
                150
        );

        when(priceService.getPrice("AAPL"))
                .thenReturn(190.0);

        double pnl = holdingService.calculatePnL(holding);

        assertEquals(400.0, pnl);
    }

    @Test
    void shouldSaveHolding() {

        Holding holding = new Holding(
                "AAPL",
                10,
                150
        );

        Holding savedHolding = new Holding(
                "AAPL",
                10,
                150
        );

        when(repository.save(holding))
                .thenReturn(savedHolding);

        Holding result =
                holdingService.addHolding(holding);

        assertNotNull(result);
        assertEquals("AAPL", result.getsymbol());
    }

    @Test
    void shouldThrowExceptionWhenHoldingNotFound() {

        when(repository.existsById(999L))
                .thenReturn(false);

        assertThrows(
                RuntimeException.class,
                () -> holdingService.deleteHolding(999L)
        );
    }
}